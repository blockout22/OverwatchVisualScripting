package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetProjectileSpeed extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinPerc = new PinVar();

    PinAction output = new PinAction();

    public NodeSetProjectileSpeed(Graph graph) {
        super(graph);
        setName("Set Projectile Speed");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinPerc.setNode(this);
        pinPerc.setName("Percentage");
        addCustomInput(pinPerc);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> percData = pinPerc.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Null");
        handlePinStringConnection(pinPerc, percData, "100");

        outputData.getValue().set("Set Projectile Speed(" + playerData.getValue().get() + ", " + percData.getValue().get() + ");");

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
