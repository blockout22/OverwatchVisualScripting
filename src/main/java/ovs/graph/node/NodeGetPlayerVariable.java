package ovs.graph.node;

import imgui.ImVec4;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinVar;

public class NodeGetPlayerVariable extends Node{

    private PinVar pinPlayer = new PinVar();
    private PinVar outputPin = new PinVar();
    public ComboBox variableBox = new ComboBox();

    public NodeGetPlayerVariable(Graph graph) {
        super(graph);
        setName("Get Player Variable");
        setColor(0, 125, 255);

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

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
//        if (outputPin.isConnected() && variableBox.size() > 0 && variableBox.getSelectedIndex() != -1) {
            PinData<ImString> playerData = pinPlayer.getData();
            PinData<ImString> data = outputPin.getData();

            handlePinStringConnection(pinPlayer, playerData, "Event Player");
            data.getValue().set(playerData.getValue().get() + "." + variableBox.getSelectedValue());
//        }
    }

    @Override
    public void copy(Node node) {
        populateCombobox();
        if(node instanceof NodeGetPlayerVariable){
            variableBox.selectValue(((NodeGetPlayerVariable) node).variableBox.getSelectedValue());
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
        for (int i = 0; i < getGraph().playerVariables.size(); i++) {
            variableBox.addOption(getGraph().playerVariables.get(i).name);
        }

        for (int i = 0; i < variableBox.size(); i++) {
            variableBox.setItemColor(i, new ImVec4(.75f, 1, .75f, 255));
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
        PinData<ImString> outputData = outputPin.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        variableBox.show();
    }
}
