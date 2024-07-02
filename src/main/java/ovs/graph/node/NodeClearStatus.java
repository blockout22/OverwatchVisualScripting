package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeClearStatus extends Node {

    ComboBox status = new ComboBox(Global.status);

    PinVar pinPlayer = new PinVar();
    PinAction output = new PinAction();

    public NodeClearStatus(Graph graph) {
        super(graph);
        setName("Clear Status");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        status.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Status:" + status.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Status")){
                status.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + status.getSelectedValue() + ");");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        status.show();
    }

    @Override
    public String getTooltip() {
        return "Clears a status that wazs applied from a set status action from one or more players.";
    }
}