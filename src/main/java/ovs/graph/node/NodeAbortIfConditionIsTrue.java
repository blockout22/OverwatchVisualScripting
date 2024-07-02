package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeAbortIfConditionIsTrue extends Node{

    PinAction output = new PinAction();

    public NodeAbortIfConditionIsTrue(Graph graph) {
        super(graph);
        setName("Abort If Condition Is True");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Abort If Condition Is True;");
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
        return "Stops execution of the action list if all conditions in the condition list are true. if any are false, execution continues with the next action.";
    }
}
