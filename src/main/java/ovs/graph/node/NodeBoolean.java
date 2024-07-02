package ovs.graph.node;

import imgui.type.ImBoolean;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinBoolean;
import ovs.graph.pin.PinVar;

public class NodeBoolean extends Node{

    PinBoolean input = new PinBoolean();

    PinVar output = new PinVar();

    public NodeBoolean(Graph graph) {
        super(graph);
        setName("Boolean");

        input.setNode(this);
        addCustomInput(input);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImBoolean> inputData = input.getData();
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set(inputData.getValue().get() ? "True" : "False");
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
        return "The Boolean Value or either true or false";
    }
}
