package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeElseIf extends Node {

    PinVar pinCondition = new PinVar();
    PinAction output = new PinAction();

    public NodeElseIf(Graph graph) {
        super(graph);
        setName("Else If");

        pinCondition.setNode(this);
        pinCondition.setName("Condition");
        addCustomInput(pinCondition);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> conditionData = pinCondition.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinCondition, conditionData);

        outputData.getValue().set("Else If(" + conditionData.getValue().get() + ");");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {

    }
}