package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeValueInArray extends Node {

    PinVar pinArray = new PinVar();
    PinVar pinIndex = new PinVar();
    PinVar output = new PinVar();

    public NodeValueInArray(Graph graph) {
        super(graph);
        setName("Value In Array");

        pinArray.setNode(this);
        pinArray.setName("Array");
        addCustomInput(pinArray);

        pinIndex.setNode(this);
        pinIndex.setName("Index");
        addCustomInput(pinIndex);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> arrayData = pinArray.getData();
        PinData<ImString> indexData = pinIndex.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinArray, arrayData);
        handlePinStringConnection(pinIndex, indexData, "0");

        outputData.getValue().set(getName() + "(" + arrayData.getValue().get() + ", " + indexData.getValue().get() + ")");
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
        return "The value found at a specific element of an array. results in 0 if the element does not exist.";
    }
}