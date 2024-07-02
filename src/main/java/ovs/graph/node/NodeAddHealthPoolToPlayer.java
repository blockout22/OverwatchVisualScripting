package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeAddHealthPoolToPlayer extends Node{

    ComboBox healthType = new ComboBox(Global.healthType);

    PinVar pinPlayer = new PinVar();
    PinVar pinMaxHealth = new PinVar();
    PinVar pinRecoverable = new PinVar();
    PinVar pinReevaluation = new PinVar();

    PinAction output = new PinAction();

    public NodeAddHealthPoolToPlayer(Graph graph) {
        super(graph);
        setName("Node Add Health Pool To Player");

        healthType.select(0);

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinMaxHealth.setNode(this);
        pinMaxHealth.setName("Max Health");
        addCustomInput(pinMaxHealth);

        pinRecoverable.setNode(this);
        pinRecoverable.setName("Recoverable");
        addCustomInput(pinRecoverable);

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> maxHealthData = pinMaxHealth.getData();
        PinData<ImString> recoverableData = pinRecoverable.getData();
        PinData<ImString> reevaluationData = pinReevaluation.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinMaxHealth, maxHealthData, "100");
        handlePinStringConnection(pinRecoverable, recoverableData, "True");
        handlePinStringConnection(pinReevaluation, reevaluationData, "True");

        outputData.getValue().set("Add Health Pool To Player(" + playerData.getValue().get() + ", " + healthType.getSelectedValue() + ", " + maxHealthData.getValue().get() + ", " + recoverableData.getValue().get() + ", " + reevaluationData.getValue().get() + ");");
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Type:" + healthType.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Type")){
                healthType.selectValue(data.split(":")[1]);
            }
        }
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
        return "Adds a temporary health pool to a player or players. the health pool can be referenced using the last created health pool value, up to 16 health pools of a given health type (Health, armor, or shields) may exist on a player (including base pools and pools granted by abilities).";
    }
}
