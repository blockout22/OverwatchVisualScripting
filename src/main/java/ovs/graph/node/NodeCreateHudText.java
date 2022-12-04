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

    PinVar inputVisibleTo = new PinVar();
    PinVar inputText1 = new PinVar();
    PinVar inputText2 = new PinVar();
    PinVar inputText3 = new PinVar();

    PinFloat pinSortOrder = new PinFloat();

    PinAction outputPin = new PinAction();

    ComboBox comboBox = new ComboBox();

    public NodeCreateHudText(Graph graph) {
        super(graph);
        setName("Create Hud Text");

        inputVisibleTo.setNode(this);
        inputVisibleTo.setName("Visible To");
        addCustomInput(inputVisibleTo);

        inputText1.setNode(this);
        inputText1.setName("Header");
        addCustomInput(inputText1);

        inputText2.setNode(this);
        inputText2.setName("Subheader");
        addCustomInput(inputText2);

        inputText3.setNode(this);
        inputText3.setName("Text");
        addCustomInput(inputText3);

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
        PinData<ImString> inputVisibleData = inputVisibleTo.getData();
        PinData<ImString> inputData1 = inputText1.getData();
        PinData<ImString> inputData2 = inputText2.getData();
        PinData<ImString> inputData3 = inputText3.getData();
        PinData<ImFloat> sortOrderData = pinSortOrder.getData();

        PinData<ImString> outputData = outputPin.getData();

        if(inputVisibleTo.isConnected()){
            Pin connectedTo = inputVisibleTo.getConnectedPin();

            PinData<ImString> connectedData = connectedTo.getData();
            inputVisibleData.getValue().set(connectedData.getValue().get());
        }else{
            inputVisibleData.getValue().set("Null");
        }

        if(inputText1.isConnected()){
            Pin connectedPin = inputText1.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            inputData1.getValue().set(connectedData.getValue().get());
        }else{
            inputData1.getValue().set("Null");
        }

        if(inputText2.isConnected()){
            Pin connectedPin = inputText2.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            inputData2.getValue().set(connectedData.getValue().get());
        }else{
            inputData2.getValue().set("Null");
        }

        if(inputText3.isConnected()){
            Pin connectedPin = inputText3.getConnectedPin();

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
        String output = "Create HUD Text(" + inputVisibleData.getValue().get() + "," + inputData1.getValue().get() + ", " + inputData2.getValue().get() + ", " + inputData3.getValue().get() + ", " + location + ", " + sortOrderData.getValue().get() + ", Color(White), Color(White), Color(White), Visible To and String, Default Visibility);";
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
