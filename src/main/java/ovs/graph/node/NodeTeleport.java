package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeTeleport extends Node{

    //who to teleport
    PinVar pinPlayer = new PinVar();
    //Where to teleport inputPin
    PinVar pinPosition = new PinVar();
    PinAction outputPin = new PinAction();

    public NodeTeleport(Graph graph) {
        super(graph);
        setName("Teleport");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinPosition.setNode(this);
        pinPosition.setName("Position");
        addCustomInput(pinPosition);

        outputPin.setNode(this);
        addCustomOutput(outputPin);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> positionData = pinPosition.getData();
        PinData<ImString> outputData = outputPin.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinPosition, positionData);

        outputData.getValue().set("Teleport(" + playerData.getValue().get() + ", " + positionData.getValue().get() + ");");
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
