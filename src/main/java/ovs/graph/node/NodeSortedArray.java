package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeSortedArray extends Node {

    PinVar pinArray = new PinVar();
    PinVar pinRank = new PinVar();
    PinVar output = new PinVar();

    public NodeSortedArray(Graph graph) {
        super(graph);
        setName("Sorted Array");

        pinArray.setNode(this);
        pinArray.setName("Array");
        addCustomInput(pinArray);

        pinRank.setNode(this);
        pinRank.setName("Value Rank");
        addCustomInput(pinRank);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> arrayData = pinArray.getData();
        PinData<ImString> rankData = pinRank.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinArray, arrayData);
        handlePinStringConnection(pinRank, rankData);

        outputData.getValue().set(getName() + "(" + arrayData.getValue().get() + ", " + rankData.getValue().get() + ")");
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