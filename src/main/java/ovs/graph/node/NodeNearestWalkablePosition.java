package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeNearestWalkablePosition extends Node {

    PinVar pinPosition = new PinVar();
    PinVar output = new PinVar();

    public NodeNearestWalkablePosition(Graph graph) {
        super(graph);
        setName("Nearest Walkable Position");

        pinPosition.setNode(this);
        pinPosition.setName("Position");
        addCustomInput(pinPosition);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> positionData = pinPosition.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPosition, positionData);

        outputData.getValue().set(getName() + "(" + positionData.getValue().get() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {

    }
}