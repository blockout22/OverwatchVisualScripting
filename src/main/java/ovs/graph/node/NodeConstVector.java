package ovs.graph.node;

import imgui.ImVec4;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinVar;
import ovs.graph.pin.PinVector;

public class NodeConstVector extends Node{

    PinVector inputPin = new PinVector();
    PinVar outputPin = new PinVar();

    public NodeConstVector(Graph graph) {
        super(graph);
        setName("Const Vector");

        inputPin.setNode(this);
        addCustomInput(inputPin);

        outputPin.setNode(this);
        addCustomOutput(outputPin);
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeConstVector){
            PinData<ImVec4> nodeData = ((NodeConstVector) node).inputPin.getData();
            PinData<ImVec4> data = inputPin.getData();

            data.getValue().set(nodeData.getValue().x, nodeData.getValue().y, nodeData.getValue().y, nodeData.getValue().w);
        }
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
