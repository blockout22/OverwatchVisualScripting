package ovs.graph.node;

import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinFloat;

public class NodeWait extends Node{

    ComboBox waitBehavior = new ComboBox();

    PinFloat inputPin;
    PinAction outputPin;

    PinData<ImFloat> data;

    public NodeWait(Graph graph){
        super(graph);
        setName("Wait");

        waitBehavior.addOption("Ignore Condition");
        waitBehavior.addOption("Abort When False");
        waitBehavior.addOption("Restart When True");

        waitBehavior.select(0);

        inputPin = new PinFloat();
        inputPin.setNode(this);
        addCustomInput(inputPin);

        data = inputPin.getData();

        outputPin = new PinAction();
        outputPin.setNode(this);
        addCustomOutput(outputPin);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("WaitBehavior:" + waitBehavior.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("WaitBehavior")){
                waitBehavior.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeWait){
            waitBehavior.selectValue(((NodeWait) node).waitBehavior.getSelectedValue());

            PinData<ImFloat> copyData = ((NodeWait) node).inputPin.getData();

            data.getValue().set(copyData.value.get());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = inputPin.getData();
        PinData<ImString> outputData = outputPin.getData();

        if(inputPin.isConnected()){
            Pin connectedPin = inputPin.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();

            inputData.getValue().set(connectedData.getValue().get());
        }

        outputData.getValue().set("Wait(" + data.getValue() + ", "+ waitBehavior.getSelectedValue() +");");
    }

    @Override
    public String getOutput() {
        PinData<ImString> data = outputPin.getData();
        return data.getValue().get();
    }

    @Override
    public void UI() {
        waitBehavior.show();
    }
}
