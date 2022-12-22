package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.node.Node;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDestroyDummyBot extends Node {

    PinVar pinTeam = new PinVar();
    PinVar pinSlot = new PinVar();
    PinAction output = new PinAction();

    public NodeDestroyDummyBot(Graph graph) {
        super(graph);
        setName("Destroy Dummy Bot");

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
        PinData<ImString> teamData = pinTeam.getData();
        PinData<ImString> slotData = pinSlot.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinTeam, teamData);
        handlePinStringConnection(pinSlot, slotData, "0");

        outputData.getValue().set("Destroy Dummy Bot(" + teamData.getValue().get() + ", " + slotData.getValue().get() + ");");

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
