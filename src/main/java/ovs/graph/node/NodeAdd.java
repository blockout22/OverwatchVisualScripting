package ovs.graph.node;

import imgui.ImGui;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinVar;

public class NodeAdd extends Node{

    PinVar input1 = new PinVar();
    PinVar input2 = new PinVar();

    PinVar output = new PinVar();

    public NodeAdd(Graph graph) {
        super(graph);
        setName("Add");

        input1.setNode(this);
        addCustomInput(input1);

        input2.setNode(this);
        addCustomInput(input2);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> input1Data = input1.getData();
        PinData<ImString> input2Data = input2.getData();
        PinData<ImString> outputData = output.getData();

        if(input1.isConnected()){
            Pin connectedPin = input1.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            input1Data.getValue().set(connectedData.getValue().get());
        }

        if(input2.isConnected()){
            Pin connectedPin = input2.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            input2Data.getValue().set(connectedData.getValue().get());
        }

        outputData.getValue().set(input1Data.getValue().get() +" + " + input2Data.getValue().get());
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
