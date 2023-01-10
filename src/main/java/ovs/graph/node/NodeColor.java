package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeColor extends Node {

    PinCombo color = new PinCombo();
    PinVar output = new PinVar();

    public NodeColor(Graph graph) {
        super(graph);
        setName("Color");

        color.setNode(this);
        color.setName("Color");
        addCustomInput(color);

        for (int i = 0; i < Global.colors.size(); i++) {
            color.addOption(Global.colors.get(i));
        }

        color.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Color:" + color.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Color")){
                try{
                    color.getComboBox().selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    color.getComboBox().select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> colorData = color.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(color, colorData);

        outputData.getValue().set("Color(" + colorData.getValue().get() + ")");
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