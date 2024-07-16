package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeHero extends Node{

    PinCombo pinHero = new PinCombo();

    PinVar output = new PinVar();

    public NodeHero(Graph graph) {
        super(graph);
        setName("Hero");

        pinHero.setNode(this);
        pinHero.setName("Hero");
        addCustomInput(pinHero);

        output.setNode(this);
        addCustomOutput(output);

        pinHero.getComboBox().addOptions(Global.heroes);

        pinHero.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Hero:" + pinHero.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Hero")){
                try {
                    pinHero.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinHero.select(0);
                }
            }
        }
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeHero){
            pinHero.selectValue(((NodeHero) node).pinHero.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> heroData = pinHero.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinHero, heroData, pinHero.getComboBox().getSelectedValue());

        outputData.getValue().set("Hero(" + heroData.getValue().get() + ")");
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
        return "A hero constant.";
    }
}
