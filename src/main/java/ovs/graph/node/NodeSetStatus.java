package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeSetStatus extends Node {

//    ComboBox status = new ComboBox(Global.status);

    PinVar pinPlayer = new PinVar();
    PinVar pinAssister = new PinVar();
    PinCombo pinStatus = new PinCombo();
    PinVar pinDuration = new PinVar();

    PinAction output = new PinAction();

    public NodeSetStatus(Graph graph) {
        super(graph);
        setName("Set Status");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinAssister.setNode(this);
        pinAssister.setName("Assister");
        addCustomInput(pinAssister);

        pinStatus.setNode(this);
        pinStatus.setName("Status");
        addCustomInput(pinStatus);

        pinDuration.setNode(this);
        pinDuration.setName("Duration");
        addCustomInput(pinDuration);

        pinStatus.getComboBox().addOptions(Global.status);
        pinStatus.select(3);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Status:" + pinStatus.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Status")){
                pinStatus.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> assisterData = pinAssister.getData();
        PinData<ImString> statusData = pinStatus.getData();
        PinData<ImString> durationData = pinDuration.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinAssister, assisterData, "Null");
        handlePinStringConnection(pinStatus, statusData, pinStatus.getComboBox().getSelectedValue());
        handlePinStringConnection(pinDuration, durationData, "0");

        outputData.getValue().set("Set Status(" + playerData.getValue().get() + ", " + assisterData.getValue().get() + ", " + statusData.getValue().get() + ", " + durationData.getValue().get() + ");");
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
        return "Applies a status to one or more players. this status will remain in effect for the specified duration or until it is cleared by the clear status action.";
    }
}