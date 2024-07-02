package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodeLocalVectorOf extends Node {

    ComboBox transform = new ComboBox("Rotation", "Rotation And Translation");

    PinVar pinWorldVector = new PinVar();
    PinVar pinRelativePlayer = new PinVar();
    PinVar output = new PinVar();

    public NodeLocalVectorOf(Graph graph) {
        super(graph);
        setName("Local Vector Of");

        pinWorldVector.setNode(this);
        pinWorldVector.setName("World Vector");
        addCustomInput(pinWorldVector);

        pinRelativePlayer.setNode(this);
        pinRelativePlayer.setName("Relative Player");
        addCustomInput(pinRelativePlayer);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> worldVectorData = pinWorldVector.getData();
        PinData<ImString> relativePlayerData = pinRelativePlayer.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinWorldVector, worldVectorData);
        handlePinStringConnection(pinRelativePlayer, relativePlayerData);

        outputData.getValue().set(getName() + "(" + worldVectorData.getValue().get() + ", " + relativePlayerData.getValue().get() + ", " + transform.getSelectedValue() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        transform.show();
    }

    @Override
    public String getTooltip() {
        return "The vector in local coordinates corresponding to the provided vector in the world coordinates.";
    }
}