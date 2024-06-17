package ovs.graph.node;

import imgui.ImVec4;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinVar;

public class NodeGetGlobalVariable extends Node{

    private PinVar outputPin = new PinVar();
    public ComboBox variableBox = new ComboBox();

    public NodeGetGlobalVariable(Graph graph) {
        super(graph);
        setName("Get Global Variable");
        setColor(0, 125, 255);

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        variableBox.addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                String lastSelectedValue = variableBox.getSelectedValue();
                populateCombobox();

                variableBox.selectValue(lastSelectedValue);
                width = -1;
            }
        });

        variableBox.addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                width = -1;
            }
        });
    }

    @Override
    public void execute() {
        if (outputPin.isConnected() && variableBox.size() > 0 && variableBox.getSelectedIndex() != -1) {
            PinData<ImString> data = outputPin.getData();
            data.getValue().set(variableBox.getSelectedValue());
        }
    }

    @Override
    public void copy(Node node) {
        populateCombobox();
        if(node instanceof NodeGetGlobalVariable){
            variableBox.selectValue(((NodeGetGlobalVariable) node).variableBox.getSelectedValue());
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Var:" + variableBox.getSelectedValue());
    }

    public void populateCombobox(){
        //Populate combox to allow selection of saved variables
        variableBox.clear();
        for (int i = 0; i < getGraph().globalVariables.size(); i++) {
            variableBox.addOption(getGraph().globalVariables.get(i).name);
        }

        for (int i = 0; i < variableBox.size(); i++) {
            String option = variableBox.getOptions()[i];
            variableBox.setItemColor(i, new ImVec4(.75f, .75f, .75f, 255));
        }
    }

    @Override
    public void onLoaded() {
        populateCombobox();
        for(String data : getExtraSaveData()){
            if(data.startsWith("Var")){
                try{
                    variableBox.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    variableBox.select(-1);
                }
            }
        }
    }

    @Override
    public String getOutput() {
        return "Global." + variableBox.getSelectedValue();
    }

    @Override
    public void UI() {
        variableBox.show();
    }
}
