package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeCosineFromDegrees extends Node {

    PinVar pinAngle = new PinVar();
    PinVar output = new PinVar();

    public NodeCosineFromDegrees(Graph graph) {
        super(graph);
        setName("Cosine From Degrees");

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

        outputData.getValue().set("Cosine From Degrees(" + angleData.getValue().get() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "Cosine of the specified angle in degrees.";
    }
}