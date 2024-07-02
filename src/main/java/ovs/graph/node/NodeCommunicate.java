package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeCommunicate extends Node {

    ComboBox type = new ComboBox(Global.communicationLines);

    PinVar pinPlayer = new PinVar();
    PinAction output = new PinAction();

    public NodeCommunicate(Graph graph) {
        super(graph);
        setName("Communicate");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        type.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Type:" + type.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Type"))
            {
                type.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {

        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + type.getSelectedValue() + ");");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        type.show();
    }

    @Override
    public String getTooltip() {
        return "Causes one or more players to use an emote, voice line, or other equipped communications.";
    }
}