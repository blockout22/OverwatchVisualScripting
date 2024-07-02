package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeBreak extends Node{

    PinAction output = new PinAction();

    public NodeBreak(Graph graph) {
        super(graph);
        setName("Break");
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Break;");
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
        return "Denotes an action that will abort execution of the current while or for loop. execution jumps to the next action after the end action.";
    }
}
