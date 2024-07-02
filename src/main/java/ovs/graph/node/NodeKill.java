package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeKill extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinKiller = new PinVar();
    PinAction output = new PinAction();

    public NodeKill(Graph graph) {
        super(graph);
        setName("Kill");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinKiller.setNode(this);
        pinKiller.setName("Killer");
        addCustomInput(pinKiller);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> killerData = pinKiller.getData();
        PinData<ImString> data = output.getData();

        if(pinPlayer.isConnected()){
            Pin connectedPin = pinPlayer.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            playerData.getValue().set(connectedData.getValue().get());
        }else{
            playerData.getValue().set("Null");
        }

        if(pinKiller.isConnected()){
            Pin connectedPin = pinKiller.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            killerData.getValue().set(connectedData.getValue().get());
        }else{
            killerData.getValue().set("Null");
        }

        data.getValue().set("Kill(" + playerData.getValue().get() + ", " + killerData.getValue().get() + ");");
    }

    @Override
    public String getOutput() {
        PinData<ImString> data = output.getData();
        return data.getValue().get();
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "Instantly kills one or more players.";
    }
}
