package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeHeroIconString extends Node{

    PinVar heroPin = new PinVar();

    PinVar output = new PinVar();

    public NodeHeroIconString(Graph graph) {
        super(graph);
        setName("Hero Icon String");

        heroPin.setNode(this);
        heroPin.setName("Hero");
        addCustomInput(heroPin);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> heroData = heroPin.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(heroPin, heroData, "Hero(Ana)");

        outputData.getValue().set("Hero Icon String(" + heroData.getValue().get() + ")");
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
        return "Converts a hero parameter into a string that shows up as an icon (up to 4 per string).";
    }
}
