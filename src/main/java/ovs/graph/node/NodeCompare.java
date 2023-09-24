package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinIf;
import ovs.graph.pin.PinVar;

public class NodeCompare extends Node{

    ComboBox conditionBox = new ComboBox();

    PinVar leftPin = new PinVar();
    PinVar rightPin = new PinVar();

    PinIf output = new PinIf();

    public NodeCompare(Graph graph) {
        super(graph);
        setName("Compare");

        conditionBox.addOption("<");
        conditionBox.addOption(">");
        conditionBox.addOption("<=");
        conditionBox.addOption(">=");
        conditionBox.addOption("!=");
        conditionBox.addOption("==");

        conditionBox.select(5);
        addUiComponent(conditionBox);

        leftPin.setNode(this);
        leftPin.setName("Left Condition");
        addCustomInput(leftPin);

        rightPin.setNode(this);
        rightPin.setName("Right Condition");
        addCustomInput(rightPin);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Compare:" + conditionBox.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Compare"))
            {
                conditionBox.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeCompare){
            conditionBox.selectValue(((NodeCompare) node).conditionBox.getSelectedValue());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> leftData = leftPin.getData();
        PinData<ImString> rightData = rightPin.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(leftPin, leftData);
        handlePinStringConnection(rightPin, rightData, "True");

        outputData.getValue().set(leftData.getValue().get() + " " + conditionBox.getSelectedValue() + " " + rightData.getValue().get());
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
