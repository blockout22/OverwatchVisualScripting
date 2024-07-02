package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeCreateDummyBot extends Node{

    PinVar pinHero = new PinVar();
    PinVar pinTeam = new PinVar();
    PinVar pinSlot = new PinVar();
    PinVar pinPosition = new PinVar();
    PinVar pinFacing = new PinVar();

    PinAction output = new PinAction();

    public NodeCreateDummyBot(Graph graph) {
        super(graph);
        setName("Create Dummy Bot");
        setColor(255, 0, 0);

        pinHero.setNode(this);
        pinHero.setName("Hero");
        addCustomInput(pinHero);

        pinTeam.setNode(this);
        pinTeam.setName("Team");
        addCustomInput(pinTeam);

        pinSlot.setNode(this);
        pinSlot.setName("Slot");
        addCustomInput(pinSlot);

        pinPosition.setNode(this);
        pinPosition.setName("Position");
        addCustomInput(pinPosition);

        pinFacing.setNode(this);
        pinFacing.setName("Facing");
        addCustomInput(pinFacing);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> heroData = pinHero.getData();
        PinData<ImString> teamData = pinTeam.getData();
        PinData<ImString> slotData = pinSlot.getData();
        PinData<ImString> positionData = pinPosition.getData();
        PinData<ImString> facingData = pinFacing.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinHero, heroData, "Hero(Ana)");
        handlePinStringConnection(pinTeam, teamData, "All Teams");
        handlePinStringConnection(pinSlot, slotData, "0");
        handlePinStringConnection(pinPosition, positionData, "Vector(0, 0, 0)");
        handlePinStringConnection(pinFacing, facingData, "Vector(0, 0, 0)");

        outputData.getValue().set("Create Dummy Bot(" + heroData.getValue().get() + ", " + teamData.getValue().get() + ", " + slotData.getValue().get() + ", " + positionData.getValue().get() + ", " + facingData.getValue().get() + ");");
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
        return "Adds a new bot to the specified slot on the specified team so long as the slot is available. this bos will only move, fire, or use abilities if executing workshop actions. extra dummy bots may be created if corresponding workshop extension is enabled.";
    }
}
