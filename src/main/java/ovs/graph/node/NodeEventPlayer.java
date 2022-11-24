package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeEventPlayer extends Node{

    PinVar outputPin = new PinVar();

    public NodeEventPlayer(Graph graph) {
        super(graph);
        setName("Event Player");

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        PinData<ImString> data = outputPin.getData();
        data.getValue().set("Event Player");
    }

    @Override
    public void execute() {

    }

    @Override
    public String getOutput() {
        return "Event Player";
    }

    @Override
    public void UI() {

    }
}
