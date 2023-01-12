package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeNumberOfHeroes extends Node {

    PinVar pinHero = new PinVar();
    PinVar pinTeam = new PinVar();
    PinVar output = new PinVar();

    public NodeNumberOfHeroes(Graph graph) {
        super(graph);
        setName("Number Of Heroes");

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

        outputData.getValue().set(getName() + "(" + heroData.getValue().get() + ", " + teamData.getValue().get() + ")");
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