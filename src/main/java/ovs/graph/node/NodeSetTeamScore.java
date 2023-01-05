package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetTeamScore extends Node {

    PinVar pinTeam = new PinVar();
    PinVar pinScore = new PinVar();
    PinAction output = new PinAction();

    public NodeSetTeamScore(Graph graph) {
        super(graph);
        setName("Set Team Score");

        pinTeam.setNode(this);
        pinTeam.setName("Team");
        addCustomInput(pinTeam);

        pinScore.setNode(this);
        pinScore.setName("Score");
        addCustomInput(pinScore);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> teamData = pinTeam.getData();
        PinData<ImString> scoreData = pinScore.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinTeam, teamData);
        handlePinStringConnection(pinScore, scoreData, "0");

        outputData.getValue().set("Set Team Score(" + teamData.getValue().get() + ", " + scoreData.getValue().get() + ");");
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