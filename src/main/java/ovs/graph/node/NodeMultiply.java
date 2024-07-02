package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeMultiply extends Node {

    PinVar pinValue1 = new PinVar();
    PinVar pinValue2 = new PinVar();
    PinVar output = new PinVar();

    public NodeMultiply(Graph graph) {
        super(graph);
        setName("Multiply");

        pinValue1.setNode(this);
        pinValue1.setName("Value");
        addCustomInput(pinValue1);

        pinValue2.setNode(this);
        pinValue2.setName("Value");
        addCustomInput(pinValue2);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> value1Data = pinValue1.getData();
        PinData<ImString> value2Data = pinValue2.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinValue1, value1Data);
        handlePinStringConnection(pinValue2, value2Data);

        outputData.getValue().set(value1Data.getValue().get() + " * " + value2Data.getValue().get());
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
        return "The product of two numbers or vectors. a vector multiplied by a number will yield a scaled vector.";
    }
}