package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeTotalTimeElapsed extends Node {

    PinVar output = new PinVar();

    public NodeTotalTimeElapsed(Graph graph) {
        super(graph);
        setName("Total Time Elapsed");


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
}