package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeCreateBeamEffect extends Node {

    PinVar pinVisibleTo = new PinVar();
    PinCombo pinBeamType = new PinCombo();
    PinVar pinStartPos = new PinVar();
    PinVar pinEndPos = new PinVar();
    PinVar pinColor = new PinVar();
    PinCombo pinReevaluation = new PinCombo();
    PinAction output = new PinAction();

    public NodeCreateBeamEffect(Graph graph) {
        super(graph);
        setName("Create Beam Effect");

        pinVisibleTo.setNode(this);
        pinVisibleTo.setName("Visible To");
        addCustomInput(pinVisibleTo);

        pinBeamType.setNode(this);
        pinBeamType.setName("Beam Type");
        addCustomInput(pinBeamType);

        pinBeamType.addOption("Good Beam");
        pinBeamType.addOption("Bad Beam");
        pinBeamType.addOption("Grapple Beam");
        pinBeamType.select(0);

        pinStartPos.setNode(this);
        pinStartPos.setName("Start Position");
        addCustomInput(pinStartPos);

        pinEndPos.setNode(this);
        pinEndPos.setName("End Position");
        addCustomInput(pinEndPos);

        pinColor.setNode(this);
        pinColor.setName("Color");
        addCustomInput(pinColor);

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

        pinReevaluation.addOption("Visible To Position and Radius");
        pinReevaluation.addOption("Position and Radius");
        pinReevaluation.addOption("Position And Radius");
        pinReevaluation.addOption("Visible To");
        pinReevaluation.addOption("None");
        pinReevaluation.addOption("Visible To Position Radius and Color");
        pinReevaluation.addOption("Position Radius and Color");
        pinReevaluation.addOption("Color");
        pinReevaluation.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Beam_Type:" + pinBeamType.getComboBox().getSelectedValue());
        getExtraSaveData().add("Reevaluation:" + pinReevaluation.getComboBox().getSelectedValue());
    }

    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Beam_Type")){
                try{
                    pinBeamType.getComboBox().selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinBeamType.getComboBox().select(-1);
                }
            }

            if(data.startsWith("Reevaluation")){
                try{
                    pinReevaluation.getComboBox().selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinReevaluation.getComboBox().select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> visibleToData =  pinVisibleTo.getData();
        PinData<ImString> beamTypeData =  pinBeamType.getData();
        PinData<ImString> startPosData =  pinStartPos.getData();
        PinData<ImString> endPosData =  pinEndPos.getData();
        PinData<ImString> colorData =  pinColor.getData();
        PinData<ImString> reevaluationData = pinReevaluation.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVisibleTo, visibleToData);
        handlePinStringConnection(pinBeamType, beamTypeData, pinBeamType.getComboBox().getSelectedValue());
        handlePinStringConnection(pinStartPos, startPosData);
        handlePinStringConnection(pinEndPos, endPosData);
        handlePinStringConnection(pinColor, colorData, "Color(White)");
        handlePinStringConnection(pinReevaluation, reevaluationData, pinReevaluation.getComboBox().getSelectedValue());

        outputData.getValue().set(getName() + "(" + visibleToData.getValue() + ", " + beamTypeData.getValue().get() + ", " + startPosData.getValue().get() + ", " + endPosData.getValue().get() + ", " + colorData.getValue().get() + ", " + reevaluationData.getValue().get() + ");");
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
        return "Creates an in-world beam effect entity. this effect entity will persist until destroyed. to obtain a references to this entity, use the last created entity value. this action will fail if too many entities have been created.";
    }
}