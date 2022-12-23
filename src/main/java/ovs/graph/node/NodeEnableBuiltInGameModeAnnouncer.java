package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeEnableBuiltInGameModeAnnouncer extends Node{

    PinAction output = new PinAction();

    public NodeEnableBuiltInGameModeAnnouncer(Graph graph) {
        super(graph);
        setName("Enable Built In Game Mode Announcer");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Enable Built-In Game Mode Announcer;");

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
