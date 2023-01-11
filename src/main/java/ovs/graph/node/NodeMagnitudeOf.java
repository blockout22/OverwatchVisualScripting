package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeMagnitudeOf extends Node {

    PinVar pinVector = new PinVar();
    PinVar output = new PinVar();

    public NodeMagnitudeOf(Graph graph) {
        super(graph);
        setName("Magnitude Of");

        pinVector.setNode(this);
        pinVector.setName("Vector");
        addCustomInput(pinVector);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> vecctorData = pinVector.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVector, vecctorData);

        outputData.getValue().set(getName() + "(" + vecctorData.getValue().get() + ")");
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