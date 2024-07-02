package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSkip extends Node {

    PinVar pinNumOfActions = new PinVar();
    PinAction output = new PinAction();

    public NodeSkip(Graph graph) {
        super(graph);
        setName("Skip");

        pinNumOfActions.setNode(this);
        pinNumOfActions.setName("Number Of Actions");
        addCustomInput(pinNumOfActions);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> numOfActionsData = pinNumOfActions.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinNumOfActions, numOfActionsData, "1");

        outputData.getValue().set(getName() + "(" + numOfActionsData.getValue().get() + ");");
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
        return "Skips execution of a certain number of actions in the action list.";
    }
}