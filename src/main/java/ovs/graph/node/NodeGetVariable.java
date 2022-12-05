package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinVar;

public class NodeGetVariable extends Node{

    PinVar outputPin = new PinVar();
    private ComboBox comboBox = new ComboBox();

    //private int lastVariableCount = 0;

    public NodeGetVariable(Graph graph) {
        super(graph);
        setName("Get Variable");

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        comboBox.addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                String lastSelectedValue = comboBox.getSelectedValue();
                comboBox.clear();
                for (int i = 0; i < getGraph().globalVariables.size(); i++) {
                    comboBox.addOption("Global: " + getGraph().globalVariables.get(i).name);
                }

                for (int i = 0; i < getGraph().playerVariables.size(); i++) {
                    comboBox.addOption("Event Player: " + getGraph().playerVariables.get(i).name);
                }

                comboBox.selectValue(lastSelectedValue);

                //lastVariableCount = getGraph().globalVariables.size() + getGraph().playerVariables.size();
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
            String[] val = comboBox.getSelectedValue().split(":");
            data.getValue().set(val[0] + "." + val[1].replace(" ", ""));
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        //TODO get variable class and save ID and type
        getExtraSaveData().add("Var:" + comboBox.getSelectedIndex());
    }

    @Override
    public void onLoaded() {
        //Populate combox to allow selection of saved variables
        comboBox.clear();
        for (int i = 0; i < getGraph().globalVariables.size(); i++) {
            comboBox.addOption("Global: " + getGraph().globalVariables.get(i).name);
        }

        for (int i = 0; i < getGraph().playerVariables.size(); i++) {
            comboBox.addOption("Event Player: " + getGraph().playerVariables.get(i).name);
        }
        for(String data : getExtraSaveData()){
            if(data.startsWith("Var")){
                int index = Integer.valueOf(data.split(":")[1]);
                if(index > -1) {
                    comboBox.select(index);
                }
            }
        }
    }

    @Override
    public String getOutput() {
        String[] val = comboBox.getSelectedValue().split(":");
        return val[0] + "." + val[1].replace(" ", "");
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
