package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeCreateInWorldText extends Node {

    PinVar pinVisibleTo  = new PinVar();
    PinVar pinHeader = new PinVar();
    PinVar pinPosition = new PinVar();
    PinVar pinScale = new PinVar();
    PinCombo pinClipping = new PinCombo();
    PinCombo pinReevaluation = new PinCombo();
    PinVar pinTextColor = new PinVar();
    PinCombo pinNonTeamSpectators = new PinCombo();
    PinAction output = new PinAction();

    public NodeCreateInWorldText(Graph graph) {
        super(graph);
        setName("Create In-World Text");

        pinVisibleTo.setNode(this);
        pinVisibleTo.setName("Visible To");
        addCustomInput(pinVisibleTo);

        pinHeader.setNode(this);
        pinHeader.setName("Header");
        addCustomInput(pinHeader);

        pinPosition.setNode(this);
        pinPosition.setName("Position");
        addCustomInput(pinPosition);

        pinScale.setNode(this);
        pinScale.setName("Scale");
        addCustomInput(pinScale);

        pinClipping.setNode(this);
        pinClipping.setName("Clipping");
        addCustomInput(pinClipping);

        pinClipping.addOption("Clip Against Surfaces");
        pinClipping.addOption("Do Not Clip");
        pinClipping.select(0);

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

        pinReevaluation.addOption("Color");
        pinReevaluation.addOption("None");
        pinReevaluation.addOption("String");
        pinReevaluation.addOption("String And Color");
        pinReevaluation.addOption("Visible To");
        pinReevaluation.addOption("Visible To And Color");
        pinReevaluation.addOption("Visible To And Position");
        pinReevaluation.addOption("Visible To And String");
        pinReevaluation.addOption("Visible To Position And Color");
        pinReevaluation.addOption("Visible To Position And String");
        pinReevaluation.addOption("Visible To String And Color");
        pinReevaluation.addOption("Visible To Position String And Color");

        pinReevaluation.select(11);

        pinTextColor.setNode(this);
        pinTextColor.setName("Text Color");
        addCustomInput(pinTextColor);

        pinNonTeamSpectators.setNode(this);
        pinNonTeamSpectators.setName("Non-Team Spectators");
        addCustomInput(pinNonTeamSpectators);

        pinNonTeamSpectators.addOption("Default Visibility");
        pinNonTeamSpectators.addOption("Visible Always");
        pinNonTeamSpectators.addOption("Visible Never");
        pinNonTeamSpectators.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Spectator:" + pinNonTeamSpectators.getComboBox().getSelectedValue());
        getExtraSaveData().add("Clipping:" + pinClipping.getComboBox().getSelectedValue());
        getExtraSaveData().add("Reevaluation:" + pinReevaluation.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Clipping")){
                try{
                    pinClipping.getComboBox().selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinClipping.getComboBox().select(-1);
                }
            }

            if(data.startsWith("Reevaluation")){
                try{
                    pinReevaluation.getComboBox().selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinReevaluation.getComboBox().select(-1);
                }
            }

            if(data.startsWith("Spectator")){
                try{
                    pinNonTeamSpectators.getComboBox().selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinNonTeamSpectators.getComboBox().select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> visibleToData = pinVisibleTo.getData();
        PinData<ImString> headerData = pinHeader.getData();
        PinData<ImString> positionData = pinPosition.getData();
        PinData<ImString> scaleData = pinScale.getData();
        PinData<ImString> clippingData = pinClipping.getData();
        PinData<ImString> reevaluationData = pinReevaluation.getData();
        PinData<ImString> textColorData = pinTextColor.getData();
        PinData<ImString> nonTeamSpectatorsData = pinNonTeamSpectators.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVisibleTo, visibleToData);
        handlePinStringConnection(pinHeader, headerData);
        handlePinStringConnection(pinPosition, positionData);
        handlePinStringConnection(pinScale, scaleData, "1");
        handlePinStringConnection(pinClipping, clippingData, pinClipping.getComboBox().getSelectedValue());
        handlePinStringConnection(pinReevaluation, reevaluationData, pinReevaluation.getComboBox().getSelectedValue());
        handlePinStringConnection(pinTextColor, textColorData, "Color(White)");
        handlePinStringConnection(pinNonTeamSpectators, nonTeamSpectatorsData, pinNonTeamSpectators.getComboBox().getSelectedValue());

        outputData.getValue().set(getName() + "(" + visibleToData.getValue().get() + ", " + headerData.getValue().get() + ", " + positionData.getValue().get() + ", " + scaleData.getValue().get() + ", " + clippingData.getValue().get() + ", " + reevaluationData.getValue().get() + ", " + textColorData.getValue().get() + ", " + nonTeamSpectatorsData.getValue().get() + ");");
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