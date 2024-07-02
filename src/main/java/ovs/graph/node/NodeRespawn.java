package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeRespawn extends Node{

    PinVar input = new PinVar();
    PinAction output = new PinAction();

    public NodeRespawn(Graph graph) {
        super(graph);
        setName("Respawn");

        input.setNode(this);
        input.setName("Player");
        addCustomInput(input);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = input.getData();
        PinData<ImString> outputData = output.getData();

        if(input.isConnected()){
            Pin connectedPin = input.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            inputData.getValue().set(connectedData.getValue().get());
        }

        outputData.getValue().set("Respawn(" + inputData.getValue().get() + ");");
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
        return "Respawns one or more players at an appropriate spawn location with full health, even if they were already alive.";
    }
}
