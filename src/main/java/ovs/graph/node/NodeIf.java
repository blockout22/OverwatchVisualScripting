package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinIf;
import ovs.graph.pin.PinVar;

public class NodeIf extends Node{

    ComboBox ifTypeCombo = new ComboBox();
//    ComboBox conditionBox = new ComboBox();

//    PinVar leftPin = new PinVar();
//    PinVar rightPin = new PinVar();


    PinIf inputPin = new PinIf();
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
//        PinData<ImString> leftData = leftPin.getData();
//        PinData<ImString> rightData = rightPin.getData();
        PinData<ImString> inputData = inputPin.getData();
        PinData<ImString> ifActionData = ifActionPin.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(inputPin, inputData);
        handlePinStringConnection(ifActionPin, ifActionData);


        //TODO Else If isn't setup to work yet

//        if(leftPin.isConnected() && rightPin.isConnected() && ifTypeCombo.getSelectedValue() == "Loop If"){
//            leftData.getValue().set(((ImString)leftPin.getConnectedPin().getData().getValue()).get());
//            rightData.getValue().set(((ImString)rightPin.getConnectedPin().getData().getValue()).get());
//
//            outputData.getValue().set(ifTypeCombo.getSelectedValue() + "(" + leftData.getValue().get() + " " + conditionBox.getSelectedValue() + " " + rightData.getValue().get() + ");");
//            return;
//        }

//        if(leftPin.isConnected() && rightPin.isConnected() && ifActionPin.isConnected()){
//            if(ifActionPin.isConnected()){
//                Pin connectedPin = ifActionPin.getConnectedPin();
//
//                PinData<ImString> connectedData = connectedPin.getData();
//            }
//
//            handlePinStringConnection(leftPin, leftData);
//            handlePinStringConnection(rightPin, rightData);
//            handlePinStringConnection(ifActionPin, ifActionData);
////            System.out.println(ifActionData.getValue().get());
////            dataLeft.getValue().set(((ImString)leftPin.getConnectedPin().getData().getValue()).get());
////            dataRight.getValue().set(((ImString)rightPin.getConnectedPin().getData().getValue()).get());
////            ruleData.getValue().set(((ImString)ifActionPin.getConnectedPin().getData().getValue()).get());
//
        String out = "";
        String[] lines = ifActionData.getValue().get().split("\n");
        for (int i = 0; i < lines.length; i++) {
            out += "" + lines[i] + "\n";
        }
        outputData.getValue().set(ifTypeCombo.getSelectedValue() + "(" + inputData.getValue().get() + ");\n" +
                out +
                "End;");
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
