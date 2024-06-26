package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeIsFlagBeingCarried extends Node {

    PinVar pinTeam = new PinVar();
    PinVar output = new PinVar();

    public NodeIsFlagBeingCarried(Graph graph) {
        super(graph);
        setName("Is Flag Being Carried");

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

        outputData.getValue().set("Is Flag Being Carried(" + teamData.getValue().get() + ")");
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
        return "Whether a specific team's flag is being carried by a member of the opposing team in capture the flag.";
    }
}