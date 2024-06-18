package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.node.interfaces.NodeDisabled;
import ovs.graph.pin.PinAction;

public class NodeEnd extends Node{

    PinAction output = new PinAction();

    public NodeEnd(Graph graph) {
        super(graph);
        setName("End");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();
        outputData.getValue().set("End;");
    }

    @Override
    public String getOutput() {
        return "End;";
    }

    @Override
    public void UI() {

    }
}
