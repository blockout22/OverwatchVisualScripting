package ovs.graph.node;

import imgui.ImVec4;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinVar;
import ovs.graph.pin.PinVector;

public class NodeVector extends Node{

//    PinVector inputPin = new PinVector();
    PinVar pinX = new PinVar();
    PinVar pinY = new PinVar();
    PinVar pinZ = new PinVar();
    PinVar outputPin = new PinVar();

    public NodeVector(Graph graph) {
        super(graph);
        setName("Vector");

        pinX.setNode(this);
        pinX.setName("X");
        addCustomInput(pinX);

        pinY.setNode(this);
        pinY.setName("Y");
        addCustomInput(pinY);

        pinZ.setNode(this);
        pinZ.setName("Z");
        addCustomInput(pinZ);

        outputPin.setNode(this);
        addCustomOutput(outputPin);
    }

    @Override
    public void execute() {
        PinData<ImString> xData = pinX.getData();
        PinData<ImString> yData = pinY.getData();
        PinData<ImString> zData = pinZ.getData();
        PinData<ImString> outputData = outputPin.getData();

        handlePinStringConnection(pinX, xData, "0");
        handlePinStringConnection(pinY, yData, "0");
        handlePinStringConnection(pinZ, zData, "0");

        outputData.getValue().set("Vector(" + xData.getValue().get() + ", " +  yData.getValue().get() + ", " + zData.getValue().get() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> data = outputPin.getData();
        return data.getValue().get();
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "A vector composed of three real numbers (x,y,z) where x is left, y is up, and z is forward, vectors are used for position, direction, and velocity.";
    }
}
