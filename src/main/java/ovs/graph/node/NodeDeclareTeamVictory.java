package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDeclareTeamVictory extends Node {

    PinVar pinTeam = new PinVar();
    PinAction output = new PinAction();

    public NodeDeclareTeamVictory(Graph graph) {
        super(graph);
        setName("Declare Team Victory");

        pinTeam.setNode(this);
        pinTeam.setName("Round Winning Team");
        addCustomInput(pinTeam);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> roundWinningTeamData = pinTeam.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinTeam, roundWinningTeamData);

        outputData.getValue().set(getName() + "(" + pinTeam.getData().getValue() + ");");
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
        return "Instantly ends the match with the specified team as the winner, this action has no effect in free-for-all modes.";
    }
}