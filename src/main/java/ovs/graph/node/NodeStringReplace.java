package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeStringReplace extends Node {

    PinVar pinString = new PinVar();
    PinVar pinPattern = new PinVar();
    PinVar pinReplacement = new PinVar();
    PinVar output = new PinVar();

    public NodeStringReplace(Graph graph) {
        super(graph);
        setName("String Replace");

        pinString.setNode(this);
        pinString.setName("String");
        addCustomInput(pinString);

        pinPattern.setNode(this);
        pinPattern.setName("Pattern");
        addCustomInput(pinPattern);

        pinReplacement.setNode(this);
        pinReplacement.setName("Replacement");
        addCustomInput(pinReplacement);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> stringData = pinString.getData();
        PinData<ImString> patternData = pinPattern.getData();
        PinData<ImString> replacementData = pinReplacement.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinString, stringData);
        handlePinStringConnection(pinPattern, patternData);
        handlePinStringConnection(pinReplacement, replacementData);

        outputData.getValue().set(getName() + "(" + stringData.getValue().get() + ", " + patternData.getValue().get() + ", " + replacementData.getValue().get() + ")");
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
        return "Results in a string value, this string value will be build from the specified string value, where all occurrences of the pattern string are replaced with the replacement string.";
    }
}