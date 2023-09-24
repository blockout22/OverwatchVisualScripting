package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodePlayEffect extends Node {

    PinVar pinVisibleTo = new PinVar();
    PinCombo pinType = new PinCombo();
    PinVar pinColor = new PinVar();
    PinVar pinPosition = new PinVar();
    PinVar pinRadius = new PinVar();

    PinAction output = new PinAction();

    public NodePlayEffect(Graph graph) {
        super(graph);
        setName("Play Effect");

        pinVisibleTo.setNode(this);
        pinVisibleTo.setName("Visible To");
        addCustomInput(pinVisibleTo);

        pinType.setNode(this);
        pinType.setName("Type");
        addCustomInput(pinType);

        pinColor.setNode(this);
        pinColor.setName("Color");
        addCustomInput(pinColor);

        pinPosition.setNode(this);
        pinPosition.setName("Position");
        addCustomInput(pinPosition);

        pinRadius.setNode(this);
        pinRadius.setName("Radius");
        addCustomInput(pinRadius);

        output.setNode(this);
        addCustomOutput(output);

        pinType.getComboBox().addOptions(Global.playEffectType);
        pinType.sort();

        pinType.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Type:" + pinType.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Type")){
                try{
                    pinType.getComboBox().selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinType.getComboBox().select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> visibleToData = pinVisibleTo.getData();
        PinData<ImString> typeData = pinType.getData();
        PinData<ImString> colorData = pinColor.getData();
        PinData<ImString> positionData = pinPosition.getData();
        PinData<ImString> radiusData = pinRadius.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVisibleTo, visibleToData);
        handlePinStringConnection(pinType, typeData, pinType.getComboBox().getSelectedValue());
        handlePinStringConnection(pinColor, colorData, "Color(White)");
        handlePinStringConnection(pinPosition, positionData, "Vector(0, 0, 0)");
        handlePinStringConnection(pinRadius, radiusData, "5");

        outputData.getValue().set("Play Effect(" + visibleToData.getValue().get() + ", " + typeData.getValue().get() + ", " + colorData.getValue().get() + ", " + positionData.getValue().get() + ", " + radiusData.getValue().get() + ");");
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