package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetAbilityResource extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinButton = new PinVar();
    PinVar pinPerc = new PinVar();

    PinAction output = new PinAction();

    public NodeSetAbilityResource(Graph graph) {
        super(graph);
        setName("Set Ability Resource");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinButton.setNode(this);
        pinButton.setName("Button");
        addCustomInput(pinButton);

        pinPerc.setNode(this);
        pinPerc.setName("Resource Percentage");
        addCustomInput(pinPerc);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> buttonData = pinButton.getData();
        PinData<ImString> percData = pinPerc.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Null");
        handlePinStringConnection(pinButton, buttonData, "Null");
        handlePinStringConnection(pinPerc, percData, "100");

        outputData.getValue().set("Set Ability Resource(" + playerData.getValue().get() + ", " + buttonData.getValue().get() + ", " + percData.getValue().get() + ");");
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
        return "Set the ability resource percentage for one or more players if supported.";
    }
}
