package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeStartScalingBarriers extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinReevaluation = new PinVar();
    PinAction output = new PinAction();

    public NodeStartScalingBarriers(Graph graph) {
        super(graph);
        setName("Start Scaling Barriers");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> reevaluationData = pinReevaluation.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");
        handlePinStringConnection(pinReevaluation, reevaluationData, "True");

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + reevaluationData.getValue().get() + ");");
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
        return "Start modifying the size of barriers for a player of players.";
    }
}