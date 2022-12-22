package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeApplyImpulse extends Node{

    ComboBox relative = new ComboBox("To World", "To Player");
    ComboBox motion = new ComboBox("Cancel Contrary Motion", "Cancel Contrary Motion XYZ", "Incorporate Contrary Motion");

    PinVar pinPlayer = new PinVar();
    PinVar pinDirection = new PinVar();
    PinVar pinSpeed = new PinVar();

    PinAction output = new PinAction();

    public NodeApplyImpulse(Graph graph) {
        super(graph);
        setName("Apply Impulse");

        relative.select(0);
        motion.select(0);

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinDirection.setNode(this);
        pinDirection.setName("Direction");
        addCustomInput(pinDirection);

        pinSpeed.setNode(this);
        pinSpeed.setName("Speed");
        addCustomInput(pinSpeed);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Relative:" + relative.getSelectedValue());
        getExtraSaveData().add("Motion:" + motion.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Relative")){
                relative.selectValue(data.split(":")[1]);
            }else if(data.startsWith("Motion")){
                motion.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> directionData = pinDirection.getData();
        PinData<ImString> speedData = pinSpeed.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinDirection, directionData);
        handlePinStringConnection(pinSpeed, speedData, "0");

        outputData.getValue().set("Apply Impulse(" + playerData.getValue().get() + ", " + directionData.getValue().get() + ", " + speedData.getValue().get() + ", " + relative.getSelectedValue() + ", " + motion.getSelectedValue() + ");");

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
