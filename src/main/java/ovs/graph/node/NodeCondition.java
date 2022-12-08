package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinCondition;
import ovs.graph.pin.PinVar;

public class NodeCondition extends Node{

    ComboBox conditionBox = new ComboBox();

    PinVar pinLeft = new PinVar();
    PinVar pinRight = new PinVar();

    PinCondition output = new PinCondition();

    public NodeCondition(Graph graph) {
        super(graph);
        setName("Condition");

        conditionBox.addOption("<");
        conditionBox.addOption(">");
        conditionBox.addOption("<=");
        conditionBox.addOption(">=");
        conditionBox.addOption("!=");
        conditionBox.addOption("==");

        conditionBox.select(5);

        pinLeft.setNode(this);
        pinLeft.setName("Left Condition");
        addCustomInput(pinLeft);

        pinRight.setNode(this);
        pinRight.setName("Right Condition");
        addCustomInput(pinRight);

        output.setNode(this);
        addCustomOutput(output);

        addUiComponent(conditionBox);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Condition:" + conditionBox.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Condition"))
            {
                conditionBox.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> leftData = pinLeft.getData();
        PinData<ImString> rightData = pinRight.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinLeft, leftData);
        handlePinStringConnection(pinRight, rightData);

        outputData.getValue().set(leftData.getValue().get() + " " + conditionBox.getSelectedValue() + " " + rightData.getValue().get() + ";");

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
