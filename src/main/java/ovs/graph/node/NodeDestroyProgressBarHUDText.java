package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDestroyProgressBarHUDText extends Node{

    PinVar pinEntity = new PinVar();
    PinAction output = new PinAction();

    public NodeDestroyProgressBarHUDText(Graph graph) {
        super(graph);
        setName("Destroy Progress Bar Hud Text");

        pinEntity.setNode(this);
        pinEntity.setName("Text ID");
        addCustomInput(pinEntity);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> entityData = pinEntity.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinEntity, entityData);

        outputData.getValue().set("Destroy Progress Bar HUD Text(" + entityData.getValue().get() + ");");

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
        return "Destroys progress bar hud text that was created by create progress bar hud text.";
    }
}
