package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetAbilityCooldown extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinButton = new PinVar();
    PinVar pinChargeCount = new PinVar();

    PinAction output = new PinAction();

    public NodeSetAbilityCooldown(Graph graph) {
        super(graph);
        setName("Set Ability Cooldown");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinButton.setNode(this);
        pinButton.setName("Button");
        addCustomInput(pinButton);

        pinChargeCount.setNode(this);
        pinChargeCount.setName("Charge Count");
        addCustomInput(pinChargeCount);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> buttonData = pinButton.getData();
        PinData<ImString> chargeData = pinChargeCount.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinButton, buttonData);
        handlePinStringConnection(pinChargeCount, chargeData, "100");

        outputData.getValue().set("Set Ability Cooldown(" + playerData.getValue().get() + ", " + buttonData.getValue().get() + ", " + chargeData.getValue().get() + ");");
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
        return "Set the ability cooldown time for one or more players if supported.";
    }
}
