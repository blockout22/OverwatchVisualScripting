package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeVerticalAngleFromDirection extends Node {

    PinVar pinDirection = new PinVar();
    PinVar output = new PinVar();

    public NodeVerticalAngleFromDirection(Graph graph) {
        super(graph);
        setName("Vertical Angle From Direction");

        pinDirection.setNode(this);
        pinDirection.setName("Direction");
        addCustomInput(pinDirection);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> directionData = pinDirection.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinDirection, directionData);

        outputData.getValue().set(getName() + "(" + directionData.getValue().get() + ")");
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