package ovs.graph.node;

import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinFloat;
import ovs.graph.pin.PinVar;

public class NodeNumber extends Node{

    PinFloat input = new PinFloat();
    PinVar output = new PinVar();

    public NodeNumber(Graph graph) {
        super(graph);
        setName("Number");

        input.setNode(this);
        addCustomInput(input);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeNumber){
            PinData<ImFloat> nodeData = ((NodeNumber) node).input.getData();
            PinData<ImFloat> data = input.getData();

            data.getValue().set(nodeData.getValue().get());
        }
    }

    @Override
    public void execute() {
        PinData<ImFloat> inputData = input.getData();
        PinData<ImString> outputData = output.getData();

        if(input.isConnected()){
            Pin connectedPin = input.getConnectedPin();

            PinData<ImFloat> connectedData = connectedPin.getData();
            inputData.getValue().set(connectedData.getValue().get());
        }

        outputData.getValue().set(inputData.getValue().get());
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
