package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeHeal extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinHealer = new PinVar();
    PinVar pinAmount = new PinVar();

    PinAction output = new PinAction();

    public NodeHeal(Graph graph) {
        super(graph);
        setName("Heal");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinHealer.setNode(this);
        pinHealer.setName("Healer");
        addCustomInput(pinHealer);

        pinAmount.setNode(this);
        pinAmount.setName("Amount");
        addCustomInput(pinAmount);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> healerData = pinHealer.getData();
        PinData<ImString> amountData = pinAmount.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinHealer, healerData, "Null");
        handlePinStringConnection(pinAmount, amountData, "0");

        outputData.getValue().set("Heal(" + playerData.getValue().get() + ", " + healerData.getValue().get() + ", " + amountData.getValue().get() + ");");

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
        return "Provides an instantaneous heal to one or more players. this heal will not resurrect dead players.";
    }
}
