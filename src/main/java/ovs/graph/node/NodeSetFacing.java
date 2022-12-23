package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetFacing extends Node {

    ComboBox relative = new ComboBox("To World", "To Player");

    PinVar pinPlayer = new PinVar();
    PinVar pinDir = new PinVar();

    PinAction output = new PinAction();

    public NodeSetFacing(Graph graph) {
        super(graph);
        setName("Set Facing");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinDir.setNode(this);
        pinDir.setName("Direction");
        addCustomInput(pinDir);

        relative.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> dirData = pinDir.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinDir, dirData, "Vector(0, 0, 0)");

        outputData.getValue().set("Set Facing(" + playerData.getValue().get() + ", " + dirData.getValue().get() + ", " + relative.getSelectedValue() + ");");
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