package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeDisableBuiltInGameModeMusic extends Node{

    PinAction output = new PinAction();

    public NodeDisableBuiltInGameModeMusic(Graph graph) {
        super(graph);
        setName("Disable Built-In Game Mode Music");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Disable Built-In Game Mode Music;");
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
        return "Disabled all game mode music until reeanbled or the match ends.";
    }
}
