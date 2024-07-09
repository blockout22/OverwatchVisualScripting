package ovs.graph.node;

import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.*;

public class NodeWait extends Node{

    PinVar pinTime = new PinVar();
    PinCombo pinWaitBehaviour = new PinCombo();
    PinAction outputPin = new PinAction();

    public NodeWait(Graph graph){
        super(graph);
        setName("Wait");

        pinTime.setNode(this);
        pinTime.setName("Time");
        addCustomInput(pinTime);

        pinWaitBehaviour.setNode(this);
        pinWaitBehaviour.setName("Wait Behaviour");
        addCustomInput(pinWaitBehaviour);

        pinWaitBehaviour.addOption("Ignore Condition");
        pinWaitBehaviour.addOption("Abort When False");
        pinWaitBehaviour.addOption("Restart When True");


        outputPin = new PinAction();
        outputPin.setNode(this);
        addCustomOutput(outputPin);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("WaitBehavior:" + pinWaitBehaviour.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("WaitBehavior")){
                pinWaitBehaviour.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeWait){
            pinWaitBehaviour.selectValue(((NodeWait) node).pinWaitBehaviour.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> timeData = pinTime.getData();
        PinData<ImString> waitBehaviourData = pinWaitBehaviour.getData();
        PinData<ImString> outputData = outputPin.getData();

        handlePinStringConnection(pinTime, timeData, "0.25");
        handlePinStringConnection(pinWaitBehaviour, waitBehaviourData, pinWaitBehaviour.getComboBox().getSelectedValue());

        outputData.getValue().set((getFormattedComment()) + "Wait(" + timeData.getValue().get() + ", "+ waitBehaviourData.getValue().get() +");");
    }

    @Override
    public String getOutput() {
        PinData<ImString> data = outputPin.getData();
        return data.getValue().get();
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "Pauses the execution of the action list, unless the wait is interrupted, the remainder of the actions will execute after the pause.";
    }
}
