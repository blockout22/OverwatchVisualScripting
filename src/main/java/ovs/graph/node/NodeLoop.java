package ovs.graph.node;

import ovs.graph.Graph;
import ovs.graph.pin.PinAction;

public class NodeLoop extends Node{

    private PinAction output = new PinAction();

    public NodeLoop(Graph graph) {
        super(graph);
        setName("Loop");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {

    }

    @Override
    public String getOutput() {
        return "Loop;";
    }

    @Override
    public void UI() {

    }
}
