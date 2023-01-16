package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeRemoveFromArray extends Node {

    PinVar pinArray = new PinVar();
    PinVar pinValue = new PinVar();
    PinVar output = new PinVar();

    public NodeRemoveFromArray(Graph graph) {
        super(graph);
        setName("Remove From Array");

        pinArray.setNode(this);
        pinArray.setName("Array");
        addCustomInput(pinArray);

        pinValue.setNode(this);
        pinValue.setName("Value");
        addCustomInput(pinValue);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> arrayData = pinArray.getData();
        PinData<ImString> valueData = pinValue.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinArray, arrayData);
        handlePinStringConnection(pinValue, valueData);

        outputData.getValue().set(getName() + "(" + arrayData.getValue().get() + ", " + valueData.getValue().get() + ")");
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