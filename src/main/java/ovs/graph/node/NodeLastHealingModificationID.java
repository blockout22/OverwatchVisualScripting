package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeLastHealingModificationID extends Node {

    PinVar output = new PinVar();

    public NodeLastHealingModificationID(Graph graph) {
        super(graph);
        setName("Last Healing Modification ID");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Last Healing Modification ID");
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
        return "An id representing the most recent start healing modification action that was executed by the event player (or executed at the global level).";
    }
}