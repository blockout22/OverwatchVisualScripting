package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeIsObjectiveComplete extends Node {

    PinVar pinNumber = new PinVar();
    PinVar output = new PinVar();

    public NodeIsObjectiveComplete(Graph graph) {
        super(graph);
        setName("Is Objective Complete");

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

        outputData.getValue().set("Is Objective Complete(" + numberData.getValue().get() + ")");
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