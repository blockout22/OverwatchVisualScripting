package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodeAllPlayers extends Node{

    ComboBox comboBoxTeam = new ComboBox();

    PinVar output = new PinVar();

    public NodeAllPlayers(Graph graph) {
        super(graph);
        setName("All Players");


        comboBoxTeam.addOption("All Teams");
        comboBoxTeam.addOption("Team 1");
        comboBoxTeam.addOption("Team 2");

        comboBoxTeam.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Team:" + comboBoxTeam.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Team"))
            {
                comboBoxTeam.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("All Players(" + comboBoxTeam.getSelectedValue() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        comboBoxTeam.show();
    }
}
