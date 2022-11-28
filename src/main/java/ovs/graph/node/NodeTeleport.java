package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeTeleport extends Node{

    //who to teleport
    PinVar inputPin = new PinVar();
    //Where to teleport inputPin
    PinVar inputPin2 = new PinVar();
    PinAction outputPin = new PinAction();

    public NodeTeleport(Graph graph) {
        super(graph);
        setName("Teleport");

        inputPin.setNode(this);
        addCustomInput(inputPin);

        inputPin2.setNode(this);
        addCustomInput(inputPin2);

        outputPin.setNode(this);
        addCustomOutput(outputPin);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = inputPin.getData();
        PinData<ImString> inputData2 = inputPin2.getData();
        PinData<ImString> outputData = outputPin.getData();

        if(inputPin.isConnected())
        {
            Pin connectedPin = inputPin.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();

            inputData.getValue().set(connectedData.getValue().get());
        }

        if(inputPin2.isConnected())
        {
            Pin connectedPin = inputPin2.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();

            inputData2.getValue().set(connectedData.getValue().get());
        }

        outputData.getValue().set("Teleport(" + inputData.getValue().get() + ", " + inputData2.getValue().get() + ");");
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
