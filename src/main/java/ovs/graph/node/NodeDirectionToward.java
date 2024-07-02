package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeDirectionToward extends Node {

    PinVar pinStartPos = new PinVar();
    PinVar pinEndPos = new PinVar();
    PinVar output = new PinVar();

    public NodeDirectionToward(Graph graph) {
        super(graph);
        setName("Direction Towards");

        pinStartPos.setNode(this);
        pinStartPos.setName("Start Position");
        addCustomInput(pinStartPos);

        pinEndPos.setNode(this);
        pinEndPos.setName("End Position");
        addCustomInput(pinEndPos);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> startData = pinStartPos.getData();
        PinData<ImString> endData = pinEndPos.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinStartPos, startData);
        handlePinStringConnection(pinEndPos, endData);

        outputData.getValue().set("Direction Towards(" + startData.getValue().get() + ", " + endData.getValue().get() + ")");
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
        return "The unit-length direction vector from one position to another.";
    }
}