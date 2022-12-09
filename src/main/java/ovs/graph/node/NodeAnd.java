package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinIf;

public class NodeAnd extends Node{

    PinIf leftPin = new PinIf();
    PinIf rightPin = new PinIf();

    PinIf output = new PinIf();

    public NodeAnd(Graph graph) {
        super(graph);
        setName("And");

        leftPin.setNode(this);
        addCustomInput(leftPin);

        rightPin.setNode(this);
        addCustomInput(rightPin);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> leftData = leftPin.getData();
        PinData<ImString> rightData = rightPin.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(leftPin, leftData);
        handlePinStringConnection(rightPin, rightData);

        outputData.getValue().set(leftData.getValue().get() + " && " + rightData.getValue().get());
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
