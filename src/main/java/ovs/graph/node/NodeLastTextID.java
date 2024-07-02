package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeLastTextID extends Node{

    PinVar output = new PinVar();

    public NodeLastTextID(Graph graph) {
        super(graph);
        setName("Last Text ID");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Last Text ID");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "A reference to the last piece of text created by the event player (or created at the global level) via the create hud text, create in-world text, create progress bar hud text, or create progress bar in-world text action.";
    }
}
