package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeEnableBuiltInGameModeScoring extends Node{

    PinAction output = new PinAction();

    public NodeEnableBuiltInGameModeScoring(Graph graph) {
        super(graph);
        setName("Enable Built In Game Mode Scoring");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Enable Built-In Game Mode Scoring;");

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
        return "Undoes the effect of the disable built-in game mode scoring action.";
    }
}
