package ovs.graph.node;

import ovs.graph.Graph;
import ovs.graph.pin.PinString;

public class NodeRule extends Node{

    private PinString pinString, pinString2;

    public NodeRule(Graph graph) {
        super(graph);
        setName("Rule");

        pinString = new PinString();
        pinString.setNode(this);
        addCustomInput(pinString);

        pinString = new PinString();
        pinString.setNode(this);
        addCustomInput(pinString);

        pinString2 = new PinString();
        pinString2.setNode(this);
        addCustomOutput(pinString2);
    }
}
