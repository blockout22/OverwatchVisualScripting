package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDisableMovementCollisionWithEnvironment extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinIncludeFloors = new PinVar();
    PinAction output = new PinAction();

    public NodeDisableMovementCollisionWithEnvironment(Graph graph) {
        super(graph);
        setName("Disable Movement Collision With Environment");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinIncludeFloors.setNode(this);
        pinIncludeFloors.setName("Include Floors");
        addCustomInput(pinIncludeFloors);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> floorData = pinIncludeFloors.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinIncludeFloors, floorData);

        outputData.getValue().set("Disable Movement Collision With Environment(" + playerData.getValue().get() + ", " + floorData.getValue().get() + ");");
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
