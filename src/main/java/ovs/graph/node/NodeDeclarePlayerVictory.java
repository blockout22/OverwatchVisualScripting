package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDeclarePlayerVictory extends Node {

    PinVar pinPlayer = new PinVar();
    PinAction output = new PinAction();

    public NodeDeclarePlayerVictory(Graph graph) {
        super(graph);
        setName("Declare Player Victory");

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

        handlePinStringConnection(pinPlayer, playerData, "Event Player");

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ");");
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
        return "Instantly ends the match with the specific player as the winner, this action only has an effect in free-for-all modes.";
    }
}