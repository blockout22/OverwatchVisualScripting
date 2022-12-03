package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodeHero extends Node{

    ComboBox heroes = new ComboBox();

    PinVar output = new PinVar();

    public NodeHero(Graph graph) {
        super(graph);
        setName("Hero");

        output.setNode(this);
        addCustomOutput(output);

        heroes.addOption("Ana");
        heroes.addOption("Ashe");
        heroes.addOption("Baptiste");
        heroes.addOption("Bastion");
        heroes.addOption("Brigitte");
        heroes.addOption("Doomfist");
        heroes.addOption("D.Va");
        heroes.addOption("Echo");
        heroes.addOption("Genji");
        heroes.addOption("Wrecking Ball");
        heroes.addOption("Hanzo");
        heroes.addOption("Junker Queen");
        heroes.addOption("Junkrat");
        heroes.addOption("Kiriko");
        heroes.addOption("Lúcio");
        heroes.addOption("Cassidy");
        heroes.addOption("Mei");
        heroes.addOption("Mercy");
        heroes.addOption("Moira");
        heroes.addOption("Orisa");
        heroes.addOption("Pharah");
        heroes.addOption("Reaper");
        heroes.addOption("Reinhardt");
        heroes.addOption("Roadhog");
        heroes.addOption("Sigma");
        heroes.addOption("Sojourn");
        heroes.addOption("Soldier: 76");
        heroes.addOption("Sombra");
        heroes.addOption("Symmetra");
        heroes.addOption("Torbjörn");
        heroes.addOption("Tracer");
        heroes.addOption("Windowmaker");
        heroes.addOption("Winston");
        heroes.addOption("Zarya");
        heroes.addOption("Zenyatta");

        heroes.select(9);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Hero:" + heroes.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Hero")){
                heroes.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Hero(" + heroes.getSelectedValue() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        heroes.show();
    }
}
