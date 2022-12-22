package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDisableVoiceChat extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinTeamVC = new PinVar();
    PinVar pinMatchVC = new PinVar();
    PinVar pinGroupVC = new PinVar();
    PinAction output = new PinAction();

    public NodeDisableVoiceChat(Graph graph) {
        super(graph);
        setName("Disable Voice Chat");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinTeamVC.setNode(this);
        pinTeamVC.setName("Team Voice Chat");
        addCustomInput(pinTeamVC);

        pinMatchVC.setNode(this);
        pinMatchVC.setName("Match Voice Chat");
        addCustomInput(pinMatchVC);

        pinGroupVC.setNode(this);
        pinGroupVC.setName("Group Voice Chat");
        addCustomInput(pinGroupVC);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> teamData = pinTeamVC.getData();
        PinData<ImString> matchData = pinMatchVC.getData();
        PinData<ImString> groupData = pinGroupVC.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinTeamVC, teamData, "True");
        handlePinStringConnection(pinMatchVC, matchData, "True");
        handlePinStringConnection(pinGroupVC, groupData, "True");

        outputData.getValue().set("Disable Text Chat(" + playerData.getValue().get() + ", " + teamData.getValue().get() + ", " + matchData.getValue().get() + ", " + groupData.getValue().get() + ");");
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
