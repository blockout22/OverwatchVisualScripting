package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeIsInViewAngle extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinLocation = new PinVar();
    PinVar output = new PinVar();

    public NodeIsInViewAngle(Graph graph) {
        super(graph);
        setName("Is In View Angle");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinLocation.setNode(this);
        pinLocation.setName("Location");
        addCustomInput(pinLocation);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> locationData = pinLocation.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinLocation, locationData);

        outputData.getValue().set("Is In View Angle(" + playerData.getValue().get() + ", " + locationData.getValue().get() + ")");
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