package ovs.graph.node;

import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetMoveSpeed extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinMoveSpeed = new PinVar();

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
        PinData<ImString> speedData = pinMoveSpeed.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Null");
        handlePinStringConnection(pinMoveSpeed, speedData, "100");

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

    @Override
    public String getTooltip() {
        return "Sets the move speed of one or more players to a percentage of their raw move speed.";
    }
}
