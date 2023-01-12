package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeNormalize extends Node {

    PinVar pinVector = new PinVar();
    PinVar output = new PinVar();

    public NodeNormalize(Graph graph) {
        super(graph);
        setName("Normalize");

        pinVector.setNode(this);
        pinVector.setName("Vector");
        addCustomInput(pinVector);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> vectorData = pinVector.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVector, vectorData);

        outputData.getValue().set(getName() + "(" + vectorData.getValue().get() + ")");
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