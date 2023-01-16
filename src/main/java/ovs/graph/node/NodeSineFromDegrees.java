package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeSineFromDegrees extends Node {

    PinVar pinAngle = new PinVar();
    PinVar output = new PinVar();

    public NodeSineFromDegrees(Graph graph) {
        super(graph);
        setName("Sine From Degrees");

        pinAngle.setNode(this);
        pinAngle.setName("Angle");
        addCustomInput(pinAngle);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> angleData = pinAngle.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinAngle, angleData);

        outputData.getValue().set(getName() + "(" + angleData.getValue().get() + ")");
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