package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDeclareRoundVictory extends Node {

    PinVar pinRoundWinningTeam = new PinVar();
    PinAction output = new PinAction();

    public NodeDeclareRoundVictory(Graph graph) {
        super(graph);
        setName("Declare Round Victory");

        pinRoundWinningTeam.setNode(this);
        pinRoundWinningTeam.setName("Round Winning Team");
        addCustomInput(pinRoundWinningTeam);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> roundWinningTeamData = pinRoundWinningTeam.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinRoundWinningTeam, roundWinningTeamData);

        outputData.getValue().set(getName() + "(" + pinRoundWinningTeam.getData().getValue() + ");");
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
        return "Declare a team as the current round winner. this only works in the control and elimination game modes.";
    }
}