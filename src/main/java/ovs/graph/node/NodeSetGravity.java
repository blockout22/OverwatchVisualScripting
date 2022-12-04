package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetGravity extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinVal = new PinVar();

    PinAction output = new PinAction();

    public NodeSetGravity(Graph graph) {
        super(graph);
        setName("Set Gravity");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinVal.setNode(this);
        pinVal.setName("Gravity Percentage");
        addCustomInput(pinVal);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> percData = pinVal.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Null");
        handlePinStringConnection(pinVal, percData, "100");

        outputData.getValue().set("Set Gravity(" + playerData.getValue().get() + ", " + percData.getValue().get() + ");");

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
