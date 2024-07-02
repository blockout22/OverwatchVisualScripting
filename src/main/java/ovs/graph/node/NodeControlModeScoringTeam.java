package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeControlModeScoringTeam extends Node {

    PinVar output = new PinVar();

    public NodeControlModeScoringTeam(Graph graph) {
        super(graph);
        setName("Control Mode Scoring Team");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Control Mode Scoring Team");
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
        return "The team that is currently accumulating score percentage in control mode. results in all if neither team is accumulating score.";
    }
}