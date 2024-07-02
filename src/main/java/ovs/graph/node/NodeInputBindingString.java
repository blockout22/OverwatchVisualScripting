package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeInputBindingString extends Node {

    PinVar pinButton = new PinVar();
    PinVar output = new PinVar();

    public NodeInputBindingString(Graph graph) {
        super(graph);
        setName("Input Binding String");

        pinButton.setNode(this);
        pinButton.setName("Button");
        addCustomInput(pinButton);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> buttonData = pinButton.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinButton, buttonData);

        outputData.getValue().set("Input Binding String(" + buttonData.getValue().get() + ")");
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
        return "Converts a button parameter into a string that shows up based on the player's input bindings. this value cannot be stored in variables.";
    }
}