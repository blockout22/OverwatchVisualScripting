package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeStartGameMode extends Node{

    PinAction output = new PinAction();

    public NodeStartGameMode(Graph graph) {
        super(graph);
        setName("Start Game Mode");
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Start Game Mode;");
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
        return "Starts the game mode. this action doesn't have an effect if the game is already in progress";
    }
}
