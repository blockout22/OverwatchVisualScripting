package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinVar;

public class NodeDivide extends Node{

    PinVar input1 = new PinVar();
    PinVar input2 = new PinVar();

    PinVar output = new PinVar();

    public NodeDivide(Graph graph) {
        super(graph);
        setName("Divide");

        input1.setNode(this);
        addCustomInput(input1);

        input2.setNode(this);
        addCustomInput(input2);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> input1Data = input1.getData();
        PinData<ImString> input2Data = input2.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(input1, input1Data);
        handlePinStringConnection(input2, input2Data);

        outputData.getValue().set(input1Data.getValue().get() +" / " + input2Data.getValue().get());
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
        return "The ratio of two numbers or vectors. a vector divided by a number will yield a scaled vector, division by zero results in zero.";
    }
}
