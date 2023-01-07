package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeDirectionFromAngles extends Node {

    PinVar pinHorizontalAngle = new PinVar();
    PinVar pinVerticalAngle = new PinVar();
    PinVar output = new PinVar();

    public NodeDirectionFromAngles(Graph graph) {
        super(graph);
        setName("Direction From Angles");

        pinHorizontalAngle.setNode(this);
        pinHorizontalAngle.setName("Horizontal Angle");
        addCustomInput(pinHorizontalAngle);

        pinVerticalAngle.setNode(this);
        pinVerticalAngle.setName("Vertical Angle");
        addCustomInput(pinVerticalAngle);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> horizontalData = pinHorizontalAngle.getData();
        PinData<ImString> verticalData = pinVerticalAngle.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinHorizontalAngle, horizontalData);
        handlePinStringConnection(pinVerticalAngle, verticalData);

        outputData.getValue().set("Direction From Angles(" + horizontalData.getValue().get() + ", " + verticalData.getValue().get());
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