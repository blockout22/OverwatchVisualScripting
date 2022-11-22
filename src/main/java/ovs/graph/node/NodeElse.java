package ovs.graph.node;

import ovs.graph.Graph;

public class NodeElse extends Node{
    public NodeElse(Graph graph) {
        super(graph);
        setName("Else");
    }

    @Override
    public void execute() {

    }

    @Override
    public String getOutput() {
        return "Else;";
    }

    @Override
    public void UI() {

    }
}
