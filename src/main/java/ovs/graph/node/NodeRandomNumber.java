package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodeRandomNumber extends Node {

    ComboBox randomType = new ComboBox("Integer", "Real");

    PinVar pinMin = new PinVar();
    PinVar pinMax = new PinVar();
    PinVar output = new PinVar();

    public NodeRandomNumber(Graph graph) {
        super(graph);
        setName("Random Number");

        pinMin.setNode(this);
        pinMin.setName("Min");
        addCustomInput(pinMin);

        pinMax.setNode(this);
        pinMax.setName("Max");
        addCustomInput(pinMax);

        randomType.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Random:" + randomType.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Random")){
                try{
                    randomType.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    randomType.select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> minData = pinMin.getData();
        PinData<ImString> maxData = pinMax.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinMin, minData);
        handlePinStringConnection(pinMax, maxData);

        outputData.getValue().set("Random " + randomType.getSelectedValue() + "(" + minData.getValue().get() + ", " + maxData.getValue().get() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        randomType.show();
    }
}