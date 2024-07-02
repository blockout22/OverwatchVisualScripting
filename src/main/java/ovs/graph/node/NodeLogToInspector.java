package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeLogToInspector extends Node {

    PinVar pinText = new PinVar();
    PinAction output = new PinAction();

    public NodeLogToInspector(Graph graph) {
        super(graph);
        setName("Log To Inspector");

        pinText.setNode(this);
        pinText.setName("Text");
        addCustomInput(pinText);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> textData = pinText.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinText, textData, "");

        outputData.getValue().set(getName() + "(" + textData.getValue().get() + ");");
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
        return "Causes the workshop inspector to record a log entry.";
    }
}