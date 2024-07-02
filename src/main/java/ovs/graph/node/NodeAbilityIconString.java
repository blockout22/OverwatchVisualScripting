package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeAbilityIconString extends Node {

    PinVar pinHero = new PinVar();
    PinVar pinButton = new PinVar();
    PinVar output = new PinVar();

    public NodeAbilityIconString(Graph graph) {
        super(graph);
        setName("Ability Icon String");

        pinHero.setNode(this);
        pinHero.setName("Hero");
        addCustomInput(pinHero);

        pinButton.setNode(this);
        pinButton.setName("Button");
        addCustomInput(pinButton);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> heroData = pinHero.getData();
        PinData<ImString> buttonData = pinButton.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinHero, heroData);
        handlePinStringConnection(pinButton, buttonData);

        outputData.getValue().set("Ability Icon String(" + heroData.getValue().get() + ", " + buttonData.getValue().get() + ")");
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
        return "Converts a hero and button parameter into a string that shows up as an icon (up to 4 per string).";
    }
}