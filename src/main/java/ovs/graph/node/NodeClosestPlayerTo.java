package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeClosestPlayerTo extends Node {

    PinVar pinCenter = new PinVar();
    PinVar pinTeam = new PinVar();
    PinVar output = new PinVar();

    public NodeClosestPlayerTo(Graph graph) {
        super(graph);
        setName("Closest Player To");

        pinCenter.setNode(this);
        pinCenter.setName("Center");
        addCustomInput(pinCenter);

        pinTeam.setNode(this);
        pinTeam.setName("Team");
        addCustomInput(pinTeam);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> centerData = pinCenter.getData();
        PinData<ImString> teamData = pinTeam.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinCenter, centerData);
        handlePinStringConnection(pinTeam, teamData);

        outputData.getValue().set("Closest Player To(" + centerData.getValue().get() + ", " + teamData.getValue().get() + ")");
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