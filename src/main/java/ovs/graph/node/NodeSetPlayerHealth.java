package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetPlayerHealth extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinAmount = new PinVar();

    PinAction output = new PinAction();

    public NodeSetPlayerHealth(Graph graph) {
        super(graph);
        setName("Set Player Health");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinAmount.setNode(this);
        pinAmount.setName("Amount");
        addCustomInput(pinAmount);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> amountData = pinAmount.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinAmount, amountData, "100");

        outputData.getValue().set("Set Player Health(" + playerData.getValue().get() + ", " + amountData.getValue().get() + ");");
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
        return "Sets the health of a player or players without affecting stats or granting damage/healing credit. this action only has an effect on living players.(for dead players, use the resurrect player action instead.)";
    }
}