package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeStartFacing extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinDirection = new PinVar();
    PinVar pinTurnRate = new PinVar();
    PinCombo pinRelative = new PinCombo();
    PinCombo pinReevaluation = new PinCombo();
    PinAction output = new PinAction();

    public NodeStartFacing(Graph graph) {
        super(graph);
        setName("Start Facing");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinDirection.setNode(this);
        pinDirection.setName("Direction");
        addCustomInput(pinDirection);

        pinTurnRate.setNode(this);
        pinTurnRate.setName("Turn Rate");
        addCustomInput(pinTurnRate);

        pinRelative.setNode(this);
        pinRelative.setName("Relative");
        addCustomInput(pinRelative);

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

        output.setNode(this);
        addCustomOutput(output);

        pinRelative.addOption("To World");
        pinRelative.addOption("To Player");
        pinRelative.select(0);

        pinReevaluation.addOption("Direction And Turn Rate");
        pinReevaluation.addOption("None");
        pinReevaluation.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Relative:" + pinRelative.getComboBox().getSelectedValue());
        getExtraSaveData().add("Reevaluation:" + pinReevaluation.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Relative")){
                try {
                    pinRelative.selectValue(data.split(":")[1]);
                }catch (IndexOutOfBoundsException e){
                    pinRelative.select(0);
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
        if(node instanceof NodeStartFacing){
            pinRelative.getComboBox().selectValue(((NodeStartFacing) node).pinRelative.getComboBox().getSelectedValue());
            pinReevaluation.getComboBox().selectValue(((NodeStartFacing) node).pinReevaluation.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> directionData = pinDirection.getData();
        PinData<ImString> turnRateData = pinTurnRate.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");
        handlePinStringConnection(pinDirection, directionData, "Vector(0, 0, 0)");
        handlePinStringConnection(pinTurnRate, turnRateData, "100");

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + directionData.getValue().get() + ", " + turnRateData.getValue().get() + ", " + pinRelative.getComboBox().getSelectedValue() + ", " + pinReevaluation.getComboBox().getSelectedValue() + ");");

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
        return "Starts turning one or more players to face the specified direction.";
    }
}