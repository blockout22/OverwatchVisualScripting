package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeRemoveHealthPoolsFromPlayer extends Node {

    PinVar pinHealthPoolId = new PinVar();
    PinAction output = new PinAction();

    public NodeRemoveHealthPoolsFromPlayer(Graph graph) {
        super(graph);
        setName("Remove Health Pools From Player");

        pinHealthPoolId.setNode(this);
        pinHealthPoolId.setName("Health Pool ID");
        addCustomInput(pinHealthPoolId);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> healthPoolIdData = pinHealthPoolId.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinHealthPoolId, healthPoolIdData, "Last Created Health Pool");

        outputData.getValue().set(getName() + "(" + healthPoolIdData.getValue().get() + ");");
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
        return "Removes a health pools that were added via the add health pool action.";
    }
}