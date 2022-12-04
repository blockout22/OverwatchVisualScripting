package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeRandomReal extends Node{

    PinVar pinMin = new PinVar();
    PinVar pinMax = new PinVar();
    PinVar output = new PinVar();

    public NodeRandomReal(Graph graph) {
        super(graph);
        setName("Random Real");

        pinMin.setNode(this);
        pinMin.setName("Min");
        addCustomInput(pinMin);

        pinMax.setNode(this);
        pinMax.setName("Max");
        addCustomInput(pinMax);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> minData = pinMin.getData();
        PinData<ImString> maxData = pinMax.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinMin, minData, "0");
        handlePinStringConnection(pinMax, maxData, "0");

        outputData.getValue().set("Random Real(" + minData.getValue().get() + ", " + maxData.getValue().get() + ")");
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
