package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeDestroyEffect extends Node{

    PinVar pinEntity = new PinVar();
    PinAction output = new PinAction();

    public NodeDestroyEffect(Graph graph) {
        super(graph);
        setName("Destroy Effect");

        pinEntity.setNode(this);
        pinEntity.setName("Entity");
        addCustomInput(pinEntity);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> entityData = pinEntity.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinEntity, entityData);

        outputData.getValue().set("Destroy Effect(" + entityData.getValue().get() + ");");

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
        return "Destroys an effect entity that was created by create effect or create beam effect.";
    }
}
