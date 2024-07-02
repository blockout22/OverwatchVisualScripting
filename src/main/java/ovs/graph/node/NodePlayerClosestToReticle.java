package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodePlayerClosestToReticle extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinTeam = new PinVar();
    PinVar output = new PinVar();

    public NodePlayerClosestToReticle(Graph graph) {
        super(graph);
        setName("Player Closest To Reticle");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinTeam.setNode(this);
        pinTeam.setName("Team");
        addCustomInput(pinTeam);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> teamData = pinTeam.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinTeam, teamData);

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + teamData.getValue().get() + ")");
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
        return "The player closest to the reticle of the specified player, optionally restricted by team.";
    }
}