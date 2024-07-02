package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.Button;
import ovs.graph.UI.Listeners.LeftClickListener;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinVar;

public class NodeArray extends Node {

    PinVar output = new PinVar();

    Button button = new Button("Add");

    public NodeArray(Graph graph) {
        super(graph);
        setName("Array");

        output.setNode(this);
        addCustomOutput(output);

        addUiComponent(button);
        button.addLeftClickListener(new LeftClickListener() {
            @Override
            public void onClicked() {
                Pin pin = new PinVar();
                pin.setNode(self);
                pin.setCanDelete(true);
                addCustomInput(pin);
            }
        });
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        if(inputPins.getList().isEmpty()){
            outputData.getValue().set("Empty Array");
        }else{
            String outputVal = "";
            for (int i = 0; i < inputPins.size(); i++) {
                Pin pin = inputPins.get(i);

                PinData<ImString> data = pin.getData();

                handlePinStringConnection(pin, data);

                if(pin.isConnected()){
                    Pin connectedPin = pin.getConnectedPin();

                    PinData<ImString> connectedData = connectedPin.getData();
                    outputVal += connectedData.getValue().get();

                    if(i < inputPins.size() - 1){
                        outputVal += ", ";
                    }
                }
            }
            outputData.getValue().set("Array(" + outputVal + ")");
        }

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
        return "An array constructed from the listed values.";
    }
}