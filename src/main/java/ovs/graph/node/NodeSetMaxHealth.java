package ovs.graph.node;

import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinFloat;
import ovs.graph.pin.PinVar;

public class NodeSetMaxHealth extends Node{

    PinVar input = new PinVar();
    PinFloat inputHealth = new PinFloat();

    PinAction output = new PinAction();

    public NodeSetMaxHealth(Graph graph) {
        super(graph);
        setName("Set Max Health");

        input.setNode(this);
        addCustomInput(input);

        inputHealth.setNode(this);
        addCustomInput(inputHealth);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = input.getData();
        PinData<ImFloat> inputHealthData = inputHealth.getData();
        PinData<ImString> outputData = output.getData();

        if(input.isConnected()){
            Pin connectedPin = input.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            inputData.getValue().set(connectedData.getValue().get());
        }else{
            inputData.getValue().set("Null");
        }

        if(inputHealth.isConnected()){
            Pin connectedPin = inputHealth.getConnectedPin();

            PinData<ImFloat> connectedData = connectedPin.getData();
            inputHealthData.getValue().set(connectedData.getValue().get());
        }

        outputData.getValue().set("Set Max Health(" + inputData.getValue().get() + ", " + inputHealthData.getValue().get() + ");");
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
