package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeIsCommunicatingAnyVoiceLine extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar output = new PinVar();

    public NodeIsCommunicatingAnyVoiceLine(Graph graph) {
        super(graph);
        setName("Is Communicating Any Voice Line");

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

        outputData.getValue().set("Is Communicating Any Voice Line(" + playerData.getValue().get() + ")");
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
        return "Whether a player is using a voice line. (the duration of voice lines is assumed to be 4 seconds.).";
    }
}