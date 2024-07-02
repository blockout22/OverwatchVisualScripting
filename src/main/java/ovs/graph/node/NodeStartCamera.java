package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeStartCamera extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinEyePos = new PinVar();
    PinVar pinLookAt = new PinVar();
    PinVar pinBlendSpeed = new PinVar();

    PinAction output = new PinAction();

    public NodeStartCamera(Graph graph) {
        super(graph);
        setName("Start Camera");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinEyePos.setNode(this);
        pinEyePos.setName("Eye Position");
        addCustomInput(pinEyePos);

        pinLookAt.setNode(this);
        pinLookAt.setName("Look At Position");
        addCustomInput(pinLookAt);

        pinBlendSpeed.setNode(this);
        pinBlendSpeed.setName("Blend Speed");
        addCustomInput(pinBlendSpeed);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinLookAt.getData();
        PinData<ImString> eyePosData = pinEyePos.getData();
        PinData<ImString> lookAtData = pinLookAt.getData();
        PinData<ImString> blendSpeedData = pinBlendSpeed.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinEyePos, eyePosData);
        handlePinStringConnection(pinLookAt, lookAtData);
        handlePinStringConnection(pinBlendSpeed, blendSpeedData, "1");

        outputData.getValue().set("Start Camera(" + playerData.getValue().get() + ", " + eyePosData.getValue().get() + ", " + lookAtData.getValue().get() + ", " + blendSpeedData.getValue().get() + ");");

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
        return "Places your camera at a location, facing a direction.";
    }
}
