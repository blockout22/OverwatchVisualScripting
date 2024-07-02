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

    private PinVar outputPin = new PinVar();
    public ComboBox variableBox = new ComboBox();

    //TODO allow users to select a specific players variable
    public NodeGetVariable(Graph graph) {
        super(graph);
        setName("Get Variable");
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

                //lastVariableCount = getGraph().globalVariables.size() + getGraph().playerVariables.size();
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
//        if(lastVariableCount != getGraph().globalVariables.size() + getGraph().playerVariables.size()){
//
//            return;
//        }
        if (outputPin.isConnected() && variableBox.size() > 0 && variableBox.getSelectedIndex() != -1) {
            PinData<ImString> data = outputPin.getData();
//            String[] val = comboBox.getSelectedValue().split(":");
//            data.getValue().set(val[0] + "." + val[1].replace(" ", ""));
            data.getValue().set(variableBox.getSelectedValue());
        }
    }

    @Override
    public void copy(Node node) {
        populateCombobox();
        if(node instanceof NodeGetVariable){
            variableBox.selectValue(((NodeGetVariable) node).variableBox.getSelectedValue());
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
//        System.out.println(comboBox.getSelectedValue());
        getExtraSaveData().add("Var:" + variableBox.getSelectedValue());
    }

    public void populateCombobox(){
        //Populate combox to allow selection of saved variables
        variableBox.clear();
        for (int i = 0; i < getGraph().globalVariables.size(); i++) {
            variableBox.addOption("Global." + getGraph().globalVariables.get(i).name);
        }

        for (int i = 0; i < getGraph().playerVariables.size(); i++) {
            variableBox.addOption("Event Player." + getGraph().playerVariables.get(i).name);
        }

        for (int i = 0; i < variableBox.size(); i++) {
            String option = variableBox.getOptions()[i];
            if(option.startsWith("Global")){
                variableBox.setItemColor(i, new ImVec4(.75f, .75f, .75f, 255));
            }else{
                variableBox.setItemColor(i, new ImVec4(.75f, 1, .75f, 255));
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
                    variableBox.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    variableBox.select(-1);
                }
            }
        }
    }

    @Override
    public String getOutput() {
//        String[] val = comboBox.getSelectedValue().split(":");
//        return val[0] + "." + val[1].replace(" ", "");
        return variableBox.getSelectedValue();
    }

    @Override
    public void UI() {
        variableBox.show();
    }

    @Override
    public String getTooltip() {
        return "The current value of a either global or player variable (player variables default to using variables from event player, use 'GetPlayerVariable' to specify the player).";
    }
}
