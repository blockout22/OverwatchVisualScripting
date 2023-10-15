package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

import java.util.ArrayList;

public class NodeCreateIcon extends Node {

    PinVar pinVisibleTo = new PinVar();
    PinVar pinPosition = new PinVar();
    PinCombo pinIcon = new PinCombo();
    PinCombo pinReevaluation = new PinCombo();
    PinVar pinColor = new PinVar();
    PinVar pinShowOffScreen = new PinVar();
    PinAction output = new PinAction();

    public NodeCreateIcon(Graph graph) {
        super(graph);
        setName("Create Icon");

        pinVisibleTo.setNode(this);
        pinVisibleTo.setName("Visible To");
        addCustomInput(pinVisibleTo);

        pinPosition.setNode(this);
        pinPosition.setName("Position");
        addCustomInput(pinPosition);

        pinIcon.setNode(this);
        pinIcon.setName("Icon");
        addCustomInput(pinIcon);

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

        pinColor.setNode(this);
        pinColor.setName("Color");
        addCustomInput(pinColor);

        pinShowOffScreen.setNode(this);
        pinShowOffScreen.setName("Show Off Screen");
        addCustomInput(pinShowOffScreen);

        output.setNode(this);
        addCustomOutput(output);

        pinIcon.getComboBox().addOptions(Global.icons);
        pinIcon.select(0);

        ArrayList<String> reevalOptions = new ArrayList<>();
        reevalOptions.add("Visible To And Position");
        reevalOptions.add("Position");
        reevalOptions.add("Visible To");
        reevalOptions.add("None");
        reevalOptions.add("Visible To Position And Color");
        reevalOptions.add("Position And Color");
        reevalOptions.add("Visible To And Color");
        reevalOptions.add("Color");
        pinReevaluation.getComboBox().addOptions(reevalOptions);
        pinReevaluation.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Icon:" + pinIcon.getComboBox().getSelectedValue());
        getExtraSaveData().add("Reevaluation:" + pinReevaluation.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Icon")){
                try{
                    pinIcon.getComboBox().selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinIcon.getComboBox().select(0);
                }
            }

            if(data.startsWith("Reevaluation")){
                try{
                    pinReevaluation.getComboBox().selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinReevaluation.getComboBox().select(0);
                }
            }
        }
    }

    public void copy(Node node) {
        if(node instanceof NodeCreateIcon){
            pinIcon.getComboBox().selectValue(((NodeCreateIcon) node).pinIcon.getComboBox().getSelectedValue());
            pinReevaluation.selectValue(((NodeCreateIcon) node).pinReevaluation.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> visibleToData = pinVisibleTo.getData();
        PinData<ImString> positionData = pinPosition.getData();
        PinData<ImString> colorData = pinColor.getData();
        PinData<ImString> showOffScreenData = pinShowOffScreen.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVisibleTo, visibleToData);
        handlePinStringConnection(pinPosition, positionData);
        handlePinStringConnection(pinColor, colorData);
        handlePinStringConnection(pinShowOffScreen, showOffScreenData, "True");

        outputData.getValue().set(getName() + "(" + visibleToData.getValue().get() + ", " + positionData.getValue().get() + ", " + pinIcon.getComboBox().getSelectedValue() + ", " + pinReevaluation.getComboBox().getSelectedValue() + ", " + colorData.getValue().get() + ", " + showOffScreenData.getValue().get() + ");");
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