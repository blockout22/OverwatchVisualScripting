package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeStartDamageOverTime extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinDamager = new PinVar();
    PinVar pinDuration = new PinVar();
    PinVar pinDamagePerSecond = new PinVar();

    PinAction output = new PinAction();

    public NodeStartDamageOverTime(Graph graph) {
        super(graph);
        setName("Start Damage Over Time");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinDamager.setNode(this);
        pinDamager.setName("Damager");
        addCustomInput(pinDamager);

        pinDuration.setNode(this);
        pinDuration.setName("Duration");
        addCustomInput(pinDuration);

        pinDamagePerSecond.setNode(this);
        pinDamagePerSecond.setName("Damage Per Second");
        addCustomInput(pinDamagePerSecond);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> damagerData = pinDamager.getData();
        PinData<ImString> durationData = pinDuration.getData();
        PinData<ImString> dpsData = pinDamagePerSecond.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinDamager, damagerData, "Null");
        handlePinStringConnection(pinDuration, durationData, "0");
        handlePinStringConnection(pinDamagePerSecond, dpsData, "0");

        outputData.getValue().set("Start Damage Over Time(" + playerData.getValue().get() + ", " + damagerData.getValue().get() + ", " + durationData.getValue().get() + ", " + dpsData.getValue().get() + ");");
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