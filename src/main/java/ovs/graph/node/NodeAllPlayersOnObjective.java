package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeAllPlayersOnObjective extends Node {

    PinVar pinTeam = new PinVar();
    PinVar output = new PinVar();

    public NodeAllPlayersOnObjective(Graph graph) {
        super(graph);
        setName("All Players On Objective");

        pinTeam.setNode(this);
        pinTeam.setName("Team");
        addCustomInput(pinTeam);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> teamData = pinTeam.getData();
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("All Players On Objective(" + teamData.getValue().get() + ")");
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
        return "An array containing all players occupying a payload or control point (either on a team or in the match).";
    }
}