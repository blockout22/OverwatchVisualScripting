package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeStartHoldingButton extends Node {

    PinVar pinPlayer = new PinVar();
    PinCombo pinButton = new PinCombo();
    PinAction output = new PinAction();

    public NodeStartHoldingButton(Graph graph) {
        super(graph);
        setName("Start Holding Button");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinButton.setNode(this);
        pinButton.setName("Button");
        addCustomInput(pinButton);

        output.setNode(this);
        addCustomOutput(output);

        pinButton.getComboBox().addOptions(Global.buttons);
        pinButton.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Button:" + pinButton.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Button")){
                try {
                    pinButton.selectValue(data.split(":")[1]);
                }catch (IndexOutOfBoundsException e){
                    pinButton.select(0);
                }
            }
        }
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeStartHoldingButton){
            pinButton.getComboBox().selectValue(((NodeStartHoldingButton) node).pinButton.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", Button(" + pinButton.getComboBox().getSelectedValue() + "));");

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
        return "Forces one or more players to hold a button virtually until stopped by the stop holding button action.";
    }
}