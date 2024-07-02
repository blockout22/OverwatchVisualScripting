package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinVar;

public class NodeDistanceBetween extends Node{

    PinVar input1 = new PinVar();
    PinVar input2 = new PinVar();

    PinVar output = new PinVar();

    public NodeDistanceBetween(Graph graph) {
        super(graph);
        setName("Distance Between");

        input1.setNode(this);
        input1.setName("Start Position");
        addCustomInput(input1);

        input2.setNode(this);
        input2.setName("End Position");
        addCustomInput(input2);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData1 = input1.getData();
        PinData<ImString> inputData2 = input2.getData();
        PinData<ImString> outputData = output.getData();

        if(input1.isConnected()){
            Pin connectedPin = input1.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            inputData1.getValue().set(connectedData.getValue().get());
        }else {

        }

        if(input2.isConnected()){
            Pin connectedPin = input2.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            inputData2.getValue().set(connectedData.getValue().get());
        }else {

        }

        outputData.getValue().set("Distance Between(" + inputData1.getValue().get() + ", " + inputData2.getValue().get() + ")");

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
        return "The distance between two positions. in meters.";
    }
}
