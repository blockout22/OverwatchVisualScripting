package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeTextCount extends Node {

    PinVar output = new PinVar();

    public NodeTextCount(Graph graph) {
        super(graph);
        setName("Text Count");


        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();


        outputData.getValue().set(getName());
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
        return "The current number of text instances started from the create hud text, create in-world text, create progress bar hud text, or create progress bar in-world text action.";
    }
}