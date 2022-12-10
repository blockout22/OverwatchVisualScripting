package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinCondition;
import ovs.graph.pin.PinIf;

public class NodeToCondition extends Node{

    PinIf input = new PinIf();

    PinCondition output = new PinCondition();

    public NodeToCondition(Graph graph) {
        super(graph);
        setName("To Condition");

        input.setNode(this);
        addCustomInput(input);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = input.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(input, inputData);

        outputData.getValue().set(inputData.getValue().get() + ";");
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
