package ovs.graph.node;

import ovs.graph.Graph;
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

    }

    @Override
    public String getOutput() {
        return "End;";
    }

    @Override
    public void UI() {

    }
}
