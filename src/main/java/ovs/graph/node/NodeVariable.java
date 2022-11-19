package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinString;

public class NodeVariable extends Node{

    PinString outputPin = new PinString();
    private ComboBox comboBox = new ComboBox();

    private int lastVariableCount = 0;

    public NodeVariable(Graph graph) {
        super(graph);
        setName("Variable");

        outputPin.setNode(this);
        addCustomOutput(outputPin);
    }

    @Override
    public void execute() {
        if(lastVariableCount != getGraph().globalVariables.size() + getGraph().playerVariables.size()){
            comboBox.clear();
            for (int i = 0; i < getGraph().globalVariables.size(); i++) {
                comboBox.addOption("Global: " + getGraph().globalVariables.get(i).name);
            }

            for (int i = 0; i < getGraph().playerVariables.size(); i++) {
                comboBox.addOption("Player: " + getGraph().playerVariables.get(i).name);
            }

            lastVariableCount = getGraph().globalVariables.size() + getGraph().playerVariables.size();
            return;
        }
        if (outputPin.isConnected() && comboBox.size() > 0 && comboBox.getSelectedIndex() != -1) {
            PinData<ImString> data = outputPin.getData();
            String[] val = comboBox.getSelectedValue().replace(" ", "").split(":");
            data.getValue().set(val[0] + "." + val[1]);
        }
    }

    @Override
    public String getOutput() {
        String[] val = comboBox.getSelectedValue().replace(" ", "").split(":");
        return val[0] + "." + val[1];
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
