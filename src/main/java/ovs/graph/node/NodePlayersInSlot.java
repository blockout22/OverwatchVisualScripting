package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodePlayersInSlot extends Node {

    PinVar pinSlot = new PinVar();
    PinVar pinTeam = new PinVar();
    PinVar output = new PinVar();

    public NodePlayersInSlot(Graph graph) {
        super(graph);
        setName("Players In Slot");

        pinSlot.setNode(this);
        pinSlot.setName("Slot");
        addCustomInput(pinSlot);

        pinTeam.setNode(this);
        pinTeam.setName("Team");
        addCustomInput(pinTeam);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> slotData = pinSlot.getData();
        PinData<ImString> teamData = pinTeam.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinSlot, slotData);
        handlePinStringConnection(pinTeam, teamData);

        outputData.getValue().set(getName() + "(" + slotData.getValue().get() + ", " + teamData.getValue().get() + ")");
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