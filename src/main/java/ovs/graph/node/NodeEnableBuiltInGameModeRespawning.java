package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeEnableBuiltInGameModeRespawning extends Node{

    PinVar pinPlayer = new PinVar();

    PinAction output = new PinAction();

    public NodeEnableBuiltInGameModeRespawning(Graph graph) {
        super(graph);
        setName("Enable Built In Game Mode Respawning");

        pinPlayer.setNode(this);
        pinPlayer.setName("Players");
        addCustomInput(pinPlayer);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playersData = pinPlayer.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playersData);

        outputData.getValue().set("Enable Built-In Game Mode Respawning(" + playersData.getValue().get() + ");");

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
