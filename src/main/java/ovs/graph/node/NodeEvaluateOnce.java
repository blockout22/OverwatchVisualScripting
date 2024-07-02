package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeEvaluateOnce extends Node {

    PinVar input = new PinVar();
    PinVar output = new PinVar();

    public NodeEvaluateOnce(Graph graph) {
        super(graph);
        setName("Evaluate Once");

        input.setNode(this);
        input.setName("Input Value");
        addCustomInput(input);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = input.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(input, inputData);

        outputData.getValue().set("Evaluate Once(" + inputData.getValue().get() + ")");
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
        return "Results in the first value that was provided to the input value parameter for the given action or condition (useful for selectively not reevaluating certain parts of a value when used in conjunction with an action that is capable of reevaluation).";
    }
}