package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeDisableBuiltInGameModeCompletion extends Node{

    PinAction output = new PinAction();

    public NodeDisableBuiltInGameModeCompletion(Graph graph) {
        super(graph);
        setName("Disable Built-In Game Mode Completion");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Disable Built-In Game Mode Completion;");
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
        return "Disables completion of the match from the game mode itself, only allowing the match to be completed by scripting commands.";
    }
}
