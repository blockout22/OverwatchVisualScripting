package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinVar;

public class NodeCustomColor extends Node{

    PinVar pinRed = new PinVar();
    PinVar pinGreen = new PinVar();
    PinVar pinBlue = new PinVar();
    PinVar pinAlpha = new PinVar();

    PinVar output = new PinVar();

    public NodeCustomColor(Graph graph) {
        super(graph);
        setName("Custom Color");

        pinRed.setNode(this);
        pinRed.setName("Red");
        addCustomInput(pinRed);

        pinGreen.setNode(this);
        pinGreen.setName("Green");
        addCustomInput(pinGreen);

        pinBlue.setNode(this);
        pinBlue.setName("Blue");
        addCustomInput(pinBlue);

        pinAlpha.setNode(this);
        pinAlpha.setName("Alpha");
        addCustomInput(pinAlpha);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> redData = pinRed.getData();
        PinData<ImString> greenData = pinGreen.getData();
        PinData<ImString> blueData = pinBlue.getData();
        PinData<ImString> alphaData = pinAlpha.getData();
        PinData<ImString> outputData = output.getData();

        if(pinRed.isConnected()) {
            Pin connectedPin = pinRed.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            redData.getValue().set(connectedData.getValue().get());
        }else{
            redData.getValue().set("255");
        }

        if(pinGreen.isConnected()) {
            Pin connectedPin = pinGreen.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            greenData.getValue().set(connectedData.getValue().get());
        }else{
            greenData.getValue().set("255");
        }

        if(pinBlue.isConnected()) {
            Pin connectedPin = pinBlue.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            blueData.getValue().set(connectedData.getValue().get());
        }else{
            blueData.getValue().set("255");
        }

        if(pinAlpha.isConnected()) {
            Pin connectedPin = pinAlpha.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            alphaData.getValue().set(connectedData.getValue().get());
        }else{
            alphaData.getValue().set("255");
        }

        outputData.getValue().set("Custom Color(" + redData.getValue().get() + ", " + greenData.getValue().get() + ", " + blueData.getValue().get() + ", " + alphaData.getValue().get() + ")");

    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {

    }
}
