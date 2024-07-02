package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeAbortIfConditionIsFalse extends Node{

    PinAction output = new PinAction();

    public NodeAbortIfConditionIsFalse(Graph graph) {
        super(graph);
        setName("Abort If Condition Is False");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Abort If Condition Is False;");
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
        return "Stops execution of the action list if at least one condition in the condition list is false. if all condition are true, execution continues with the next action.";
    }
}
