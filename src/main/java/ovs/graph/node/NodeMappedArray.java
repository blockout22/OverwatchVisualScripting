package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeMappedArray extends Node {

    PinVar pinArray = new PinVar();
    PinVar pinMappingExpression = new PinVar();
    PinVar output = new PinVar();

    public NodeMappedArray(Graph graph) {
        super(graph);
        setName("Mapped Array");

        pinArray.setNode(this);
        pinArray.setName("Array");
        addCustomInput(pinArray);

        pinMappingExpression.setNode(this);
        pinMappingExpression.setName("Mapping Expression");

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> arrayData = pinArray.getData();
        PinData<ImString> mappingExpressionData = pinMappingExpression.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinArray, arrayData);
        handlePinStringConnection(pinMappingExpression, mappingExpressionData, "Current Array Element");

        outputData.getValue().set(getName() + "(" + arrayData.getValue().get() + ", " + mappingExpressionData.getValue().get() + ")");
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
        return "A copy of the specified array with the values mapped according to the mapping expression that is evaluated for each element.";
    }
}