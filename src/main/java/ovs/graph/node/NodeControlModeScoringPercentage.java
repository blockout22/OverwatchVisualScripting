package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeControlModeScoringPercentage extends Node {

    PinVar team = new PinVar();
    PinVar output = new PinVar();

    public NodeControlModeScoringPercentage(Graph graph) {
        super(graph);
        setName("Control Mode Scoring Percentage");

        team.setNode(this);
        team.setName("Team");
        addCustomInput(team);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();
        PinData<ImString> teamData = team.getData();

        handlePinStringConnection(team, teamData);

        outputData.getValue().set("Control Mode Scoring Percentage(" + teamData.getValue().get() + ")");
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