package ovs.graph.node;

import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinFloat;

public class NodeSetMatchTime extends Node{

    PinFloat pinTime = new PinFloat();
    PinAction output = new PinAction();

    public NodeSetMatchTime(Graph graph) {
        super(graph);
        setName("Set Match Time");

        pinTime.setNode(this);
        addCustomInput(pinTime);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImFloat> timeData = pinTime.getData();
        PinData<ImString> outputData = output.getData();

        if(pinTime.isConnected()){
            Pin connectedPin = pinTime.getConnectedPin();

            PinData<ImFloat> connectedData = connectedPin.getData();
            timeData.getValue().set(connectedData.getValue().get());
        }

        outputData.getValue().set("Set Match Time(" + timeData.getValue().get() + ")");
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
