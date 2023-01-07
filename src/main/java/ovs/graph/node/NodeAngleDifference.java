package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeAngleDifference extends Node {

    PinVar pinAngle1 = new PinVar();
    PinVar pinAngle2 = new PinVar();
    PinVar output = new PinVar();

    public NodeAngleDifference(Graph graph) {
        super(graph);
        setName("Angle Difference");

        pinAngle1.setNode(this);
        pinAngle1.setName("Angle");
        addCustomInput(pinAngle1);

        pinAngle2.setNode(this);
        pinAngle2.setName("Angle");
        addCustomInput(pinAngle2);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> angle1Data = pinAngle1.getData();
        PinData<ImString> angle2Data = pinAngle2.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinAngle1, angle1Data);
        handlePinStringConnection(pinAngle2, angle2Data);

        outputData.getValue().set("Angle Difference(" + angle1Data.getValue().get() + ", " + angle2Data.getValue().get() + ")");
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