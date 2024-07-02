package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeMaxAmmo extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinClip = new PinVar();
    PinVar output = new PinVar();

    public NodeMaxAmmo(Graph graph) {
        super(graph);
        setName("Max Ammo");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinClip.setNode(this);
        pinClip.setName("Clip");
        addCustomInput(pinClip);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> clipData = pinClip.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinClip, clipData);

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + clipData.getValue().get() + ")");
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
        return "The current max ammo of a player.";
    }
}