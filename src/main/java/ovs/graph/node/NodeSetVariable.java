package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetVariable extends Node{

    PinAction outputPin = new PinAction();
    PinVar inputPin = new PinVar();
    private ComboBox comboBox = new ComboBox();

    public NodeSetVariable(Graph graph) {
        super(graph);
        setName("Set Variable");

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        inputPin.setNode(this);
        addCustomInput(inputPin);

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
            }
        });
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        //TODO get variable class and save ID and type
        getExtraSaveData().add("Var:" + comboBox.getSelectedIndex());
    }

    @Override
    public void onLoaded() {
        comboBox.clear();
        for (int i = 0; i < getGraph().globalVariables.size(); i++) {
            comboBox.addOption("Global: " + getGraph().globalVariables.get(i).name);
        }

        for (int i = 0; i < getGraph().playerVariables.size(); i++) {
            comboBox.addOption("Event Player: " + getGraph().playerVariables.get(i).name);
        }

        for(String data : getExtraSaveData()){
            if (data.startsWith("Var")) {
                int index = Integer.valueOf(data.split(":")[1]);
                if(index > -1){
                    comboBox.select(index);
                }
            }
        }
    }

    @Override
    public void execute() {

        if(inputPin.isConnected()){
            Pin connectedPin = inputPin.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            PinData<ImString> inputData = inputPin.getData();

            inputData.getValue().set(connectedData.getValue().get());
        }

        if (outputPin.isConnected() && comboBox.size() > 0 && comboBox.getSelectedIndex() != -1) {
            PinData<ImString> data = outputPin.getData();
            String[] val = comboBox.getSelectedValue().split(":");
            data.getValue().set(val[0] + "." + val[1].replace(" ", ""));
        }
    }

    @Override
    public String getOutput() {
        if(inputPin.isConnected() && (comboBox.getSelectedIndex() != -1)) {

            PinData<ImString> data = inputPin.getData();

            String[] val = comboBox.getSelectedValue().split(":");
            return val[0] + "." + val[1].replace(" ", "") + " = " + data.getValue().get() + ";";
        }

        return "";
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
