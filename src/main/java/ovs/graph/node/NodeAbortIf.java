package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeAbortIf extends Node{

    PinVar inputPin = new PinVar();

    PinAction output = new PinAction();

    public NodeAbortIf(Graph graph) {
        super(graph);
        setName("Abort If");

        inputPin.setNode(this);
        addCustomInput(inputPin);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = inputPin.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(inputPin, inputData);

        outputData.getValue().set("Abort If(" + inputData.getValue().get() + ");");
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
