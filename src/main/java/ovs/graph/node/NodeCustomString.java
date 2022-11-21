package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinString;
import ovs.graph.pin.PinVar;

public class NodeCustomString extends Node{

    PinString input = new PinString();
    PinVar output = new PinVar();

    public NodeCustomString(Graph graph) {
        super(graph);
        setName("Custom String");

        input.setNode(this);
        addCustomInput(input);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = input.getData();
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Custom String(\"" + inputData.getValue().get() + "\")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> data = output.getData();
        return data.getValue().get();
    }

    @Override
    public void UI() {

    }
}
