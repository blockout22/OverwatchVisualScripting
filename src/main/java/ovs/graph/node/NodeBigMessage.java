package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeBigMessage extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinHeader = new PinVar();
    PinAction output = new PinAction();

    public NodeBigMessage(Graph graph) {
        super(graph);
        setName("Big Message");

        pinPlayer.setNode(this);
        pinPlayer.setName("Visible To");
        addCustomInput(pinPlayer);

        pinHeader.setNode(this);
        pinHeader.setName("Header");
        addCustomInput(pinHeader);

        output.setNode(this);
        addCustomOutput(output);

    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> headerData = pinHeader.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinHeader, headerData, "Custom String(\"\", Null, Null, Null)");

        outputData.getValue().set("Big Message(" + playerData.getValue().get() + ", " + headerData.getValue().get() + ");");
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
        return "Displays a large message above the reticle that is visible to specific players.";
    }
}
