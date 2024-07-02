package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetInvisible extends Node {

    ComboBox invisTo = new ComboBox("All", "Enemies", "None");

    PinVar pinPlayer = new PinVar();

    PinAction output = new PinAction();

    public NodeSetInvisible(Graph graph) {
        super(graph);
        setName("Set Invisible");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Invisible:" + invisTo.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Invisible")){
                invisTo.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);

        outputData.getValue().set("Set Invisible(" + playerData.getValue().get() + ", " + invisTo.getSelectedValue() + ");");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        invisTo.show();

    }

    @Override
    public String getTooltip() {
        return "Causes one or more players to become invisible to either all other players or just the enemies.";
    }
}