package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.Button;
import ovs.graph.UI.Listeners.LeftClickListener;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinCondition;

public class NodeConditionList extends Node{

    PinCondition inputPin = new PinCondition();

    PinCondition outputPin = new PinCondition();

    Button button = new Button("Add Input");

    public NodeConditionList(Graph graph) {
        super(graph);
        setName("Condition List");

        inputPin.setNode(this);
        addCustomInput(inputPin);

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        addUiComponent(button);

        button.addLeftClickListener(new LeftClickListener() {
            @Override
            public void onClicked() {
                Pin pin = new PinCondition();
                pin.setNode(self);
                pin.setCanDelete(true);
                addCustomInput(pin);
            }
        });
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = outputPin.getData();
        String output = "";
        for (int i = 0; i < inputPins.size(); i++) {
            Pin pin = inputPins.get(i);

            PinData<ImString> data = pin.getData();

            if(pin.isConnected()){
                Pin connectedPin = pin.getConnectedPin();

                PinData<ImString> connectedData = connectedPin.getData();

                output += "" + connectedData.getValue().get() + (connectedData.getValue().get().endsWith(";") ? "" : ";");
                output += "\n";
            }
        }

        outputData.getValue().set(output);
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = outputPin.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {

    }
}
