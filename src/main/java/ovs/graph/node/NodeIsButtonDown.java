package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinVar;

public class NodeIsButtonDown extends Node{

    ComboBox comboBox = new ComboBox();

    PinVar input = new PinVar();
    PinVar output = new PinVar();

    public NodeIsButtonDown(Graph graph) {
        super(graph);
        setName("Is Button Down");

        for (int i = 0; i < Global.buttons.size(); i++) {
            comboBox.addOption(Global.buttons.get(i));
        }

        input.setNode(this);
        input.setName("Player");
        addCustomInput(input);

        output.setNode(this);
        addCustomOutput(output);

        comboBox.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Button:" + comboBox.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Button:")){
                comboBox.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = input.getData();
        PinData<ImString> outputData = output.getData();

        if(input.isConnected()){
            Pin connectedPin = input.getConnectedPin();

            PinData<ImString> conenctedData = connectedPin.getData();

            inputData.getValue().set(conenctedData.getValue().get());
        }


        outputData.getValue().set("Is Button Held(" + inputData.getValue().get() + ", Button(" + comboBox.getSelectedValue() +"))");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
