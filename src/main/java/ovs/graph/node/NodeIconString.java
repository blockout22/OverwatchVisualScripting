package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeIconString extends Node {

    PinCombo pinIcon = new PinCombo();

    PinVar output = new PinVar();

    public NodeIconString(Graph graph) {
        super(graph);
        setName("Icon String");

        pinIcon.setNode(this);
        pinIcon.setName("Icon");
        addCustomInput(pinIcon);

        pinIcon.getComboBox().addOptions(Global.icons);
        pinIcon.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Icon:" + pinIcon.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Icon")){
                try{
                    String[] values = data.split(":");
                    String value = "";

                    for (int i = 1; i < values.length; i++) {
                        value += values[i];
                        if(i + 1 < values.length){
                            value += ":";
                        }
                    }
                    pinIcon.getComboBox().selectValue(value);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinIcon.getComboBox().select(0);
                }
            }
        }
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeCreateIcon){
            pinIcon.getComboBox().selectValue(((NodeCreateIcon) node).pinIcon.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set(getName() + "(" + pinIcon.getComboBox().getSelectedValue() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {

    }
}