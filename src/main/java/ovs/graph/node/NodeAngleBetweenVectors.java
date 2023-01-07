package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeAngleBetweenVectors extends Node {

    PinVar pinVector1 = new PinVar();
    PinVar pinVector2 = new PinVar();
    PinVar output = new PinVar();

    public NodeAngleBetweenVectors(Graph graph) {
        super(graph);
        setName("Angle Between Vectors");

        pinVector1.setNode(this);
        pinVector1.setName("Vector");
        addCustomInput(pinVector1);

        pinVector2.setNode(this);
        pinVector2.setName("Vector");
        addCustomInput(pinVector2);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> vector1Data = pinVector1.getData();
        PinData<ImString> vector2Data = pinVector2.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVector1, vector1Data);
        handlePinStringConnection(pinVector2, vector2Data);

        outputData.getValue().set("Angle Between Vectors(" + vector1Data.getValue().get() + ", " + vector2Data.getValue().get() + ")");
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