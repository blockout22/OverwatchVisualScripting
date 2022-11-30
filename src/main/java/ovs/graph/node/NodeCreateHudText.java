package ovs.graph.node;

import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinFloat;
import ovs.graph.pin.PinVar;

public class NodeCreateHudText extends Node{

    PinVar inputVal1 = new PinVar();
    PinVar inputVal2 = new PinVar();
    PinVar inputVal3 = new PinVar();

    PinFloat pinSortOrder = new PinFloat();

    PinAction outputPin = new PinAction();

    ComboBox comboBox = new ComboBox();

    public NodeCreateHudText(Graph graph) {
        super(graph);
        setName("Create Hud Text");

        inputVal1.setNode(this);
        addCustomInput(inputVal1);

        inputVal2.setNode(this);
        addCustomInput(inputVal2);

        inputVal3.setNode(this);
        addCustomInput(inputVal3);

        pinSortOrder.setNode(this);
        addCustomInput(pinSortOrder);

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        comboBox.addOption("Left");
        comboBox.addOption("Top");
        comboBox.addOption("Right");

        comboBox.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Location:" + comboBox.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Location:"))
            {
                comboBox.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> inputData1 = inputVal1.getData();
        PinData<ImString> inputData2 = inputVal2.getData();
        PinData<ImString> inputData3 = inputVal3.getData();
        PinData<ImFloat> sortOrderData = pinSortOrder.getData();

        PinData<ImString> outputData = outputPin.getData();

        if(inputVal1.isConnected()){
            Pin connectedPin = inputVal1.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            inputData1.getValue().set(connectedData.getValue().get());
        }else{
            inputData1.getValue().set("Null");
        }

        if(inputVal2.isConnected()){
            Pin connectedPin = inputVal2.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            inputData2.getValue().set(connectedData.getValue().get());
        }else{
            inputData2.getValue().set("Null");
        }

        if(inputVal3.isConnected()){
            Pin connectedPin = inputVal3.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            inputData3.getValue().set(connectedData.getValue().get());
        }else{
            inputData3.getValue().set("Null");
        }

        if(pinSortOrder.isConnected()){
            Pin connectedPin = pinSortOrder.getConnectedPin();

            PinData<ImFloat> connectedData = connectedPin.getData();
            sortOrderData.getValue().set(connectedData.getValue().get());
        }


        String location = comboBox.getSelectedValue();
        String output = "Create HUD Text(All Players(All Teams)," + inputData1.getValue().get() + ", " + inputData2.getValue().get() + ", " + inputData3.getValue().get() + ", " + location + ", " + sortOrderData.getValue().get() + ", Color(White), Color(White), Color(White), Visible To and String, Default Visibility);";
        outputData.getValue().set(output);
    }

    @Override
    public String getOutput() {
        PinData<ImString> output = outputPin.getData();

        return output.getValue().get();
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
