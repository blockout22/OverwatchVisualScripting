package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDamage extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinDamager = new PinVar();
    PinVar pinAmount = new PinVar();
    PinAction output = new PinAction();

    public NodeDamage(Graph graph) {
        super(graph);
        setName("Damage");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinDamager.setNode(this);
        pinDamager.setName("Damager");
        addCustomInput(pinDamager);

        pinAmount.setNode(this);
        pinAmount.setName("Amount");
        addCustomInput(pinAmount);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> damagerData = pinDamager.getData();
        PinData<ImString> amountData = pinAmount.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");
        handlePinStringConnection(pinDamager, damagerData, "Null");
        handlePinStringConnection(pinAmount, amountData, "0");

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + damagerData.getValue().get() + ", " + amountData.getValue().get() + ");");
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
        return "Applies instantaneous damage to one or more players, possibly killing the player";
    }
}