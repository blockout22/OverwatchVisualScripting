package ovs.graph.node;

import imgui.ImGui;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ColorPicker;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinVar;

public class NodeCustomColor extends Node{

    ColorPicker picker = new ColorPicker();

    PinVar pinRed = new PinVar();
    PinVar pinGreen = new PinVar();
    PinVar pinBlue = new PinVar();
    PinVar pinAlpha = new PinVar();

    PinVar output = new PinVar();

    public NodeCustomColor(Graph graph) {
        super(graph);
        setName("Custom Color");
        setColor(213, 232, 0);

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

        addUiComponent(picker);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Color:" + picker.getColor()[0] + "," + picker.getColor()[1] + "," + picker.getColor()[2] + "," + picker.getColor()[3]);
    }

    @Override
    public void onLoaded() {
        for (String data : getExtraSaveData()){
            if(data.startsWith("Color")){
                String col = data.split(":")[1];
                float r = Float.valueOf(col.split(",")[0]);
                float g = Float.valueOf(col.split(",")[1]);
                float b = Float.valueOf(col.split(",")[2]);
                float a = Float.valueOf(col.split(",")[3]);

                picker.setRGBA(r, g, b, a);
            }
        }
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
            redData.getValue().set(String.valueOf(picker.getColor()[0] * 255));
        }

        if(pinGreen.isConnected()) {
            Pin connectedPin = pinGreen.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            greenData.getValue().set(connectedData.getValue().get());
        }else{
            greenData.getValue().set(String.valueOf(picker.getColor()[1] * 255));
        }

        if(pinBlue.isConnected()) {
            Pin connectedPin = pinBlue.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            blueData.getValue().set(connectedData.getValue().get());
        }else{
            blueData.getValue().set(String.valueOf(picker.getColor()[2] * 255));
        }

        if(pinAlpha.isConnected()) {
            Pin connectedPin = pinAlpha.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            alphaData.getValue().set(connectedData.getValue().get());
        }else{
            alphaData.getValue().set(String.valueOf(picker.getColor()[3] * 255));
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
