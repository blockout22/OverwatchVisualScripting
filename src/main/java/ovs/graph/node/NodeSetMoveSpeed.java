package ovs.graph.node;

import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinFloat;
import ovs.graph.pin.PinVar;

public class NodeSetMoveSpeed extends Node{

    PinVar pinPlayer = new PinVar();
    PinFloat pinMoveSpeed = new PinFloat();

    PinAction output = new PinAction();

    public NodeSetMoveSpeed(Graph graph) {
        super(graph);
        setName("Set Move Speed");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinMoveSpeed.setNode(this);
        pinMoveSpeed.setName("Move Speed");
        addCustomInput(pinMoveSpeed);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImFloat> speedData = pinMoveSpeed.getData();
        PinData<ImString> outputData = output.getData();

        if(pinPlayer.isConnected()){
            Pin connectedPin = pinPlayer.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            playerData.getValue().set(connectedData.getValue().get());
        }else{
            playerData.getValue().set("Null");
        }

        if(pinMoveSpeed.isConnected()){
            Pin connectedPin = pinMoveSpeed.getConnectedPin();

            PinData<ImFloat> connectedData = connectedPin.getData();
            speedData.getValue().set(connectedData.getValue().get());
        }

        outputData.getValue().set("Set Move Speed(" + playerData.getValue().get() + ", " + speedData.getValue().get() + ");");
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
