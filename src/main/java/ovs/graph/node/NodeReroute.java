package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.node.interfaces.NodeDisabled;

@NodeDisabled
public class NodeReroute extends Node {

    public NodeReroute(Graph graph) {
        super(graph);
        setName("Reroute");

        setHasTitleBar(false);
    }

    @Override
    public void execute() {
        if(inputPins.size() > 0 && outputPins.size() > 0) {
            PinData<ImString> inputData = inputPins.get(0).getData();
            PinData<ImString> outputData = outputPins.get(0).getData();

            handlePinStringConnection(inputPins.get(0), inputData);

            outputData.getValue().set(inputData.getValue().get());
        }
    }

    @Override
    public String getOutput() {
        if(outputPins.size() > 0) {
            PinData<ImString> outputData = outputPins.get(0).getData();
            return outputData.getValue().get();
        }

        return "";
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "";
    }
}
