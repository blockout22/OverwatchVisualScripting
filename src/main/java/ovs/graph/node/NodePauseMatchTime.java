package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodePauseMatchTime extends Node{

    PinAction output = new PinAction();

    public NodePauseMatchTime(Graph graph) {
        super(graph);
        setName("Pause Match Time");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Pause Match Time;");
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
        return "Pauses the match time. players, objective logic, and game mode advancement criteria are unaffected by this pause.";
    }
}
