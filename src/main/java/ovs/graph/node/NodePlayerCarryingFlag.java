package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodePlayerCarryingFlag extends Node {

    PinVar pinTeam = new PinVar();
    PinVar output = new PinVar();

    public NodePlayerCarryingFlag(Graph graph) {
        super(graph);
        setName("Player Carrying Flag");

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

        handlePinStringConnection(pinTeam, teamData);

        outputData.getValue().set(getName() + "(" + teamData.getValue().get() + ")");
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
        return "The player carrying a particular team's flag in capture the flag. results in null if no player is carrying the flag.";
    }
}