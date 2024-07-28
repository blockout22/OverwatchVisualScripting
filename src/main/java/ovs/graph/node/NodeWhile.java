package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeWhile extends Node{

//    ComboBox box = new ComboBox();

    PinVar pinCondition = new PinVar();
//    PinVar rightPin = new PinVar();

    PinAction actionPin = new PinAction();

    PinAction output = new PinAction();
    public NodeWhile(Graph graph) {
        super(graph);
        setName("While");

        pinCondition.setNode(this);
        pinCondition.setName("Condition");
        addCustomInput(pinCondition);

        actionPin.setNode(this);
        addCustomInput(actionPin);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {

        PinData<ImString> conditionData = pinCondition.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinCondition, conditionData);

        outputData.getValue().set(getName() + "(" + conditionData.getValue().get() + ");");
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
        return "Denotes the beginning of the series of actions that will execute in a loop as long as the specified condition is true. the next end action at the current level denotes the end of the loop, if the condition evaluates to false when execution is at the top of the loop, then the loop exits, and execution jumps to the next action after the end action.";
    }
}
