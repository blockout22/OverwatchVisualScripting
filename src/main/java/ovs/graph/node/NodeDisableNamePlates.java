package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDisableNamePlates extends Node{

    PinVar pinViewedPlayer = new PinVar();
    PinVar pinViewingPlayers = new PinVar();
    PinAction output = new PinAction();

    public NodeDisableNamePlates(Graph graph) {
        super(graph);
        setName("Disable Nameplates");

        pinViewedPlayer.setNode(this);
        pinViewedPlayer.setName("Viewed Players");
        addCustomInput(pinViewedPlayer);

        pinViewingPlayers.setNode(this);
        pinViewingPlayers.setName("Viewing Players");
        addCustomInput(pinViewingPlayers);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> viewedData = pinViewedPlayer.getData();
        PinData<ImString> viewingData = pinViewingPlayers.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinViewedPlayer, viewedData);
        handlePinStringConnection(pinViewingPlayers, viewingData);

        outputData.getValue().set("Disable Nameplates(" + viewedData.getValue().get() + ", " + viewingData.getValue().get() + ");");
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
