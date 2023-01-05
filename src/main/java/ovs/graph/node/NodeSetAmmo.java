package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetAmmo extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinClipNum = new PinVar();
    PinVar pinAmmoAmount = new PinVar();

    PinAction output = new PinAction();

    public NodeSetAmmo(Graph graph) {
        super(graph);
        setName("Set Ammo");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinClipNum.setNode(this);
        pinClipNum.setName("Clip Index");
        addCustomInput(pinClipNum);

        pinAmmoAmount.setNode(this);
        pinAmmoAmount.setName("Ammo Amount");
        addCustomInput(pinAmmoAmount);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> clipData = pinClipNum.getData();
        PinData<ImString> amountData = pinAmmoAmount.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinClipNum, clipData);
        handlePinStringConnection(pinAmmoAmount, amountData);

        outputData.getValue().set("Set Ammo(" + playerData.getValue().get() + ", " + clipData.getValue().get() + ", " + amountData.getValue().get() + ");");
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