package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDetachPlayer extends Node{

    PinVar pinChildren = new PinVar();

    PinAction output = new PinAction();

    public NodeDetachPlayer(Graph graph) {
        super(graph);
        setName("Detach Player");

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

        outputData.getValue().set("Detach Players(" + childData.getValue().get() + ");");

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
