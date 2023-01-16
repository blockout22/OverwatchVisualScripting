package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeVectorTowards extends Node {

    PinVar pinStartPosition = new PinVar();
    PinVar pinEndPosition = new PinVar();
    PinVar output = new PinVar();

    public NodeVectorTowards(Graph graph) {
        super(graph);
        setName("Vector Towards");

        pinStartPosition.setNode(this);
        pinStartPosition.setName("Start Position");
        addCustomInput(pinStartPosition);

        pinEndPosition.setNode(this);
        pinEndPosition.setName("End Position");
        addCustomInput(pinEndPosition);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> startPositionData = pinStartPosition.getData();
        PinData<ImString> endPositionData = pinEndPosition.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinStartPosition, startPositionData);
        handlePinStringConnection(pinEndPosition, endPositionData);

        outputData.getValue().set(getName() + "( " + pinStartPosition.getData().getValue() + ", " + endPositionData.getValue().get() + ")");
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