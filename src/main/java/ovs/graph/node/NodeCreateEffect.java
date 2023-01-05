package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeCreateEffect extends Node{

    PinVar pinVisibleTo = new PinVar();
    PinCombo pinType = new PinCombo();
    PinVar pinColor = new PinVar();
    PinVar pinPosition = new PinVar();
    PinVar pinRadius = new PinVar();
    PinVar pinReevaluation = new PinVar();

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
    }

    @Override
    public void execute() {
        PinData<ImString> visibleToData = pinVisibleTo.getData();
        PinData<ImString> typeData = pinType.getData();
        PinData<ImString> colorData = pinColor.getData();
        PinData<ImString> positionData = pinPosition.getData();
        PinData<ImString> radiusData = pinRadius.getData();
        PinData<ImString> reevaluationData = pinReevaluation.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVisibleTo, visibleToData);
        handlePinStringConnection(pinType, typeData);
        handlePinStringConnection(pinColor, colorData, "Color(White)");
        handlePinStringConnection(pinPosition, positionData, "Vector(0, 0, 0)");
        handlePinStringConnection(pinRadius, radiusData, "5");
        handlePinStringConnection(pinReevaluation, reevaluationData, "Visible To Position and Radius");

        outputData.getValue().set("Create Effect (" + visibleToData.getValue().get() + ", " + typeData.getValue().get() + ", " + colorData.getValue().get() + ", " + positionData.getValue().get() + ", " + radiusData.getValue().get() + ", " + reevaluationData.getValue().get() + ");");
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
