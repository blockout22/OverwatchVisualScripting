package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeAttachPlayers extends Node{

    PinVar pinChild = new PinVar();
    PinVar pinParent = new PinVar();
    PinVar pinOffset = new PinVar();

    PinAction output = new PinAction();

    public NodeAttachPlayers(Graph graph) {
        super(graph);
        setName("Attach Players");

        pinChild.setNode(this);
        pinChild.setName("Child");
        addCustomInput(pinChild);

        pinParent.setNode(this);
        pinParent.setName("Parent");
        addCustomInput(pinParent);

        pinOffset.setNode(this);
        pinOffset.setName("Offset");
        addCustomInput(pinOffset);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> childData = pinChild.getData();
        PinData<ImString> parentData = pinParent.getData();
        PinData<ImString> offsetData = pinOffset.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinChild, childData);
        handlePinStringConnection(pinParent, parentData);
        handlePinStringConnection(pinOffset, offsetData);

        outputData.getValue().set("Attach Players(" + childData.getValue().get() + ", " + parentData.getValue().get() + ", " + offsetData.getValue().get() + ");");
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
