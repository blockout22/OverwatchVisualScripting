package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeHorizontalFacingAngleOf extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar output = new PinVar();

    public NodeHorizontalFacingAngleOf(Graph graph) {
        super(graph);
        setName("Horizontal Facing Angle Of");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);

        outputData.getValue().set("Horizontal Facing Angle Of(" + playerData.getValue().get() + ")");
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
        return "The horizontal angle in degrees of a player's current facing relative to the world, this value increases as the player rotates to the left (wrapping around +/- 180).";
    }
}