package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeArcTangentInRadians extends Node {

    PinVar numerator = new PinVar();
    PinVar denominator = new PinVar();
    PinVar output = new PinVar();

    public NodeArcTangentInRadians(Graph graph) {
        super(graph);
        setName("Arctangent In Radians");

        numerator.setNode(this);
        numerator.setName("Numerator");
        addCustomInput(numerator);

        denominator.setNode(this);
        denominator.setName("Denominator");
        addCustomInput(denominator);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> numeratorData = numerator.getData();
        PinData<ImString> denominatorData = denominator.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(numerator, numeratorData);
        handlePinStringConnection(denominator, denominatorData);

        outputData.getValue().set("Arctangent In Radians(" + numeratorData.getValue().get() + ", " + denominatorData.getValue().get() + ")");
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
        return "Arctangent in radians of the specified numerator and denominator (often referred to as atan2).";
    }
}