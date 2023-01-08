package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeHasSpawned extends Node {

    PinVar pinEntity = new PinVar();
    PinVar output = new PinVar();

    public NodeHasSpawned(Graph graph) {
        super(graph);
        setName("Has Spawned");

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

        outputData.getValue().set("Has Spawned(" + entityData.getValue().get() + ")");
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