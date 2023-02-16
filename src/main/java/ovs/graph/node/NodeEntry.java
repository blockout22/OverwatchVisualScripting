package ovs.graph.node;

import ovs.graph.Graph;
import ovs.graph.node.interfaces.NodeDisabled;

@NodeDisabled
public abstract class NodeEntry extends Node{

    public NodeEntry(Graph graph) {
        super(graph);
    }
}
