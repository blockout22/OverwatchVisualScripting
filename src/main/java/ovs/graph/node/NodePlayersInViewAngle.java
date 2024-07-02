package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodePlayersInViewAngle extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinTeam = new PinVar();
    PinVar pinViewAngle = new PinVar();
    PinVar output = new PinVar();

    public NodePlayersInViewAngle(Graph graph) {
        super(graph);
        setName("Players In View Angle");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinTeam.setNode(this);
        pinTeam.setName("Team");
        addCustomInput(pinTeam);

        pinViewAngle.setNode(this);
        pinViewAngle.setName("View Angle");
        addCustomInput(pinViewAngle);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> teamData = pinTeam.getData();
        PinData<ImString> viewAngleData = pinViewAngle.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinTeam, teamData);
        handlePinStringConnection(pinViewAngle, viewAngleData);

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + teamData.getValue().get() + ", " + viewAngleData.getValue().get() + ")");
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
        return "The players who are within a specific view angle of a specific player's reticle, optionally restricted by team.";
    }
}