package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeStringSplit extends Node {

    PinVar pinString = new PinVar();
    PinVar pinSeparator = new PinVar();
    PinVar output = new PinVar();

    public NodeStringSplit(Graph graph) {
        super(graph);
        setName("String Split");

        pinString.setNode(this);
        pinString.setName("String");
        addCustomInput(pinString);

        pinSeparator.setNode(this);
        pinSeparator.setName("Separator");
        addCustomInput(pinSeparator);


        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> stringData = pinString.getData();
        PinData<ImString> separatorData = pinSeparator.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinString, stringData);
        handlePinStringConnection(pinSeparator, separatorData);

        outputData.getValue().set(getName() + "(" + stringData.getValue().get() + ", " + separatorData.getValue().get() + ")");
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
        return "Results in an array of string values, these string values will be build from the specified string value, split around the separator string.";
    }
}