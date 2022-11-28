package ovs.graph.node;

import imgui.ImVec4;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinVar;
import ovs.graph.pin.PinVector;

public class NodeVector extends Node{

    PinVector inputPin = new PinVector();
    PinVar outputPin = new PinVar();

    public NodeVector(Graph graph) {
        super(graph);
        setName("Vector");

        inputPin.setNode(this);
        addCustomInput(inputPin);

        outputPin.setNode(this);
        addCustomOutput(outputPin);
    }

    @Override
    public void execute() {
        PinData<ImVec4> inputData = inputPin.getData();
        PinData<ImString> outputData = outputPin.getData();

        if(inputPin.isConnected()){
            Pin connectedPin = inputPin.getConnectedPin();

            PinData<ImVec4> connectedData = connectedPin.getData();

            inputData.getValue().set(new ImVec4(connectedData.getValue().x, connectedData.getValue().y, connectedData.getValue().z, connectedData.getValue().w));
        }

        outputData.getValue().set("Vector(" + inputData.getValue().x + ", " +  inputData.getValue().y + ", " + inputData.getValue().z + ")");
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
