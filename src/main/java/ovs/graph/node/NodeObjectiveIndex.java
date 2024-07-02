package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeObjectiveIndex extends Node {

    PinVar output = new PinVar();

    public NodeObjectiveIndex(Graph graph) {
        super(graph);
        setName("Objective Index");

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
        return "The control point, payload checkpoint, or payload destination currently active (either 0, 1, or 2). valid in assault, escort, hybrid, and control.";
    }
}