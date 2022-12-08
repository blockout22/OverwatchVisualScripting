package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetAbility2Enabled extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinBool = new PinVar();

    PinAction output = new PinAction();

    public NodeSetAbility2Enabled(Graph graph) {
        super(graph);
        setName("Set Ability 2 Enabled");

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
        PinData<ImString> outData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinBool, boolData, "True");

        outData.getValue().set("Set Ability 2 Enabled(" + playerData.getValue().get() + ", " + boolData.getValue().get() + ");");

    }

    @Override
    public String getOutput() {
        PinData<ImString> outData = output.getData();
        return outData.getValue().get();
    }

    @Override
    public void UI() {

    }
}
