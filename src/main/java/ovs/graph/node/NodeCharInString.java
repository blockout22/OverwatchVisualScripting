package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeCharInString extends Node {

    PinVar pinString = new PinVar();
    PinVar pinIndex = new PinVar();
    PinVar output = new PinVar();

    public NodeCharInString(Graph graph) {
        super(graph);
        setName("Char In String");

        pinString.setNode(this);
        pinString.setName("String");
        addCustomInput(pinString);

        pinIndex.setNode(this);
        pinIndex.setName("Index");
        addCustomInput(pinIndex);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> stringData = pinString.getData();
        PinData<ImString> indexData = pinIndex.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinString, stringData);
        handlePinStringConnection(pinIndex, indexData);

        outputData.getValue().set("Char In String(" + stringData.getValue().get() + ", " + indexData.getValue().get() + ")");
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
        return "The character fount at a specified index of a string.";
    }
}