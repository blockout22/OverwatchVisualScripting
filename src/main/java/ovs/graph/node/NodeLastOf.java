package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeLastOf extends Node {

    PinVar pinArray = new PinVar();
    PinVar output = new PinVar();

    public NodeLastOf(Graph graph) {
        super(graph);
        setName("Last Of");

        pinArray.setNode(this);
        pinArray.setName("Array");
        addCustomInput(pinArray);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> arrayData = pinArray.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinArray, arrayData);

        outputData.getValue().set(getName() + "(" + arrayData.getValue().get() + ")");
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
        return "The value at the end of the specified array. results in 0 if the specified array is empty.";
    }
}