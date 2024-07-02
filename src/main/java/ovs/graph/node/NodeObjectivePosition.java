package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeObjectivePosition extends Node {

    PinVar pinNumber = new PinVar();
    PinVar output = new PinVar();

    public NodeObjectivePosition(Graph graph) {
        super(graph);
        setName("Objective Position");

        pinNumber.setNode(this);
        pinNumber.setName("Number");
        addCustomInput(pinNumber);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> numberData = pinNumber.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinNumber, numberData);

        outputData.getValue().set(getName() + "(" + numberData.getValue().get() + ")");
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
        return "The position in the world of the specified objective (either a control point, a payload checkpoint, or a payload destination). valid in assault, escort, hybrid, and control.";
    }
}