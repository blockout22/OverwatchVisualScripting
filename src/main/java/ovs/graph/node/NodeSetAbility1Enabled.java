package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetAbility1Enabled extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinBool = new PinVar();

    PinAction output = new PinAction();

    public NodeSetAbility1Enabled(Graph graph) {
        super(graph);
        setName("Set Ability 1 Enabled");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinBool.setNode(this);
        pinBool.setName("True/False");
        addCustomInput(pinBool);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> boolData = pinBool.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");
        handlePinStringConnection(pinBool, boolData, "True");

        outputData.getValue().set("Set Ability 1 Enabled(" + playerData.getValue().get() + ", " + boolData.getValue().get() + ");");

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
        return "Enables or disables ability 1 for one or more players";
    }
}
