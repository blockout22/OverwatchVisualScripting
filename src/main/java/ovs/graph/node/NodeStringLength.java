package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeStringLength extends Node {

    PinVar pinString = new PinVar();
    PinVar output = new PinVar();

    public NodeStringLength(Graph graph) {
        super(graph);
        setName("String Length");

        pinString.setNode(this);
        pinString.setName("String");
        addCustomInput(pinString);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> stringData = pinString.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinString, stringData);

        outputData.getValue().set(getName() + "(" + stringData.getValue().get() + ")");
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
        return "Results in the number of characters in the string value (or 0 if the value is not a string).";
    }
}