package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

import java.util.ArrayList;

public class NodeCreateProgressBarHUDText extends Node {

    PinVar pinVisibleTo = new PinVar();
    PinVar pinValue = new PinVar();
    PinVar pinText = new PinVar();
    PinCombo pinLocation = new PinCombo();
    PinVar pinSortOrder = new PinVar();
    PinVar pinBarColor = new PinVar();
    PinVar pinTextColor = new PinVar();
    PinCombo pinReevaluation = new PinCombo();
    PinCombo pinNonTeamSpectators = new PinCombo();
    PinAction output = new PinAction();

    public NodeCreateProgressBarHUDText(Graph graph) {
        super(graph);
        setName("Create Progress Bar HUD Text");

        pinVisibleTo.setNode(this);
        pinVisibleTo.setName("Visible To");
        addCustomInput(pinVisibleTo);

        pinValue.setNode(this);
        pinValue.setName("Value");
        addCustomInput(pinValue);

        pinText.setNode(this);
        pinText.setName("Text");
        addCustomInput(pinText);

        pinLocation.setNode(this);
        pinLocation.setName("Location");
        addCustomInput(pinLocation);

        pinSortOrder.setNode(this);
        pinSortOrder.setName("Sort Order");
        addCustomInput(pinSortOrder);

        pinBarColor.setNode(this);
        pinBarColor.setName("Progress Bar Color");
        addCustomInput(pinBarColor);

        pinTextColor.setNode(this);
        pinTextColor.setName("Text Color");
        addCustomInput(pinTextColor);

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

        pinNonTeamSpectators.setNode(this);
        pinNonTeamSpectators.setName("Non-Team Spectators");
        addCustomInput(pinNonTeamSpectators);

        output.setNode(this);
        addCustomOutput(output);

        pinLocation.getComboBox().addOption("Left");
        pinLocation.getComboBox().addOption("Top");
        pinLocation.getComboBox().addOption("Right");
        pinLocation.select(0);

        ArrayList<String> reevalOptions = new ArrayList<>();
        reevalOptions.add("Visible To Values And Color");
        reevalOptions.add("Visible To And Values");
        reevalOptions.add("Visible To And Color");
        reevalOptions.add("Visible To");
        reevalOptions.add("Values And Color");
        reevalOptions.add("Values");
        reevalOptions.add("Color");
        reevalOptions.add("None");
        pinReevaluation.getComboBox().addOptions(reevalOptions);
        pinReevaluation.select(0);

        ArrayList<String> specOptions = new ArrayList<>();
        specOptions.add("Default Visibility");
        specOptions.add("Visible Always");
        specOptions.add("Visible Never");
        pinNonTeamSpectators.getComboBox().addOptions(specOptions);
        pinNonTeamSpectators.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Location:" + pinLocation.getComboBox().getSelectedValue());
        getExtraSaveData().add("Reevaluation:" + pinReevaluation.getComboBox().getSelectedValue());
        getExtraSaveData().add("NonTeamSpectators:" + pinNonTeamSpectators.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Location")){
                try {
                    pinLocation.selectValue(data.split(":")[1]);
                }catch (IndexOutOfBoundsException e){
                    pinLocation.select(0);
                }
            }

            if(data.startsWith("Reevaluation")){
                try {
                    pinReevaluation.selectValue(data.split(":")[1]);
                }catch (IndexOutOfBoundsException e){
                    pinReevaluation.select(0);
                }
            }

            if(data.startsWith("NonTeamSpectators")){
                try {
                    pinNonTeamSpectators.selectValue(data.split(":")[1]);
                }catch (IndexOutOfBoundsException e){
                    pinNonTeamSpectators.select(0);
                }
            }
        }
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeCreateProgressBarHUDText) {
            pinLocation.selectValue(((NodeCreateProgressBarHUDText) node).pinLocation.getComboBox().getSelectedValue());
            pinReevaluation.selectValue(((NodeCreateProgressBarHUDText) node).pinReevaluation.getComboBox().getSelectedValue());
            pinNonTeamSpectators.selectValue(((NodeCreateProgressBarHUDText) node).pinNonTeamSpectators.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> visibleToData = pinVisibleTo.getData();
        PinData<ImString> valueData = pinValue.getData();
        PinData<ImString> textData = pinText.getData();
        PinData<ImString> sortOrderData = pinSortOrder.getData();
        PinData<ImString> barColorData = pinBarColor.getData();
        PinData<ImString> textColorData = pinTextColor.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVisibleTo, visibleToData, "Event Player");
        handlePinStringConnection(pinValue, valueData, "0");
        handlePinStringConnection(pinText, textData, "Custom String(\"\")");
        handlePinStringConnection(pinSortOrder, sortOrderData, "0");
        handlePinStringConnection(pinBarColor, barColorData, "Color(White)");
        handlePinStringConnection(pinTextColor, textColorData, "Color(White)");

        outputData.getValue().set(getName() + "(" + visibleToData.getValue().get() + ", " + valueData.getValue().get() + ", " + textData.getValue().get() + ", " + pinLocation.getComboBox().getSelectedValue() + ", " + sortOrderData.getValue().get() + ", " + barColorData.getValue().get() + ", " + textColorData.getValue().get() + ", " + pinReevaluation.getComboBox().getSelectedValue() + ", " + pinNonTeamSpectators.getComboBox().getSelectedValue() + ");");
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
        return "Creates a progress bar hud text visible to a specific players at a specific position in the world. this text will persist until destroyed, to obtain a reference to this text, use the last text id value, this action will fail if too many text elements have been created.";
    }
}