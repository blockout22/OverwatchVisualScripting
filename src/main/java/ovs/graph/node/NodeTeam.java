package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodeTeam extends Node{

    ComboBox team = new ComboBox();

    PinVar output = new PinVar();

    public NodeTeam(Graph graph) {
        super(graph);
        setName("Team");

        team.addOption("All Teams");
        team.addOption("Team 1");
        team.addOption("Team 2");
        team.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Team:" + team.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Team")){
                team.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set(team.getSelectedValue());

    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        team.show();
    }

    @Override
    public String getTooltip() {
        return "A team constant, the all option represents both teams in a team game or all players in free-for-all game.";
    }
}
