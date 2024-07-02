package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeAbilityCharge extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinButton = new PinVar();
    PinVar output = new PinVar();

    public NodeAbilityCharge(Graph graph) {
        super(graph);
        setName("Ability Charge");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinButton.setNode(this);
        pinButton.setName("Button");
        addCustomInput(pinButton);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> buttonData = pinButton.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinButton, buttonData);

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + buttonData.getValue().get() + ")");
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
        return "The ability charge count for a player associated by button.";
    }
}