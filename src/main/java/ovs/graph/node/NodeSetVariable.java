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
                comboBox.clear();

                for (int i = 0; i < getGraph().globalVariables.size(); i++) {
                    comboBox.addOption("Global: " + getGraph().globalVariables.get(i).name);
                }

                for (int i = 0; i < getGraph().playerVariables.size(); i++) {
                    comboBox.addOption("Player: " + getGraph().playerVariables.get(i).name);
                }
            }
        });
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
            String[] val = comboBox.getSelectedValue().replace(" ", "").split(":");
            data.getValue().set(val[0] + "." + val[1]);
        }
    }

    @Override
    public String getOutput() {
        if(inputPin.isConnected() && (comboBox.getSelectedIndex() != -1)) {

            PinData<ImString> data = inputPin.getData();

            String[] val = comboBox.getSelectedValue().replace(" ", "").split(":");
            return val[0] + "." + val[1] + " = " + data.getValue().get() + ";";
        }

        return "";
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
