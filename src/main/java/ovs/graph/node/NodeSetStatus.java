package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetStatus extends Node {

    ComboBox status = new ComboBox("Asleep", "Burning", "Frozen", "Hacked", "Invisible", "Knocked Down", "Phased Out", "Rooted", "Stunned", "Unkillable");

    PinVar pinPlayer = new PinVar();
    PinVar pinAssister = new PinVar();
    PinVar pinDuration = new PinVar();

    PinAction output = new PinAction();

    public NodeSetStatus(Graph graph) {
        super(graph);
        setName("Set Status");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinAssister.setNode(this);
        pinAssister.setName("Assister");
        addCustomInput(pinAssister);

        pinDuration.setNode(this);
        pinDuration.setName("Duration");
        addCustomInput(pinDuration);

        status.select(3);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> assisterData = pinAssister.getData();
        PinData<ImString> durationData = pinDuration.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinAssister, assisterData, "Null");
        handlePinStringConnection(pinDuration, durationData, "0");

        outputData.getValue().set("Set Status(" + playerData.getValue().get() + ", " + assisterData.getValue().get() + ", " + status.getSelectedValue() + ", " + durationData.getValue().get() + ");");
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