package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeCrossProduct extends Node {

    PinVar value1 = new PinVar();
    PinVar value2 = new PinVar();
    PinVar output = new PinVar();

    public NodeCrossProduct(Graph graph) {
        super(graph);
        setName("Cross Product");

        value1.setNode(this);
        value1.setName("Value");
        addCustomInput(value1);

        value2.setNode(this);
        value2.setName("Value");
        addCustomInput(value2);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> value1Data = value1.getData();
        PinData<ImString> value2Data = value2.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(value1, value1Data);
        handlePinStringConnection(value2, value2Data);

        outputData.getValue().set("Cross Product(" + value1Data.getValue().get() + ", " + value2Data.getValue().get() + ")");
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
        return "The cross product of the specified values. (left cross up equals forward.)";
    }
}