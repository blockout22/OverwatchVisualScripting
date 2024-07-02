package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeStopHealOverTime extends Node {

    PinVar pinHealOverTimeID = new PinVar();
    PinAction output = new PinAction();

    public NodeStopHealOverTime(Graph graph) {
        super(graph);
        setName("Stop Heal Over Time");

        pinHealOverTimeID.setNode(this);
        pinHealOverTimeID.setName("Heal Over Time ID");
        addCustomInput(pinHealOverTimeID);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> healOverTimeIdData = pinHealOverTimeID.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinHealOverTimeID, healOverTimeIdData);

        outputData.getValue().set(getName() + "(" + healOverTimeIdData.getValue().get() + ");");
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
        return "Stops an instance of heal over time started by the start heal over time action.";
    }
}