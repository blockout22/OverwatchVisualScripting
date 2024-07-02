package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetJumpEnabled extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinEnabled = new PinVar();

    PinAction output = new PinAction();

    public NodeSetJumpEnabled(Graph graph) {
        super(graph);
        setName("Set Jump Enabled");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinEnabled.setNode(this);
        pinEnabled.setName("Enabled");
        addCustomInput(pinEnabled);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> enabledData = pinEnabled.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinEnabled, enabledData, "True");

        outputData.getValue().set("Set Jump Enabled(" + playerData.getValue().get() + ", " + enabledData.getValue().get() + ");");
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
        return "Enables or disables jump for one or more players.";
    }
}