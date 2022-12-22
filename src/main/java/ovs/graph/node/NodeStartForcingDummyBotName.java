package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeStartForcingDummyBotName extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinName = new PinVar();

    PinAction output = new PinAction();

    public NodeStartForcingDummyBotName(Graph graph) {
        super(graph);
        setName("Start Forcing Dummy Bot Name");

        pinPlayer.setNode(this);
        pinPlayer.setName("Bot ID");
        addCustomInput(pinPlayer);

        pinName.setNode(this);
        pinName.setName("Name");
        addCustomInput(pinName);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> nameData = pinName.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinName, nameData);

        outputData.getValue().set("Start Forcing Dummy Bot Name(" + playerData.getValue().get() + ", " + nameData.getValue().get() + ");");

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
