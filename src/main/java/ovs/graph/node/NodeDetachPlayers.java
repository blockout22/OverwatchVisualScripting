package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDetachPlayers extends Node{

    PinVar pinChildren = new PinVar();

    PinAction output = new PinAction();

    public NodeDetachPlayers(Graph graph) {
        super(graph);
        setName("Detach Players");

        pinChildren.setNode(this);
        pinChildren.setName("Children");
        addCustomInput(pinChildren);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> childData = pinChildren.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinChildren, childData);

        outputData.getValue().set(getName() + "(" + childData.getValue().get() + ");");

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
        return "Undoes the attachment caused by the attach players action for one or more players, these players will resume normal movement from their current position.";
    }
}
