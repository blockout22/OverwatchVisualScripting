package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeCreateEffect extends Node{

    PinVar pinPlayer = new PinVar();
    PinCombo pinType = new PinCombo();
    PinVar pinColor = new PinVar();
    PinVar pinPosition = new PinVar();
    PinVar pinRadius = new PinVar();
    PinVar pinReevaluation = new PinVar();

    PinAction output = new PinAction();

    public NodeCreateEffect(Graph graph) {
        super(graph);
        setName("Create Effect");
        setColor(255, 0, 0);

        pinPlayer.setNode(this);
        pinPlayer.setName("Visible To");
        addCustomInput(pinPlayer);

        pinType.setNode(this);
        pinType.setName("Type");
        addCustomInput(pinType);

        populateTypeOptions();

        pinColor.setNode(this);
        pinColor.setName("Color");
        addCustomInput(pinColor);

        pinPosition.setNode(this);
        pinPosition.setName("Position");
        addCustomInput(pinPosition);

        pinRadius.setNode(this);
        pinRadius.setName("Radius");
        addCustomInput(pinRadius);

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> typeData = pinType.getData();
        PinData<ImString> colorData = pinColor.getData();
        PinData<ImString> positionData = pinPosition.getData();
        PinData<ImString> radiusData = pinRadius.getData();
        PinData<ImString> reevaluationData = pinReevaluation.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinType, typeData);
        handlePinStringConnection(pinColor, colorData, "Color(White)");
        handlePinStringConnection(pinPosition, positionData, "Vector(0, 0, 0)");
        handlePinStringConnection(pinRadius, radiusData, "5");
        handlePinStringConnection(pinReevaluation, reevaluationData, "Visible To Position and Radius");

        outputData.getValue().set("Create Effect (" + playerData.getValue().get() + ", " + typeData.getValue().get() + ", " + colorData.getValue().get() + ", " + positionData.getValue().get() + ", " + radiusData.getValue().get() + ", " + reevaluationData.getValue().get() + ");");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    private void populateTypeOptions(){
        pinType.addOption("Bad Aura");
        pinType.addOption("Bad Aura Sound");
        pinType.addOption("Beacon Sound");
        pinType.addOption("Cloud");
        pinType.addOption("Decal Sound");
        pinType.addOption("Energy Sound");
        pinType.addOption("Good Aura");
        pinType.addOption("Good Aura Sound");
        pinType.addOption("Light Shaft");
        pinType.addOption("Orb");
        pinType.addOption("Pick-up Sound");
        pinType.addOption("Ring");
        pinType.addOption("Smoke Sound");
        pinType.addOption("Sparkles");
        pinType.addOption("Sparkles Sound");
        pinType.addOption("Sphere");
        pinType.addOption("Echo Focusing Beam Sound");
        pinType.addOption("Junkrat Trap Chain Sound");
        pinType.addOption("Mercy Heal Beam Sound");
        pinType.addOption("Mercy Boost Beam Sound");
        pinType.addOption("Moira Grasp Connected Sound");
        pinType.addOption("Moira Orb Damage Sound");
        pinType.addOption("Moira Orb Heal Sound");
        pinType.addOption("Moira Coalescence Sound");
        pinType.addOption("Orisa Amplifier Sound");
        pinType.addOption("Orisa Halt Tendril Sound");
        pinType.addOption("Symmetra Projector Sound");
        pinType.addOption("Winston Tesla Cannon Sound");
        pinType.addOption("Zarya Particle Beam Sound");
        pinType.addOption("Omnic Slicer Beam Sound");
        pinType.addOption("Ana Nano Boosted Sound");
        pinType.addOption("Baptiste Immortality Field Projected Sound");
        pinType.addOption("Echo Cloning Sound");
        pinType.addOption("Lúcio Sound Barrier Protected Sound");
        pinType.addOption("Mei Frozen Sound");
        pinType.addOption("Mercy Damage Boosted Sound");
        pinType.addOption("Sigma Grvitic Flux Target Sound");
        pinType.addOption("Sombra Hacking Sound");
        pinType.addOption("Sombra Hacked Sound");
        pinType.addOption("Torbjörn Overloading Sound");
        pinType.addOption("Widowmaker Venom Mine Target Sound");
        pinType.addOption("Winston Tesla Cannon Target Sound");
        pinType.addOption("Winstron Primal Rage Sound");
        pinType.addOption("Wrecking Ball Adaptive Sheild Target Sound");
        pinType.addOption("Wrecking Ball Piledriver Fire Sound");
        pinType.addOption("Zenyatta Orb Of Discord Target Sound");
        pinType.addOption("Heal Target Active Effect");
        pinType.addOption("Heal Target Effect");
        pinType.addOption("Ana Biotic Grenade Increased Healing Effect");
        pinType.addOption("Ana Nano Boosted Effect");
        pinType.addOption("Baptiste Immortality Field Protected Effect");
        pinType.addOption("Echo Cloning Effect");
        pinType.addOption("Lúcio Sound Barrier Protected Effect");
        pinType.addOption("Mercy Damage Boosted Effect");
        pinType.addOption("Reaper Wraith Form Effect");
        pinType.addOption("Soldier: 76 Sprinting Effect");
        pinType.addOption("Torbjörn Overloading Effect");
        pinType.addOption("Winston Primal Rage Effect");
        pinType.addOption("Wrecking Ball Adaptive Shield Target Effect");
        pinType.addOption("Wrecking Ball Piledriver Fire Effect");
        pinType.addOption("Ana Biotic Grenade No Healing Effect");
        pinType.addOption("Ana Sleeping Effect");
        pinType.addOption("Ashe Dynamite Burning Particle Effect");
        pinType.addOption("Ashe Dynamite Burning Material Effect");
        pinType.addOption("Cassidy Flashbang Stunned Effect");
        pinType.addOption("Mei Frozen Effect");
        pinType.addOption("Sigma Gravitic Flux Target Effect");
        pinType.addOption("Sombra Hacked Looping Effect");
        pinType.addOption("Widowmaker Venom Mine Target Effect");
        pinType.addOption("Winston Tesla Cannon Target Effect");
        pinType.addOption("Zenyatta Orb Of Discord Target Effect");

        pinType.sort();
    }

    @Override
    public void UI() {

    }
}
