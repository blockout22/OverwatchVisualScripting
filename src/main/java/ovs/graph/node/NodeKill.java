package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeKill extends Node{

    PinAction output = new PinAction();

    public NodeKill(Graph graph) {
        super(graph);
        setName("Kill");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> data = output.getData();

        data.getValue().set("Kill(Event Player, Null);");
    }

    @Override
    public String getOutput() {
        PinData<ImString> data = output.getData();
        return data.getValue().get();
    }

    @Override
    public void UI() {

    }
}
