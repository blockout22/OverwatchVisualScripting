package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetUltimateCharge extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinPerc = new PinVar();

    PinAction output = new PinAction();

    public NodeSetUltimateCharge(Graph graph) {
        super(graph);
        setName("Set Ultimate Charge");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinPerc.setNode(this);
        pinPerc.setName("Charge Percentage");
        addCustomInput(pinPerc);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> percData = pinPerc.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinPerc, percData, "100");

        outputData.getValue().set("Set Ultimate Charge(" + playerData.getValue().get() + ", " + percData.getValue().get() + ");");
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
        return "Sets the ultimate charge for one or more players as a percentage of maximum charge";
    }
}