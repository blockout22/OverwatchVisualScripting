package ovs.graph.node;

import imgui.ImGui;
import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinFloat;
import ovs.graph.pin.PinVar;

public class NodeCreateHudText extends Node{

    PinVar inputVisibleTo = new PinVar();
    PinVar inputHeader = new PinVar();
    PinVar inputSubHeader = new PinVar();
    PinVar inputText = new PinVar();

    PinVar pinHeaderColor = new PinVar();
    PinVar pinSubHeaderColor = new PinVar();
    PinVar pinTextColor = new PinVar();

    PinFloat pinSortOrder = new PinFloat();

    PinAction outputPin = new PinAction();

    ComboBox comboBox = new ComboBox();

    public NodeCreateHudText(Graph graph) {
        super(graph);
        setName("Create Hud Text");

        inputVisibleTo.setNode(this);
        inputVisibleTo.setName("Visible To");
        addCustomInput(inputVisibleTo);

        inputHeader.setNode(this);
        inputHeader.setName("Header");
        addCustomInput(inputHeader);

        inputSubHeader.setNode(this);
        inputSubHeader.setName("Subheader");
        addCustomInput(inputSubHeader);

        inputText.setNode(this);
        inputText.setName("Text");
        addCustomInput(inputText);

        pinSortOrder.setNode(this);
        pinSortOrder.setName("Sort Order");
        addCustomInput(pinSortOrder);

        pinHeaderColor.setNode(this);
        pinHeaderColor.setName("Header Color");
        addCustomInput(pinHeaderColor);

        pinSubHeaderColor.setNode(this);
        pinSubHeaderColor.setName("Subheader Color");
        addCustomInput(pinSubHeaderColor);

        pinTextColor.setNode(this);
        pinTextColor.setName("Text Color");
        addCustomInput(pinTextColor);

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        comboBox.addOption("Left");
        comboBox.addOption("Top");
        comboBox.addOption("Right");

        comboBox.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Location:" + comboBox.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Location:"))
            {
                comboBox.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> inputVisibleData = inputVisibleTo.getData();
        PinData<ImString> headerData = inputHeader.getData();
        PinData<ImString> subHeaderData = inputSubHeader.getData();
        PinData<ImString> textData = inputText.getData();
        PinData<ImString> headerColorData = pinHeaderColor.getData();
        PinData<ImString> subHeaderColorData = pinSubHeaderColor.getData();
        PinData<ImString> textColorData = pinTextColor.getData();
        PinData<ImFloat> sortOrderData = pinSortOrder.getData();

        PinData<ImString> outputData = outputPin.getData();

        if(inputVisibleTo.isConnected()){
            Pin connectedTo = inputVisibleTo.getConnectedPin();

            PinData<ImString> connectedData = connectedTo.getData();
            inputVisibleData.getValue().set(connectedData.getValue().get());
        }else{
            inputVisibleData.getValue().set("Null");
        }

        if(inputHeader.isConnected()){
            Pin connectedPin = inputHeader.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            headerData.getValue().set(connectedData.getValue().get());
        }else{
            headerData.getValue().set("Null");
        }

        if(inputSubHeader.isConnected()){
            Pin connectedPin = inputSubHeader.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            subHeaderData.getValue().set(connectedData.getValue().get());
        }else{
            subHeaderData.getValue().set("Null");
        }

        if(inputText.isConnected()){
            Pin connectedPin = inputText.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            textData.getValue().set(connectedData.getValue().get());
        }else{
            textData.getValue().set("Null");
        }

        if(pinHeaderColor.isConnected()){
            Pin connectedPin = pinHeaderColor.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            headerColorData.getValue().set(connectedData.getValue().get());
        }else{
            headerColorData.getValue().set("Color(White)");
        }

        if(pinSubHeaderColor.isConnected()){
            Pin connectedPin = pinSubHeaderColor.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            subHeaderColorData.getValue().set(connectedData.getValue().get());
        }else{
            subHeaderColorData.getValue().set("Color(White)");
        }

        if(pinTextColor.isConnected()){
            Pin connectedPin = pinTextColor.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            textColorData.getValue().set(connectedData.getValue().get());
        }else{
            textColorData.getValue().set("Color(White)");
        }

        if(pinSortOrder.isConnected()){
            Pin connectedPin = pinSortOrder.getConnectedPin();

            PinData<ImFloat> connectedData = connectedPin.getData();
            sortOrderData.getValue().set(connectedData.getValue().get());
        }


        String location = comboBox.getSelectedValue();
        String output = "Create HUD Text(" + inputVisibleData.getValue().get() + "," + headerData.getValue().get() + ", " + subHeaderData.getValue().get() + ", " + textData.getValue().get() + ", " + location + ", " + sortOrderData.getValue().get() + ", " + headerColorData.getValue().get()+ ", " + subHeaderColorData.getValue().get() + ", " +textColorData.getValue().get() + ", Visible To and String, Default Visibility);";
        outputData.getValue().set(output);
    }

    @Override
    public String getOutput() {
        PinData<ImString> output = outputPin.getData();

        return output.getValue().get();
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
