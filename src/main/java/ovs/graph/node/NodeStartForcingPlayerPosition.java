package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeStartForcingPlayerPosition extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinPosition = new PinVar();

    PinAction output = new PinAction();

    public NodeStartForcingPlayerPosition(Graph graph) {
        super(graph);
        setName("Start Forcing Player Position");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinPosition.setNode(this);
        pinPosition.setName("Position");
        addCustomInput(pinPosition);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> positionData = pinPosition.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinPosition, positionData);

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + positionData.getValue().get() + ", True);");
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
        return "Starts forcing a player to be in a giving position. if reevaluation is enabled, then the position evaluated every frame, allowing the player to be moved around over time.";
    }
}
