package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.Button;
import ovs.graph.UI.Listeners.LeftClickListener;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;

public class NodeActionList extends Node{

    private PinAction inputPin = new PinAction();

    private PinAction outputPin = new PinAction();

    Button button = new Button("Add Input");

    public NodeActionList(Graph graph) {
        super(graph);
        setName("Action List");
        setColor(0, 255, 0);

        inputPin.setNode(this);
        addCustomInput(inputPin);

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        addUiComponent(button);

        button.addLeftClickListener(new LeftClickListener() {
            @Override
            public void onClicked() {
                addInputPin();
//                Pin pin = new PinAction();
//                pin.setNode(self);
//                pin.setCanDelete(true);
//                addCustomInput(pin);
            }
        });
    }

    public void addInputPin(){
        Pin pin = new PinAction();
        pin.setNode(self);
        pin.setCanDelete(true);
        addCustomInput(pin);
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

                if(connectedPin != null) {
                    PinData<ImString> connectedData = connectedPin.getData();

                    output += "" + connectedData.getValue().get();// + (connectedData.getValue().get().endsWith(";") ? "" : ";");
                    output += "\n";
                }
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

    @Override
    public String getTooltip() {
        return "multiple actions combined into 1, can be connected to a rule or another action list";
    }
}
