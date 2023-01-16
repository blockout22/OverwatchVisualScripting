package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodePlayerHeroStat extends Node {

    ComboBox stats = new ComboBox(Global.stats);

    PinVar pinPlayer = new PinVar();
    PinVar pinHero = new PinVar();
    PinVar output = new PinVar();

    public NodePlayerHeroStat(Graph graph) {
        super(graph);
        setName("Player Hero Stat");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinHero.setNode(this);
        pinHero.setName("Hero");
        addCustomInput(pinHero);

        stats.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Stat:" + stats.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Stat")){
                try{
                    stats.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    stats.select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> heroData = pinHero.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(pinHero, heroData);

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + heroData.getValue().get() + ", " + stats.getSelectedValue() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        stats.show();
    }
}