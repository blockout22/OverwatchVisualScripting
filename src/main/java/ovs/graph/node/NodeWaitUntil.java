package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeWaitUntil extends Node {

    PinVar pinContinueCondition = new PinVar();
    PinVar pinTimeout = new PinVar();
    PinAction output = new PinAction();

    public NodeWaitUntil(Graph graph) {
        super(graph);
        setName("Wait Until");

        pinContinueCondition.setNode(this);
        pinContinueCondition.setName("Continue Condition");
        addCustomInput(pinContinueCondition);

        pinTimeout.setNode(this);
        pinTimeout.setName("Timeout");
        addCustomInput(pinTimeout);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> continueConditionData = pinContinueCondition.getData();
        PinData<ImString> timeoutData = pinTimeout.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinContinueCondition, continueConditionData, "False");
        handlePinStringConnection(pinTimeout, timeoutData, "99999");

        outputData.getValue().set(getName() + "(" + continueConditionData.getValue().get() + ", " + timeoutData.getValue().get() + ");");
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
        return "Waits until the continue condition is true or timeout seconds elapse. (the rule's condition is ignored during this wait.).";
    }
}