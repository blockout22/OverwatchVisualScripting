package ovs.graph.node;

import imgui.type.ImFloat;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinFloat;

public class NodeWait extends Node{

    PinFloat inputPin;
    PinAction outputPin;

    PinData<ImFloat> data;

    public NodeWait(Graph graph){
        super(graph);
        setName("Wait");

        inputPin = new PinFloat();
        inputPin.setNode(this);
        addCustomInput(inputPin);

        data = inputPin.getData();

        outputPin = new PinAction();
        outputPin.setNode(this);
        addCustomOutput(outputPin);
    }

    @Override
    public void execute() {

    }

    @Override
    public String getOutput() {

        return "Wait(" + data.getValue() + ", Ignore Condition);";
    }

    @Override
    public void UI() {

    }
}
