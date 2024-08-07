package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeButton extends Node{

    PinCombo buttons = new PinCombo();
    PinVar output = new PinVar();

    public NodeButton(Graph graph) {
        super(graph);
        setName("Button");

        buttons.setNode(this);
        buttons.setName("Button");
        addCustomInput(buttons);

        for (int i = 0; i < Global.buttons.size(); i++) {
            buttons.addOption(Global.buttons.get(i));
        }

        buttons.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Button:" + buttons.getComboBox().getSelectedValue());
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
    public void copy(Node node) {
        if(node instanceof NodeButton){
            buttons.selectValue(((NodeButton) node).buttons.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void execute() {

        PinData<ImString> buttonData = buttons.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(buttons, buttonData, buttons.getComboBox().getSelectedValue());

        outputData.getValue().set("Button(" + buttonData.getValue().get() + ")");
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
        return "A button constant.";
    }
}
