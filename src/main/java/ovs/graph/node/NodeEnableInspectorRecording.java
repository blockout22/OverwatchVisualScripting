package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeEnableInspectorRecording extends Node{

    PinAction output = new PinAction();

    public NodeEnableInspectorRecording(Graph graph) {
        super(graph);
        setName("Enable Inspector Recording");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Enable Spectator Recording;");

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
        return "causes the workshop inspector to resume recoding new entries (in case i had been disabled earlier). enabling recording at specific times may make it easier to debug problematic areas in your logic.";
    }
}
