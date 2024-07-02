package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeServerLoad extends Node {

    PinVar output = new PinVar();

    public NodeServerLoad(Graph graph) {
        super(graph);
        setName("Server Load");

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
        return "Provides a percentage representing the cpu load of the current game instance. as this number approaches or exceeds 100, it becomes increasingly likely that the instance will be shut down because it is consuming too many resources.";
    }
}