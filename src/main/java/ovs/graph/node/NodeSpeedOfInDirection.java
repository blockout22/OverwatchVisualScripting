package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeSpeedOfInDirection extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinDirection = new PinVar();
    PinVar output = new PinVar();

    public NodeSpeedOfInDirection(Graph graph) {
        super(graph);
        setName("Speed Of In Direction");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinDirection.setNode(this);
        pinDirection.setName("Direction");
        addCustomInput(pinDirection);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> directionData = pinDirection.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinDirection, directionData);

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + directionData.getValue().get() + ")");
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