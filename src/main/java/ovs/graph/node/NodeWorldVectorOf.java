package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeWorldVectorOf extends Node {

    PinVar pinLocalVector = new PinVar();
    PinVar pinRelativePlayer = new PinVar();
    PinVar pinTransformation = new PinVar();
    PinVar output = new PinVar();

    public NodeWorldVectorOf(Graph graph) {
        super(graph);
        setName("World Vector Of");

        pinLocalVector.setNode(this);
        pinLocalVector.setName("Local Vector");
        addCustomInput(pinLocalVector);

        pinRelativePlayer.setNode(this);
        pinRelativePlayer.setName("Relative Player");
        addCustomInput(pinRelativePlayer);

        pinTransformation.setNode(this);
        pinTransformation.setName("Transformation");
        addCustomInput(pinTransformation);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> localVectorData = pinLocalVector.getData();
        PinData<ImString> relativePlayerData = pinRelativePlayer.getData();
        PinData<ImString> transformationData = pinTransformation.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinLocalVector, localVectorData);
        handlePinStringConnection(pinRelativePlayer, relativePlayerData);
        handlePinStringConnection(pinTransformation, transformationData);

        outputData.getValue().set(getName() + "(" + localVectorData.getValue().get() + ", " + relativePlayerData.getValue().get() + ", " + transformationData.getValue().get() + ")");
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
        return "The vector in world coordinates corresponding to the provided vector in local coordinates.";
    }
}