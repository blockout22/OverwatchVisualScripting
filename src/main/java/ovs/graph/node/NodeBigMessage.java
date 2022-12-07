package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeBigMessage extends Node{

    PinVar pinHeader = new PinVar();
    PinAction output = new PinAction();

    public NodeBigMessage(Graph graph) {
        super(graph);
        setName("Big Message");

        pinHeader.setNode(this);
        pinHeader.setName("Data");
        addCustomInput(pinHeader);

        output.setNode(this);
        addCustomOutput(output);

    }

    @Override
    public void execute() {
        PinData<ImString> headerData = pinHeader.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinHeader, headerData, "Custom String(\"\", Null, Null, Null)");

        //TODO have a pin to allow selecting who sees the message;
        String who = "Event Player";
        outputData.getValue().set("Big Message(" + who + ", " + headerData.getValue().get() + ");");
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
