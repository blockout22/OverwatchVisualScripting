package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.*;

public class NodeIf extends Node{

    ComboBox ifTypeCombo = new ComboBox();

    PinIf inputPin = new PinIf();
    PinAction truePin = new PinAction();

    PinAction falsePin = new PinAction();

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

        truePin.setNode(this);
        truePin.setName("True");
        addCustomInput(truePin);

        falsePin.setNode(this);
        falsePin.setName("False");
        addCustomInput(falsePin);

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
    public void copy(Node node) {
        if(node instanceof NodeIf){
            ifTypeCombo.selectValue(((NodeIf) node).ifTypeCombo.getSelectedValue());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = inputPin.getData();
        PinData<ImString> trueData = truePin.getData();
        PinData<ImString> falseData = falsePin.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(inputPin, inputData, "");
        handlePinStringConnection(truePin, trueData, "");
        handlePinStringConnection(falsePin, falseData, "");

        String trueOut = "";
        String falseOut = "";
        String[] lines = trueData.getValue().get().split("\n");
        for (int i = 0; i < lines.length; i++) {
            trueOut += "" + lines[i] + "\n";
        }

        if(ifTypeCombo.getSelectedValue().equals("Loop If"))
        {
            truePin.setVisible(false);
            falsePin.setVisible(false);
            outputData.getValue().set(ifTypeCombo.getSelectedValue() + "(" + inputData.getValue().get() + ");");
        }else {
            truePin.setVisible(true);
            falsePin.setVisible(true);
            outputData.getValue().set(ifTypeCombo.getSelectedValue() + "(" + inputData.getValue().get() + ");\n" +
                    trueOut +
                    "Else;\n" +
                    (falseData.getValue().get()) + "\nEnd;");
        }
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
