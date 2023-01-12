package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodeMaxHealthOfType extends Node {

    ComboBox healthType = new ComboBox(Global.healthType);

    PinVar pinPlayer = new PinVar();
    PinVar output = new PinVar();

    public NodeMaxHealthOfType(Graph graph) {
        super(graph);
        setName("Max Health Of Type");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        healthType.select(0);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Type:" + healthType.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Type")){
                try{
                    healthType.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    healthType.select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);

        outputData.getValue().set(getName() + "(" + playerData.getValue().get() + ", " + healthType.getSelectedValue() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        healthType.show();
    }
}