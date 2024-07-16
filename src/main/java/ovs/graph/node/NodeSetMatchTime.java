package ovs.graph.node;

import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinFloat;
import ovs.graph.pin.PinVar;

public class NodeSetMatchTime extends Node{

    PinVar pinTime = new PinVar();
    PinAction output = new PinAction();

    public NodeSetMatchTime(Graph graph) {
        super(graph);
        setName("Set Match Time");

        pinTime.setNode(this);
        addCustomInput(pinTime);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> timeData = pinTime.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinTime, timeData, "0.0");

        outputData.getValue().set("Set Match Time(" + timeData.getValue().get() + ");");
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
        return "Sets the current match time (which is visible at the top of the screen). this can be used to shorten or extend the duration of a match or to change the duration of assemble heroes or setup.";
    }
}
