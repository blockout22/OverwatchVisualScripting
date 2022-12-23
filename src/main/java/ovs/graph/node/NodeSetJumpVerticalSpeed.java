package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetJumpVerticalSpeed extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinSpeed = new PinVar();

    PinAction output = new PinAction();

    public NodeSetJumpVerticalSpeed(Graph graph) {
        super(graph);
        setName("Set Jump Vertical Speed");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinSpeed.setNode(this);
        pinSpeed.setName("Jump Vertical Speed Percentage");
        addCustomInput(pinSpeed);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> speedData = pinSpeed.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinSpeed, speedData, "100");

        outputData.getValue().set("Set Jump Vertical Speed(" + playerData.getValue().get() + ", " + speedData.getValue().get() + ");");
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