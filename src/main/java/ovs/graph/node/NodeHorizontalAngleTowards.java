package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeHorizontalAngleTowards extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinPosition = new PinVar();
    PinVar output = new PinVar();

    public NodeHorizontalAngleTowards(Graph graph) {
        super(graph);
        setName("Horizontal Angle Towards");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinPosition.setNode(this);
        pinPosition.setName("Position");
        addCustomInput(pinPosition);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> positionData = pinPosition.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinPosition, positionData);

        outputData.getValue().set("Horizontal Angle Towards(" + playerData.getValue().get() + ", " + positionData.getValue().get() + ")");
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