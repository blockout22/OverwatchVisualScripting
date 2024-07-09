package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeMovePlayerToTeam extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinTeam = new PinVar();
    PinVar pinSlot = new PinVar();
    PinAction output = new PinAction();

    public NodeMovePlayerToTeam(Graph graph) {
        super(graph);
        setName("Move Player To Team");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinTeam.setNode(this);
        pinTeam.setName("Team");
        addCustomInput(pinTeam);

        pinSlot.setNode(this);
        pinSlot.setName("Slot");
        addCustomInput(pinSlot);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> teamData = pinTeam.getData();
        PinData<ImString> slotData = pinSlot.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");
        handlePinStringConnection(pinTeam, teamData);
        handlePinStringConnection(pinSlot, slotData, "-1");

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + teamData.getValue().get() + ", " + slotData.getValue().get() + ");");
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
        return "Moves one or more players to the specified team and slot. this action can fail if the specified slot is not available. this action doesn't work on dummy bots.";
    }
}