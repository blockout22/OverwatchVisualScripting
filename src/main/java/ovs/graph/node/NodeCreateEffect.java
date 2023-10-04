package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

import java.util.ArrayList;

public class NodeCreateEffect extends Node{

    PinVar pinVisibleTo = new PinVar();
    PinCombo pinType = new PinCombo();
    PinVar pinColor = new PinVar();
    PinVar pinPosition = new PinVar();
    PinVar pinRadius = new PinVar();
    PinCombo pinReevaluation = new PinCombo();

    PinAction output = new PinAction();

    public NodeCreateEffect(Graph graph) {
        super(graph);
        setName("Create Effect");
        setColor(255, 0, 0);

        pinVisibleTo.setNode(this);
        pinVisibleTo.setName("Visible To");
        addCustomInput(pinVisibleTo);

        pinType.setNode(this);
        pinType.setName("Type");
        addCustomInput(pinType);

        populateTypeOptions();

        pinColor.setNode(this);
        pinColor.setName("Color");
        addCustomInput(pinColor);

        pinPosition.setNode(this);
        pinPosition.setName("Position");
        addCustomInput(pinPosition);

        pinRadius.setNode(this);
        pinRadius.setName("Radius");
        addCustomInput(pinRadius);

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

        output.setNode(this);
        addCustomOutput(output);

        pinType.select(0);

        ArrayList<String> reevalOptions = new ArrayList<>();
        reevalOptions.add("Visible To, Position, And Radius");
        reevalOptions.add("Position And Radius");
        reevalOptions.add("Visible To");
        reevalOptions.add("None");
        reevalOptions.add("Visible To, Position, Radius, And Color");
        reevalOptions.add("Position, Radius, And Color");
        reevalOptions.add("Visible To And Color");
        reevalOptions.add("Color");

        pinReevaluation.getComboBox().addOptions(reevalOptions);
        pinReevaluation.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Type:" + pinType.getComboBox().getSelectedValue());
        getExtraSaveData().add("Reevaluation:" + pinReevaluation.getComboBox().getSelectedValue());
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
        if(node instanceof NodeCreateEffect){
            pinType.getComboBox().selectValue(((NodeCreateEffect) node).pinType.getComboBox().getSelectedValue());
            pinReevaluation.selectValue(((NodeCreateEffect) node).pinReevaluation.getComboBox().getSelectedValue());
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
//        handlePinStringConnection(pinReevaluation, reevaluationData, "Visible To Position and Radius");

        outputData.getValue().set("Create Effect (" + visibleToData.getValue().get() + ", " + typeData.getValue().get() + ", " + colorData.getValue().get() + ", " + positionData.getValue().get() + ", " + radiusData.getValue().get() + ", " + pinReevaluation.getComboBox().getSelectedValue() + ");");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    private void populateTypeOptions(){
        for (int i = 0; i < Global.effectType.size(); i++) {
            String effectType = Global.effectType.get(i);
            pinType.addOption(effectType);
        }

        pinType.sort();
    }

    @Override
    public void UI() {

    }
}
