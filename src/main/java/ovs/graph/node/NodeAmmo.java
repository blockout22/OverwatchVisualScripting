package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeAmmo extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinClip = new PinVar();
    PinVar output = new PinVar();

    public NodeAmmo(Graph graph) {
        super(graph);
        setName("Ammo");

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

        outputData.getValue().set("Ammo(" + playerData.getValue().get() + ", " + clipData.getValue().get() + ")");
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