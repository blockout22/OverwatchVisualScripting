package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeStartForcingPlayerToBeHero extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinHero = new PinVar();

    PinAction output = new PinAction();

    public NodeStartForcingPlayerToBeHero(Graph graph) {
        super(graph);
        setName("Start Forcing Player To Be Hero");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinHero.setNode(this);
        pinHero.setName("Hero");
        addCustomInput(pinHero);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> heroData = pinHero.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinHero, heroData);

        outputData.getValue().set("Start Forcing Player To Be Hero(" + playerData.getValue().get() + ", " + heroData.getValue().get() + ");");
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
