package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeEnableMovementCollisionWithEnvironment extends Node{

    PinVar pinPlayer = new PinVar();

    PinAction output = new PinAction();

    public NodeEnableMovementCollisionWithEnvironment(Graph graph) {
        super(graph);
        setName("Enable Movement Collision With Environment");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);

        outputData.getValue().set("Enable Movement Collision With Environment(" + playerData.getValue().get() + ");");

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
