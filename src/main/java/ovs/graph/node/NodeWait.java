package ovs.graph.node;

import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
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
        PinData<ImString> inputData = inputPin.getData();
        PinData<ImString> outputData = outputPin.getData();

        if(inputPin.isConnected()){
            Pin connectedPin = inputPin.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();

            inputData.getValue().set(connectedData.getValue().get());
        }

        outputData.getValue().set("Wait(" + data.getValue() + ", Ignore Condition);");
    }

    @Override
    public String getOutput() {
        PinData<ImString> data = outputPin.getData();
        return data.getValue().get();
    }

    @Override
    public void UI() {

    }
}
