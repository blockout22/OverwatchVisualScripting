package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeForGlobalVariable extends Node {

    PinVar pinControl = new PinVar();
    PinVar pinRangeStart = new PinVar();
    PinVar pinRangeStop = new PinVar();
    PinVar pinStep = new PinVar();
    PinAction output = new PinAction();

    public NodeForGlobalVariable(Graph graph) {
        super(graph);
        setName("For Global Variable");

        pinControl.setNode(this);
        pinControl.setName("Control Variable");
        addCustomInput(pinControl);

        pinRangeStart.setNode(this);
        pinRangeStart.setName("Range Start");
        addCustomInput(pinRangeStart);

        pinRangeStop.setNode(this);
        pinRangeStop.setName("Range Stop");
        addCustomInput(pinRangeStop);

        pinStep.setNode(this);
        pinStep.setName("Step");
        addCustomInput(pinStep);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> controlVarData = pinControl.getData();
        PinData<ImString> rangeStartData = pinRangeStart.getData();
        PinData<ImString> rangeStopData = pinRangeStop.getData();
        PinData<ImString> stepData = pinStep.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinControl, controlVarData);
        handlePinStringConnection(pinRangeStart, rangeStartData, "0");
        handlePinStringConnection(pinRangeStop, rangeStopData, "1");
        handlePinStringConnection(pinStep, stepData, "1");

        outputData.getValue().set(getName() + "(" + controlVarData.getValue().get() + ", " + rangeStartData.getValue().get() + ", " + rangeStopData.getValue().get() + ", " + stepData.getValue().get() + ");");
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
        return "Denotes the beginning of a series of actions that will execute in a loop, modifying the control variable on each loop, the corresponding end action denotes the end of the loop, if the control variable reaches or passes the range stop value, then the loop exists, and execution jumps to the next action after the end action.";
    }
}