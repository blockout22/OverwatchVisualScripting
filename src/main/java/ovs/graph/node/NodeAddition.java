package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodeAddition extends Node{

    ComboBox addition = new ComboBox();

    PinVar input1 = new PinVar();
    PinVar input2 = new PinVar();

    PinVar output = new PinVar();

    public NodeAddition(Graph graph) {
        super(graph);
        setName("Addition");

        addition.addOption("+");
        addition.addOption("-");
        addition.addOption("*");
        addition.addOption("/");

        addition.select(0);

        input1.setNode(this);
        addCustomInput(input1);

        input2.setNode(this);
        addCustomInput(input2);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Addition:" + addition.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for (String data : getExtraSaveData()){
            if(data.startsWith("Addition")){
                addition.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> input1Data = input1.getData();
        PinData<ImString> input2Data = input2.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(input1, input1Data, "0");
        handlePinStringConnection(input2, input2Data, "0");

        outputData.getValue().set(input1Data.getValue().get() + " " + addition.getSelectedValue() + " " + input2Data.getValue().get());
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        addition.show();
    }

    @Override
    public String getTooltip() {
        return "Add, subtract, multiply and divide combined into 1 node";
    }
}
