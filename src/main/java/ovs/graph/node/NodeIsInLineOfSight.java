package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodeIsInLineOfSight extends Node {

    ComboBox los = new ComboBox(Global.barriersLOS);

    PinVar pinStartPosition = new PinVar();
    PinVar pinEndPosition = new PinVar();
    PinVar output = new PinVar();

    public NodeIsInLineOfSight(Graph graph) {
        super(graph);
        setName("Is In Line Of Sight");

        pinStartPosition.setNode(this);
        pinStartPosition.setName("Start Position");
        addCustomInput(pinStartPosition);

        pinEndPosition.setNode(this);
        pinEndPosition.setName("End Position");
        addCustomInput(pinEndPosition);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("LOS:" + los.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("LOS"))
            {
                los.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> startData = pinStartPosition.getData();
        PinData<ImString> endData = pinEndPosition.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinStartPosition, startData);
        handlePinStringConnection(pinEndPosition, endData);

        outputData.getValue().set("Is In Line Of Sight(" + startData.getValue().get() + ", " + endData.getValue().get() + ", " + los.getSelectedValue() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        los.show();
    }

    @Override
    public String getTooltip() {
        return "Whether two positions have ling of sight with each other.";
    }
}