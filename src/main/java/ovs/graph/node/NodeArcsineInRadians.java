package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeArcsineInRadians extends Node {

    PinVar input = new PinVar();
    PinVar output = new PinVar();

    public NodeArcsineInRadians(Graph graph) {
        super(graph);
        setName("Arcsine In Radians");

        input.setNode(this);
        input.setName("Value");
        addCustomInput(input);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = input.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(input, inputData);

        outputData.getValue().set("Arcsine In Radians(" + inputData.getValue().get() + ")");
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
        return "Arcsine in radians of the specified value.";
    }
}