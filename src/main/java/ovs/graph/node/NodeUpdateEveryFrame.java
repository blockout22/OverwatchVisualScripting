package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeUpdateEveryFrame extends Node {

    PinVar pinValue = new PinVar();
    PinVar output = new PinVar();

    public NodeUpdateEveryFrame(Graph graph) {
        super(graph);
        setName("Update Every Frame");

        pinValue.setNode(this);
        pinValue.setName("Value");
        addCustomInput(pinValue);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> valueData = pinValue.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinValue, valueData);

        outputData.getValue().set(getName() + "(" + valueData.getValue().get() + ")");
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
        return "Results in the value that is provided to the input value parameter and increases the update frequency of the value to once per frame. useful for smoothing the appearance of certain values -- such as position of -- that normally only update every few frames. applies to conditions as well as action parameters that reevaluate, may increase server load and/or lower framerate.";
    }
}