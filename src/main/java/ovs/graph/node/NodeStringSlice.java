package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeStringSlice extends Node {

    PinVar pinString = new PinVar();
    PinVar pinStartCharIndex = new PinVar();
    PinVar pinStringLength = new PinVar();
    PinVar output = new PinVar();

    public NodeStringSlice(Graph graph) {
        super(graph);
        setName("String Slice");

        pinString.setNode(this);
        pinString.setName("String");
        addCustomInput(pinString);

        pinStartCharIndex.setNode(this);
        pinStartCharIndex.setName("Start Character Index");
        addCustomInput(pinStartCharIndex);

        pinStringLength.setNode(this);
        pinStringLength.setName("String Length");
        addCustomInput(pinStringLength);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> stringData = pinString.getData();
        PinData<ImString> startCharIndexData = pinStartCharIndex.getData();
        PinData<ImString> stringLengthData = pinStringLength.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinString, stringData);
        handlePinStringConnection(pinStartCharIndex, startCharIndexData);
        handlePinStringConnection(pinStringLength, stringLengthData);

        outputData.getValue().set(getName() + "(" + stringData.getValue().get() + ", " + startCharIndexData.getValue().get() + ", " + stringLengthData.getValue().get() + ")");
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
        return "Results in the specified substring of the string value.";
    }
}