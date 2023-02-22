package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinString;
import ovs.graph.pin.PinVar;

public class NodeCustomString extends Node{

    PinString input = new PinString();
    PinVar pinInput0 = new PinVar();
    PinVar pinInput1 = new PinVar();
    PinVar pinInput2 = new PinVar();
    PinVar output = new PinVar();

    public NodeCustomString(Graph graph) {
        super(graph);
        setName("Custom String");
        setColor(213, 232, 0);

        input.setNode(this);
        addCustomInput(input);

        pinInput0.setNode(this);
        pinInput0.setName("{0}");
        addCustomInput(pinInput0);

        pinInput1.setNode(this);
        pinInput1.setName("{1}");
        addCustomInput(pinInput1);

        pinInput2.setNode(this);
        pinInput2.setName("{2}");
        addCustomInput(pinInput2);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeCustomString){
            PinData<ImString> data = ((NodeCustomString)node).input.getData();

            PinData<ImString> inputData = input.getData();
            inputData.getValue().set(data.getValue().get());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = input.getData();
        PinData<ImString> input0Data = pinInput0.getData();
        PinData<ImString> input1Data = pinInput1.getData();
        PinData<ImString> input2Data = pinInput2.getData();
        PinData<ImString> outputData = output.getData();

        String toOutput = "Custom String(\"" + inputData.getValue().get() + "\"";

        if(pinInput0.isConnected()){
            Pin connectedPin = pinInput0.getConnectedPin();

            if(connectedPin != null) {
                PinData<ImString> connectedData = connectedPin.getData();
                input0Data.getValue().set(connectedData.getValue().get());

                toOutput += ", " + input0Data.getValue().get();
            }
        }

        if(pinInput1.isConnected()){
            Pin connectedPin = pinInput1.getConnectedPin();

            if(connectedPin != null) {
                PinData<ImString> connectedData = connectedPin.getData();
                input1Data.getValue().set(connectedData.getValue().get());

                toOutput += ", " + input1Data.getValue().get();
            }
        }

        if(pinInput2.isConnected()){
            Pin connectedPin = pinInput2.getConnectedPin();

            if(connectedPin != null) {
                PinData<ImString> connectedData = connectedPin.getData();
                input2Data.getValue().set(connectedData.getValue().get());

                toOutput += ", " + input2Data.getValue().get();
            }
        }

        outputData.getValue().set(toOutput + ")");
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
