package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetMaxAmmo extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinClip = new PinVar();
    PinVar pinMaxAmmo = new PinVar();
    PinAction output = new PinAction();

    public NodeSetMaxAmmo(Graph graph) {
        super(graph);
        setName("Set Max Ammo");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinClip.setNode(this);
        pinClip.setName("Clip");
        addCustomInput(pinClip);

        pinMaxAmmo.setNode(this);
        pinMaxAmmo.setName("Max Ammo");
        addCustomInput(pinMaxAmmo);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> clipData = pinClip.getData();
        PinData<ImString> maxAmmoData = pinMaxAmmo.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");
        handlePinStringConnection(pinClip, clipData, "0");
        handlePinStringConnection(pinMaxAmmo, maxAmmoData, "0");

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + clipData.getValue().get() + ", " + maxAmmoData.getValue().get() + ");");
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
        return "Sets the max ammo of one or more players.";
    }
}