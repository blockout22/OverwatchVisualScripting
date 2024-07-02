package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeHostPlayer extends Node{

    PinVar outputPin = new PinVar();

    public NodeHostPlayer(Graph graph) {
        super(graph);
        setName("Host Player");

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        PinData<ImString> data = outputPin.getData();
        data.getValue().set("Host Player");
    }

    @Override
    public void execute() {
        PinData<ImString> data = outputPin.getData();
        data.getValue().set("Host Player");
    }

    @Override
    public String getOutput()
    {
        PinData<ImString> data = outputPin.getData();
        return data.getValue().get();
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "The player that is currently the host of the custom game. this value will change if the current host player leaves the match.";
    }
}
