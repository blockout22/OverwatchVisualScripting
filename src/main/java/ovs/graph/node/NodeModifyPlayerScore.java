package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeModifyPlayerScore extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinScore = new PinVar();
    PinAction output = new PinAction();

    public NodeModifyPlayerScore(Graph graph) {
        super(graph);
        setName("Modify Player Score");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinScore.setNode(this);
        pinScore.setName("Score");
        addCustomInput(pinScore);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> scoreData = pinScore.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");
        handlePinStringConnection(pinScore, scoreData, "0");

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + scoreData.getValue().get() + ")");
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
        return "Modifies the score (Kill Count) of one or more players. this action only has an effect in free-for-all modes.";
    }
}