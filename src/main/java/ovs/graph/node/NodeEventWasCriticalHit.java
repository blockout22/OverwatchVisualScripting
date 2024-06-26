package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeEventWasCriticalHit extends Node {

    PinVar output = new PinVar();

    public NodeEventWasCriticalHit(Graph graph) {
        super(graph);
        setName("Event Was Critical Hit");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Event Was Critical Hit");
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
        return "Whether the damage was critical hit(such as a headshot) for the event currently being processed by this rule.";
    }
}