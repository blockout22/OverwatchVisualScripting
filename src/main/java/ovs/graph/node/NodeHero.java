package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
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

        for (int i = 0; i < Global.heroes.size(); i++) {
            heroes.addOption(Global.heroes.get(i));
        }

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
