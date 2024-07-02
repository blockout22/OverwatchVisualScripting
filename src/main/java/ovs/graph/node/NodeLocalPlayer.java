package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeLocalPlayer extends Node {

    PinVar output = new PinVar();

    public NodeLocalPlayer(Graph graph) {
        super(graph);
        setName("Local Player");

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
        return "The player being controlled on the end user's device. this value is different for each end user and this can only be accessed in action which affect visuals or the hud, this value cannot be stored in variables.";
    }
}