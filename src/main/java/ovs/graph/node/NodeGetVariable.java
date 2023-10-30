package ovs.graph.node;

import imgui.ImVec4;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinVar;

public class NodeGetVariable extends Node{

    PinVar outputPin = new PinVar();
    private ComboBox comboBox = new ComboBox();

    //private int lastVariableCount = 0;
    //TODO allow users to select a specific players variable
    public NodeGetVariable(Graph graph) {
        super(graph);
        setName("Get Variable");
        setColor(0, 125, 255);

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        comboBox.addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                String lastSelectedValue = comboBox.getSelectedValue();
                populateCombobox();

                comboBox.selectValue(lastSelectedValue);
                width = -1;

                //lastVariableCount = getGraph().globalVariables.size() + getGraph().playerVariables.size();
            }
        });

        comboBox.addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                width = -1;
            }
        });
    }

    @Override
    public void execute() {
//        if(lastVariableCount != getGraph().globalVariables.size() + getGraph().playerVariables.size()){
//
//            return;
//        }
        if (outputPin.isConnected() && comboBox.size() > 0 && comboBox.getSelectedIndex() != -1) {
            PinData<ImString> data = outputPin.getData();
//            String[] val = comboBox.getSelectedValue().split(":");
//            data.getValue().set(val[0] + "." + val[1].replace(" ", ""));
            data.getValue().set(comboBox.getSelectedValue());
        }
    }

    @Override
    public void copy(Node node) {
        populateCombobox();
        if(node instanceof NodeGetVariable){
            comboBox.selectValue(((NodeGetVariable) node).comboBox.getSelectedValue());
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
//        System.out.println(comboBox.getSelectedValue());
        getExtraSaveData().add("Var:" + comboBox.getSelectedValue());
    }

    private void populateCombobox(){
        //Populate combox to allow selection of saved variables
        comboBox.clear();
        for (int i = 0; i < getGraph().globalVariables.size(); i++) {
            comboBox.addOption("Global." + getGraph().globalVariables.get(i).name);
        }

        for (int i = 0; i < getGraph().playerVariables.size(); i++) {
            comboBox.addOption("Event Player." + getGraph().playerVariables.get(i).name);
        }

        for (int i = 0; i < comboBox.size(); i++) {
            String option = comboBox.getOptions()[i];
            if(option.startsWith("Global")){
                comboBox.setItemColor(i, new ImVec4(.75f, .75f, .75f, 255));
            }else{
                comboBox.setItemColor(i, new ImVec4(.75f, 1, .75f, 255));
            }
        }
    }

    @Override
    public void onLoaded() {
        populateCombobox();
        for(String data : getExtraSaveData()){
            if(data.startsWith("Var")){
//                int index = Integer.valueOf(data.split(":")[1]);
//                if(index > -1) {
//                    comboBox.select(index);
//                }
                try{
                    comboBox.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    comboBox.select(-1);
                }
            }
        }
    }

    @Override
    public String getOutput() {
//        String[] val = comboBox.getSelectedValue().split(":");
//        return val[0] + "." + val[1].replace(" ", "");
        return comboBox.getSelectedValue();
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
