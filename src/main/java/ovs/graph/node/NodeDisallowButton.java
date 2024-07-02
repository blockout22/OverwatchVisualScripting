package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDisallowButton extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinButton = new PinVar();

    PinAction output = new PinAction();

    public NodeDisallowButton(Graph graph) {
        super(graph);
        setName("Disallow Button");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinButton.setNode(this);
        pinButton.setName("Button");
        addCustomInput(pinButton);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> buttonData = pinButton.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinButton, buttonData);

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + buttonData.getValue().get() + ");");
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
        return "Disables a logical button for one or more players such that pressing it has no effect.";
    }
}
