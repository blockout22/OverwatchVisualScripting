package ovs.graph;

import ovs.graph.node.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeParentChildList {

    public Node parent = null;
    public List<Node> children = new ArrayList<>();

    public NodeParentChildList(Node parent) {
        this.parent = parent;
    }
}
