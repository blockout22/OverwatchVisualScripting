package ovs.graph.node;

import imgui.ImGui;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.*;

public class NodeIf extends Node{

    ComboBox ifTypeCombo = new ComboBox();

    PinIf inputPin = new PinIf();
    PinAction ifActionPin = new PinAction();

    PinElse elsePin = new PinElse();

    PinAction output = new PinAction();

    public NodeIf(Graph graph) {
        super(graph);

        setName("If");

        ifTypeCombo.addOption("If");
        ifTypeCombo.addOption("Else If");
        ifTypeCombo.addOption("Skip If");
        ifTypeCombo.addOption("Loop If");

        ifTypeCombo.select(0);

//        conditionBox.addOption("<");
//        conditionBox.addOption(">");
//        conditionBox.addOption("<=");
//        conditionBox.addOption(">=");
//        conditionBox.addOption("!=");
//        conditionBox.addOption("==");
//
//        conditionBox.select(5);

//        leftPin.setNode(this);
//        leftPin.setName("Left Condition");
//        addCustomInput(leftPin);
//
//        rightPin.setNode(this);
//        rightPin.setName("Right Condition");
//        addCustomInput(rightPin);

        inputPin.setNode(this);
        inputPin.setName("If Condition");
        addCustomInput(inputPin);

        ifActionPin.setNode(this);
        addCustomInput(ifActionPin);

        elsePin.setNode(this);
        elsePin.setName("Else");
        addCustomInput(elsePin);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Type:" + ifTypeCombo.getSelectedValue());
//        getExtraSaveData().add("Condition:" + conditionBox.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Type")){
                ifTypeCombo.selectValue(data.split(":")[1]);
            }
//            if(data.startsWith("Condition"))
//            {
//                conditionBox.selectValue(data.split(":")[1]);
//            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = inputPin.getData();
        PinData<ImString> ifActionData = ifActionPin.getData();
        PinData<ImString> elseData = elsePin.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(inputPin, inputData);
        handlePinStringConnection(elsePin, elseData);
        handlePinStringConnection(ifActionPin, ifActionData);

        String out = "";
        String[] lines = ifActionData.getValue().get().split("\n");
        for (int i = 0; i < lines.length; i++) {
            out += "" + lines[i] + "\n";
        }


        outputData.getValue().set(ifTypeCombo.getSelectedValue() + "(" + inputData.getValue().get() + ");\n" +
                out +
                (elsePin.isConnected() ? elseData.getValue().get() : "") + "\nEnd;");
//
//            outputData.getValue().set(ifTypeCombo.getSelectedValue() + "(" + leftData.getValue().get() + " " + conditionBox.getSelectedValue() + " " + rightData.getValue().get() + ");\n" +
//                    out +
//                    "End;");
//        }else{
//            //TODO clear pin data if not connected
//        }
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        ifTypeCombo.show();
//        conditionBox.show();
    }
}
