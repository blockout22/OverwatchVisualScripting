package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeIf extends Node{

    ComboBox ifTypeCombo = new ComboBox();
    ComboBox conditionBox = new ComboBox();

    PinVar leftPin = new PinVar();
    PinVar rightPin = new PinVar();

    PinAction ifActionPin = new PinAction();

    PinAction output = new PinAction();

    public NodeIf(Graph graph) {
        super(graph);

        setName("If");

        ifTypeCombo.addOption("If");
        ifTypeCombo.addOption("Else If");
        ifTypeCombo.addOption("Skip If");
        ifTypeCombo.addOption("Loop If");

        ifTypeCombo.select(0);

        conditionBox.addOption("<");
        conditionBox.addOption(">");
        conditionBox.addOption("<=");
        conditionBox.addOption(">=");
        conditionBox.addOption("!=");
        conditionBox.addOption("==");

        conditionBox.select(5);

        leftPin.setNode(this);
        leftPin.setName("Left Condition");
        addCustomInput(leftPin);

        rightPin.setNode(this);
        rightPin.setName("Right Condition");
        addCustomInput(rightPin);

        ifActionPin.setNode(this);
        addCustomInput(ifActionPin);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Type:" + ifTypeCombo.getSelectedValue());
        getExtraSaveData().add("Condition:" + conditionBox.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Type")){
                ifTypeCombo.selectValue(data.split(":")[1]);
            }
            if(data.startsWith("Condition"))
            {
                conditionBox.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> dataLeft = leftPin.getData();
        PinData<ImString> dataRight = rightPin.getData();
        PinData<ImString> outputData = output.getData();

        //TODO Else If isn't setup to work yet

        //when Loop If is selected the If statement should do at the end
        if(leftPin.isConnected() && rightPin.isConnected() && ifTypeCombo.getSelectedValue() == "Loop If"){
            dataLeft.getValue().set(((ImString)leftPin.getConnectedPin().getData().getValue()).get());
            dataRight.getValue().set(((ImString)rightPin.getConnectedPin().getData().getValue()).get());

            outputData.getValue().set(ifTypeCombo.getSelectedValue() + "(" + dataLeft.getValue().get() + " " + conditionBox.getSelectedValue() + " " + dataRight.getValue().get() + ");");
            return;
        }

        if(leftPin.isConnected() && rightPin.isConnected() && ifActionPin.isConnected()){
            PinData<ImString> ruleData = ifActionPin.getData();

            dataLeft.getValue().set(((ImString)leftPin.getConnectedPin().getData().getValue()).get());
            dataRight.getValue().set(((ImString)rightPin.getConnectedPin().getData().getValue()).get());
            ruleData.getValue().set(((ImString)ifActionPin.getConnectedPin().getData().getValue()).get());

            String out = "";
            String[] lines = ruleData.getValue().get().split("\n");
            for (int i = 0; i < lines.length; i++) {
                out += "\t\t\t" + lines[i] + "\n";
            }

            outputData.getValue().set(ifTypeCombo.getSelectedValue() + "(" + dataLeft.getValue().get() + " " + conditionBox.getSelectedValue() + " " + dataRight.getValue().get() + ");\n" +
                    out +
                    "\t\tEnd;");
        }else{
            //TODO clear pin data if not connected
        }
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        ifTypeCombo.show();
        conditionBox.show();
    }
}
