package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSkipIf extends Node {

    PinVar pinCondition = new PinVar();
    PinVar pinNumOfActions = new PinVar();
    PinAction output = new PinAction();

    public NodeSkipIf(Graph graph) {
        super(graph);
        setName("Skip If");

        pinCondition.setNode(this);
        pinCondition.setName("Condition");
        addCustomInput(pinCondition);

        pinNumOfActions.setNode(this);
        pinNumOfActions.setName("Number Of Actions");
        addCustomInput(pinNumOfActions);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> conditionData = pinCondition.getData();
        PinData<ImString> numOfActionsData = pinNumOfActions.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinCondition, conditionData);
        handlePinStringConnection(pinNumOfActions, numOfActionsData, "1");

        outputData.getValue().set(getName() + "(" + conditionData.getValue().get() + ", " + numOfActionsData.getValue().get() + ");");
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
        return "Skips execution of a certain number of actions in the action list if this action's condition evaluates to true. if it does not, execution continues with the next action.";
    }
}