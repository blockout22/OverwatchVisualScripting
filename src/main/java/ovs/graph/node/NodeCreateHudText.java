package ovs.graph.node;

import imgui.ImGui;
import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.*;

import java.util.ArrayList;

public class NodeCreateHudText extends Node{

    PinVar inputVisibleTo = new PinVar();
    PinVar inputHeader = new PinVar();
    PinVar inputSubHeader = new PinVar();
    PinVar inputText = new PinVar();

    PinVar pinHeaderColor = new PinVar();
    PinVar pinSubHeaderColor = new PinVar();
    PinVar pinTextColor = new PinVar();

    PinCombo pinReevaluation = new PinCombo();

    PinVar pinSortOrder = new PinVar();

    PinAction outputPin = new PinAction();

    ComboBox comboBox = new ComboBox();

    public NodeCreateHudText(Graph graph) {
        super(graph);
        setName("Create Hud Text");
        setColor(255, 0, 0);

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

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

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

        ArrayList<String> reevalOptions = new ArrayList<>();
        reevalOptions.add("Visible To And String");
        reevalOptions.add("String");
        reevalOptions.add("Visible To Sort Order And String");
        reevalOptions.add("Sort Order And String");
        reevalOptions.add("Visible To");
        reevalOptions.add("Sort Order");
        reevalOptions.add("None");
        reevalOptions.add("Visible To String, And Color");
        reevalOptions.add("String And Color");
        reevalOptions.add("Visible To Sort Order String And Color");
        reevalOptions.add("Sort Order String, And Color");

        pinReevaluation.getComboBox().addOptions(reevalOptions);
        pinReevaluation.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Location:" + comboBox.getSelectedValue());
        getExtraSaveData().add("Reevaluation:" + pinReevaluation.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Location:"))
            {
                comboBox.selectValue(data.split(":")[1]);
            }

            if(data.startsWith("Reevaluation")){
                try {
                    pinReevaluation.selectValue(data.split(":")[1]);
                }catch (IndexOutOfBoundsException e){
                    pinReevaluation.select(0);
                }
            }
        }
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeCreateHudText){
            comboBox.selectValue(((NodeCreateHudText) node).comboBox.getSelectedValue());
            pinReevaluation.selectValue(((NodeCreateHudText) node).pinReevaluation.getComboBox().getSelectedValue());
//            PinData<ImFloat> sortValue = ((NodeCreateHudText)node).pinSortOrder.getData();
//
//            PinData<ImFloat> sort = pinSortOrder.getData();
//
//            sort.getValue().set(sortValue.getValue().get());
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
        PinData<ImString> sortOrderData = pinSortOrder.getData();

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

        handlePinStringConnection(pinSortOrder, sortOrderData, "0");


        String location = comboBox.getSelectedValue();
        String output = "Create HUD Text(" + inputVisibleData.getValue().get() + "," + headerData.getValue().get() + ", " + subHeaderData.getValue().get() + ", " + textData.getValue().get() + ", " + location + ", " + sortOrderData.getValue().get() + ", " + headerColorData.getValue().get()+ ", " + subHeaderColorData.getValue().get() + ", " +textColorData.getValue().get() + ", "+pinReevaluation.getComboBox().getSelectedValue() +", Default Visibility);";
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

    @Override
    public String getTooltip() {
        return "Creates hud text visible to specific players at a specific location on the screen, this text will persist until destroyed, to obtain a reference to this text, use the last text id value, this action will fail if too many text elements have been created.";
    }
}
