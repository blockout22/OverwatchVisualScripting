package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeNot extends Node {

    PinVar pinValue = new PinVar();
    PinVar output = new PinVar();

    public NodeNot(Graph graph) {
        super(graph);
        setName("Not");

        pinValue.setNode(this);
        pinValue.setName("Value");
        addCustomInput(pinValue);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> valueData = pinValue.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinValue, valueData);

        outputData.getValue().set(getName() + "(" + valueData.getValue().get() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {

    }
}