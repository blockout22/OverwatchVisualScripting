package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetPlayerAllowedHeroes extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinHero = new PinVar();
    PinAction output = new PinAction();

    public NodeSetPlayerAllowedHeroes(Graph graph) {
        super(graph);
        setName("Set Player Allowed Heroes");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinHero.setNode(this);
        pinHero.setName("Hero");
        addCustomInput(pinHero);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> heroData = pinHero.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");
        handlePinStringConnection(pinHero, heroData, "Hero(Ana)");

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + heroData.getValue().get() + ")");
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
        return "Sets the list of heroes available to one or more players, if a player's current hero becomes unavailable, the player is forced to choose a different hero and respawn at an appropriate spawn location.";
    }
}