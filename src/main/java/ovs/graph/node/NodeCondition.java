package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinCondition;
import ovs.graph.pin.PinVar;

public class NodeCondition extends Node {

    ComboBox conditionBox = new ComboBox();

    PinVar pinValue1 = new PinVar();
    PinVar pinValue2 = new PinVar();

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
        addUiComponent(conditionBox);

        pinValue1.setNode(this);
        pinValue1.setName("Value");
        addCustomInput(pinValue1);

        pinValue2.setNode(this);
        pinValue2.setName("Value");
        addCustomInput(pinValue2);

        output.setNode(this);
        addCustomOutput(output);
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
    public void copy(Node node) {
        if(node instanceof NodeCondition){
            conditionBox.selectValue(((NodeCondition) node).conditionBox.getSelectedValue());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> value1Data = pinValue1.getData();
        PinData<ImString> value2Data = pinValue2.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinValue1, value1Data);
        handlePinStringConnection(pinValue2, value2Data);

        outputData.getValue().set("(" + value1Data.getValue().get() + ") " + conditionBox.getSelectedValue() + " (" + value2Data.getValue().get() + ");");
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
        return "used to compare numbers";
    }
}