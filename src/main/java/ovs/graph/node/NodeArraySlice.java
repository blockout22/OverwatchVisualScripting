package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeArraySlice extends Node {

    PinVar pinArray = new PinVar();
    PinVar pinStart = new PinVar();
    PinVar pinCount = new PinVar();
    PinVar output = new PinVar();

    public NodeArraySlice(Graph graph) {
        super(graph);
        setName("Array Slice");

        pinArray.setNode(this);
        pinArray.setName("Array");
        addCustomInput(pinArray);

        pinStart.setNode(this);
        pinStart.setName("Start Index");
        addCustomInput(pinStart);

        pinCount.setNode(this);
        pinCount.setName("Count");
        addCustomInput(pinCount);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> arrayData = pinArray.getData();
        PinData<ImString> startData = pinStart.getData();
        PinData<ImString> countData = pinCount.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinArray, arrayData);
        handlePinStringConnection(pinStart, startData, "0");
        handlePinStringConnection(pinCount, countData, "0");

        outputData.getValue().set("Array Slice(" + arrayData.getValue().get() + ", " + pinStart.getData().getValue() + ", " + pinCount.getData().getValue() + ")");
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
        return "A copy of the specified array containing only values from a specified index range.";
    }
}