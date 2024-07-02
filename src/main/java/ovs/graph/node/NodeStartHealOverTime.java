package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeStartHealOverTime extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinHealer = new PinVar();
    PinVar pinDuration = new PinVar();
    PinVar pinHealingPerSecond = new PinVar();
    PinAction output = new PinAction();

    public NodeStartHealOverTime(Graph graph) {
        super(graph);
        setName("Start Heal Over Time");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinHealer.setNode(this);
        pinHealer.setName("Healer");
        addCustomInput(pinHealer);

        pinDuration.setNode(this);
        pinDuration.setName("Duration");
        addCustomInput(pinDuration);

        pinHealingPerSecond.setNode(this);
        pinHealingPerSecond.setName("Healing Per Second");
        addCustomInput(pinHealingPerSecond);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> healerData = pinHealer.getData();
        PinData<ImString> durationData = pinDuration.getData();
        PinData<ImString> healingPerSecondData = pinHealingPerSecond.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");
        handlePinStringConnection(pinHealer, healerData, "Event Player");
        handlePinStringConnection(pinDuration, durationData, "5");
        handlePinStringConnection(pinHealingPerSecond, healingPerSecondData, "20");

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + healerData.getValue().get() + ", " + durationData.getValue().get() + ", " + healingPerSecondData.getValue().get() + ");");
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
        return "Starts an instance of heal over time. this will persist for the specified duration or until stopped by script. to obtain a reference to this, use the last heal over time id value.";
    }
}