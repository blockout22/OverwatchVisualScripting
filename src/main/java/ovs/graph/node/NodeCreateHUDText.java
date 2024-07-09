package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.*;

import java.util.ArrayList;

public class NodeCreateHUDText extends Node{

    PinVar pinVisibleTo = new PinVar();
    PinVar pinHeader = new PinVar();
    PinVar pinSubHeader = new PinVar();
    PinVar pinText = new PinVar();
    PinCombo pinLocation = new PinCombo();
    PinVar pinSortOrder = new PinVar();
    PinVar pinHeaderColor = new PinVar();
    PinVar pinSubHeaderColor = new PinVar();
    PinVar pinTextColor = new PinVar();
    PinCombo pinReevaluation = new PinCombo();
    PinCombo pinNonTeamSpectators = new PinCombo();

    PinAction outputPin = new PinAction();


    public NodeCreateHUDText(Graph graph) {
        super(graph);
        setName("Create HUD Text");
        setColor(255, 0, 0);

        pinVisibleTo.setNode(this);
        pinVisibleTo.setName("Visible To");
        addCustomInput(pinVisibleTo);

        pinHeader.setNode(this);
        pinHeader.setName("Header");
        addCustomInput(pinHeader);

        pinSubHeader.setNode(this);
        pinSubHeader.setName("Subheader");
        addCustomInput(pinSubHeader);

        pinText.setNode(this);
        pinText.setName("Text");
        addCustomInput(pinText);

        pinLocation.setNode(this);
        pinLocation.setName("Location");
        addCustomInput(pinLocation);

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

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

        pinNonTeamSpectators.setNode(this);
        pinNonTeamSpectators.setName("Non-Team Spectators");
        addCustomInput(pinNonTeamSpectators);

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        pinLocation.addOption("Left");
        pinLocation.addOption("Top");
        pinLocation.addOption("Right");

        pinLocation.select(0);

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

        pinNonTeamSpectators.addOption("Default Visibility");
        pinNonTeamSpectators.addOption("Visible Always");
        pinNonTeamSpectators.addOption("Visible Never");
        pinNonTeamSpectators.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Location:" + pinLocation.getComboBox().getSelectedValue());
        getExtraSaveData().add("Non_Team_Spectators:" + pinNonTeamSpectators.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Location:"))
            {
                pinLocation.selectValue(data.split(":")[1]);
            }

            if(data.startsWith("Reevaluation")){
                try {
                    pinReevaluation.selectValue(data.split(":")[1]);
                }catch (IndexOutOfBoundsException e){
                    pinReevaluation.select(0);
                }
            }

            if(data.startsWith("Non_Team_Spectators")){
                pinNonTeamSpectators.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeCreateHUDText){
            pinLocation.getComboBox().selectValue(((NodeCreateHUDText) node).pinLocation.getComboBox().getSelectedValue());
            pinReevaluation.selectValue(((NodeCreateHUDText) node).pinReevaluation.getComboBox().getSelectedValue());
            pinNonTeamSpectators.selectValue(((NodeCreateHUDText) node).pinNonTeamSpectators.getComboBox().getSelectedValue());
//            PinData<ImFloat> sortValue = ((NodeCreateHudText)node).pinSortOrder.getData();
//
//            PinData<ImFloat> sort = pinSortOrder.getData();
//
//            sort.getValue().set(sortValue.getValue().get());
        }
    }


    @Override
    public void execute() {
        PinData<ImString> visibleToData = pinVisibleTo.getData();
        PinData<ImString> headerData = pinHeader.getData();
        PinData<ImString> subHeaderData = pinSubHeader.getData();
        PinData<ImString> textData = pinText.getData();
        PinData<ImString> locationData = pinLocation.getData();
        PinData<ImString> sortOrderData = pinSortOrder.getData();
        PinData<ImString> headerColorData = pinHeaderColor.getData();
        PinData<ImString> subHeaderColorData = pinSubHeaderColor.getData();
        PinData<ImString> textColorData = pinTextColor.getData();
        PinData<ImString> reevaluationData = pinReevaluation.getData();
        PinData<ImString> nonTeamSpectatorsData = pinNonTeamSpectators.getData();

        PinData<ImString> outputData = outputPin.getData();

        handlePinStringConnection(pinVisibleTo, visibleToData, "Event Player");
        handlePinStringConnection(pinHeader, headerData, "Null");
        handlePinStringConnection(pinSubHeader, subHeaderData, "Null");
        handlePinStringConnection(pinText, textData, "Null");
        handlePinStringConnection(pinLocation, locationData, pinLocation.getComboBox().getSelectedValue());
        handlePinStringConnection(pinSortOrder, sortOrderData, "0");
        handlePinStringConnection(pinHeaderColor, headerColorData, "Color(White)");
        handlePinStringConnection(pinSubHeaderColor, subHeaderColorData, "Color(White)");
        handlePinStringConnection(pinTextColor, textColorData, "Color(White)");
        handlePinStringConnection(pinReevaluation, reevaluationData, pinReevaluation.getComboBox().getSelectedValue());
        handlePinStringConnection(pinNonTeamSpectators, nonTeamSpectatorsData, pinNonTeamSpectators.getComboBox().getSelectedValue());


//        String location = comboBox.getSelectedValue();
//        String output = "Create HUD Text(" + visibleToData.getValue().get() + "," + headerData.getValue().get() + ", " + subHeaderData.getValue().get() + ", " + textData.getValue().get() + ", " + location + ", " + sortOrderData.getValue().get() + ", " + headerColorData.getValue().get()+ ", " + subHeaderColorData.getValue().get() + ", " +textColorData.getValue().get() + ", "+pinReevaluation.getComboBox().getSelectedValue() +", Default Visibility);";
        outputData.getValue().set(getName() + "(" + visibleToData.getValue().get() + ", " + headerData.getValue().get() + ", " + subHeaderData.getValue().get() + ", " + textData.getValue().get() + ", " + locationData.getValue().get() + ", " + sortOrderData.getValue().get() + ", " + headerColorData.getValue().get() + ", " + subHeaderColorData.getValue().get() + ", " + textColorData.getValue().get() + ", " + reevaluationData.getValue().get() + ", " + nonTeamSpectatorsData.getValue().get() + ");");
    }

    @Override
    public String getOutput() {
        PinData<ImString> output = outputPin.getData();

        return output.getValue().get();
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "Creates hud text visible to specific players at a specific location on the screen, this text will persist until destroyed, to obtain a reference to this text, use the last text id value, this action will fail if too many text elements have been created.";
    }
}
