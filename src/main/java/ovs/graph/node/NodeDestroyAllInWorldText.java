package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.node.Node;
import ovs.graph.pin.PinAction;

public class NodeDestroyAllInWorldText extends Node {

    PinAction output = new PinAction();

    public NodeDestroyAllInWorldText(Graph graph) {
        super(graph);
        setName("Destroy All In World Text");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Destroy All In-World Text;");

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
        return "Destroys all in-world text created by create in-world text.";
    }
}
