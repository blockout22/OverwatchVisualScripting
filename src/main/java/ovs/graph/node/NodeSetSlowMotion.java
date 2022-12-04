package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetSlowMotion extends Node{

    PinVar input = new PinVar();
    PinAction output = new PinAction();

    public NodeSetSlowMotion(Graph graph) {
        super(graph);
        setName("Set Slow Motion");

        input.setNode(this);
        input.setName("Speed Percentage");
        addCustomInput(input);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = input.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(input, inputData, "100");

        outputData.getValue().set("Set Slow Motion(" + inputData.getValue().get() + ");");

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
