package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeStringContains extends Node {

    PinVar pinString1 = new PinVar();
    PinVar pinString2 = new PinVar();
    PinVar output = new PinVar();

    public NodeStringContains(Graph graph) {
        super(graph);
        setName("String Contains");

        pinString1.setNode(this);
        pinString1.setName("String");
        addCustomInput(pinString1);

        pinString2.setNode(this);
        pinString2.setName("String");
        addCustomInput(pinString2);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> string1Data = pinString1.getData();
        PinData<ImString> string2Data = pinString2.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinString1, string1Data);
        handlePinStringConnection(pinString2, string2Data);

        outputData.getValue().set(getName() + "(" + string1Data.getValue().get() + ", " + string2Data.getValue().get() + ")");
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
        return "Whether the specified string contains the specified value.";
    }
}