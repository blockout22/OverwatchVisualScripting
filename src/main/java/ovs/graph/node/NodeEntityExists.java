package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeEntityExists extends Node {

    PinVar pinEntity = new PinVar();
    PinVar output = new PinVar();

    public NodeEntityExists(Graph graph) {
        super(graph);
        setName("Entity Exists");

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

        outputData.getValue().set("Entity Exists(" + entityData.getValue().get() + ")");
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
        return "Whether the specified player, icon entity, or effect entity still exists. useful for determining if a player has left the match or an entity has been destroyed.";
    }
}