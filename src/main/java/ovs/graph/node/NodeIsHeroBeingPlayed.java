package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeIsHeroBeingPlayed extends Node {

    PinVar pinHero = new PinVar();
    PinVar pinTeam = new PinVar();
    PinVar output = new PinVar();

    public NodeIsHeroBeingPlayed(Graph graph) {
        super(graph);
        setName("Is Hero Being Played");

        pinHero.setNode(this);
        pinHero.setName("Hero");
        addCustomInput(pinHero);

        pinTeam.setNode(this);
        pinTeam.setName("Team");
        addCustomInput(pinTeam);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> heroData = pinHero.getData();
        PinData<ImString> teamData = pinTeam.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinHero, heroData);
        handlePinStringConnection(pinTeam, teamData);

        outputData.getValue().set("Is Hero Being Played(" + teamData.getValue().get() + ", " + heroData.getValue().get() + ")");
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
        return "Whether a specific hero is being played (either on a team or in the match).";
    }
}