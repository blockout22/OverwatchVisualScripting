package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeFilteredArray extends Node {

    PinVar pinArray = new PinVar();
    PinVar pinCondition = new PinVar();
    PinVar output = new PinVar();

    public NodeFilteredArray(Graph graph) {
        super(graph);
        setName("Filtered Array");

        pinArray.setNode(this);
        pinArray.setName("Array");
        addCustomInput(pinArray);

        pinCondition.setNode(this);
        pinCondition.setName("Condition");
        addCustomInput(pinCondition);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> arrayData = pinArray.getData();
        PinData<ImString> conditionData = pinCondition.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinArray, arrayData, "All Players(All Teams)");
        handlePinStringConnection(pinCondition, conditionData, "0 == 0");

        outputData.getValue().set(getName() + "(" + arrayData.getValue().get() + ", " + conditionData.getValue().get() + ")");
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
        return "A copy of a specified array with any values that do not match the specified condition removed.";
    }
}