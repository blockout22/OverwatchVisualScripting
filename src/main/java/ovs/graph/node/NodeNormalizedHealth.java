package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeNormalizedHealth extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar output = new PinVar();

    public NodeNormalizedHealth(Graph graph) {
        super(graph);
        setName("Normalized Health");

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

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ")");
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
        return "The current health of a playerr, including amor and shields, normalized  between 0 and 1. (for example, 0 is no health, 0.5 is half health, 1 is full health, etc.).";
    }
}