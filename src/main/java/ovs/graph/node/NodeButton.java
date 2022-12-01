package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodeButton extends Node{

    ComboBox buttons = new ComboBox();
    PinVar output = new PinVar();

    public NodeButton(Graph graph) {
        super(graph);
        setName("Button");

        buttons.addOption("Ability 1");
        buttons.addOption("Ability 2");
        buttons.addOption("Crouch");
        buttons.addOption("Interact");
        buttons.addOption("Jump");
        buttons.addOption("Melee");
        buttons.addOption("Reload");
        buttons.addOption("Secondary Fire");
        buttons.addOption("Ultimate");

        buttons.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Button:" + buttons.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Button")){
                buttons.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Button(" + buttons.getSelectedValue() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        buttons.show();
    }
}
