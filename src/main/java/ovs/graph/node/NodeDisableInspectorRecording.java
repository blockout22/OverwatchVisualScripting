package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeDisableInspectorRecording extends Node{

    PinAction output = new PinAction();

    public NodeDisableInspectorRecording(Graph graph) {
        super(graph);
        setName("Disable Inspector Recording");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Disable Inspector Recording;");
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
        return "Causes the workshop inspector to stop recording new entries. ths has the benefit or reducing your script's server load, particularly when modifying arrays.";
    }
}
