package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;

public class NodeDestroyAllProgressBarInWorldText extends Node{

    PinAction output = new PinAction();

    public NodeDestroyAllProgressBarInWorldText(Graph graph) {
        super(graph);
        setName("Destroy All Progress Bar In World Text");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Destroy All Progress Bar In-World Text;");

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
        return "Destroys all progress bar in-world text that were created by the create progress bar in-world text action.";
    }
}
