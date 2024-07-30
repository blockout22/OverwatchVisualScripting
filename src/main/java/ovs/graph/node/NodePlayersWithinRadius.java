package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodePlayersWithinRadius extends Node {

//    ComboBox losCheck = new ComboBox(Global.LOSCheck);

    PinVar pinCenter = new PinVar();
    PinVar pinRadius = new PinVar();
    PinVar pinTeam = new PinVar();
    PinCombo losCheck = new PinCombo();
    PinVar output = new PinVar();

    public NodePlayersWithinRadius(Graph graph) {
        super(graph);
        setName("Players Within Radius");

        pinCenter.setNode(this);
        pinCenter.setName("Center");
        addCustomInput(pinCenter);

        pinRadius.setNode(this);
        pinRadius.setName("Radius");
        addCustomInput(pinRadius);

        pinTeam.setNode(this);
        pinTeam.setName("Team");
        addCustomInput(pinTeam);

        losCheck.setNode(this);
        losCheck.setName("LOS Check");
        addCustomInput(losCheck);

        losCheck.getComboBox().addOptions(Global.LOSCheck);

        losCheck.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("LOS:" + losCheck.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("LOS")){
                try{
                    losCheck.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    losCheck.select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> centerData = pinCenter.getData();
        PinData<ImString> radiusData = pinRadius.getData();
        PinData<ImString> teamData = pinTeam.getData();
        PinData<ImString> losCheckData = losCheck.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinCenter, centerData);
        handlePinStringConnection(pinRadius, radiusData);
        handlePinStringConnection(pinTeam, teamData);
        handlePinStringConnection(losCheck, losCheckData, losCheck.getComboBox().getSelectedValue());

        outputData.getValue().set(getName() + "(" + centerData.getValue().get() + ", " + radiusData.getValue().get() + ", " + teamData.getValue().get() + ", " + losCheckData.getValue().get() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI(){
    }

    @Override
    public String getTooltip() {
        return "An array containing all players within a certain distance of a position, optionally restricted by team and line of sight.";
    }
}