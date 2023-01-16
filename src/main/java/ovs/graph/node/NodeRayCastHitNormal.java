package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeRayCastHitNormal extends Node {

    PinVar pinStartPosition = new PinVar();
    PinVar pinEndPosition = new PinVar();
    PinVar pinIncludePlayers = new PinVar();
    PinVar pinExcludePlayers = new PinVar();
    PinVar pinOwnedObjectsInclude = new PinVar();
    PinVar output = new PinVar();

    public NodeRayCastHitNormal(Graph graph) {
        super(graph);
        setName("Ray Cast Hit Normal");

        pinStartPosition.setNode(this);
        pinStartPosition.setName("Start Position");
        addCustomInput(pinStartPosition);

        pinEndPosition.setNode(this);
        pinEndPosition.setName("End Position");
        addCustomInput(pinEndPosition);

        pinIncludePlayers.setNode(this);
        pinIncludePlayers.setName("Players To Include");
        addCustomInput(pinIncludePlayers);

        pinExcludePlayers.setNode(this);
        pinExcludePlayers.setName("Players To Exclude");
        addCustomInput(pinExcludePlayers);

        pinOwnedObjectsInclude.setNode(this);
        pinOwnedObjectsInclude.setName("Include Player Owned Objects");
        addCustomInput(pinOwnedObjectsInclude);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> startPositionData = pinStartPosition.getData();
        PinData<ImString> endPositionData = pinEndPosition.getData();
        PinData<ImString> includePlayersData = pinIncludePlayers.getData();
        PinData<ImString> excludePlayersData = pinExcludePlayers.getData();
        PinData<ImString> ownedObjectsData = pinOwnedObjectsInclude.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinStartPosition, startPositionData, "Vector(0, 0, 0)");
        handlePinStringConnection(pinEndPosition, endPositionData, "Vector(0, 0, 0)");
        handlePinStringConnection(pinIncludePlayers, includePlayersData, "All Players(All Teams)");
        handlePinStringConnection(pinExcludePlayers, excludePlayersData, "Event Player");
        handlePinStringConnection(pinOwnedObjectsInclude, ownedObjectsData, "True");

        outputData.getValue().set(getName() + "(" + startPositionData.getValue().get() + ", " + endPositionData.getValue().get() + ", " + includePlayersData.getValue().get() + ", " + excludePlayersData.getValue().get() + ", " + ownedObjectsData.getValue().get() + ")");
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