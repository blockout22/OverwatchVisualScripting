package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeCurrentMap extends Node{

    private PinVar output = new PinVar();

    public NodeCurrentMap(Graph graph) {
        super(graph);
        setName("Current Map");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> data = output.getData();

        data.getValue().set("Current Map");
    }

    @Override
    public String getOutput() {
        PinData<ImString> data = output.getData();
        return data.getValue().get();
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "The current map of the custom game.";
    }
}
