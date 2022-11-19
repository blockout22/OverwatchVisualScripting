package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinString;

public class NodeCreateHudText extends Node{

    PinString customStringPin = new PinString();
    PinAction outputPin = new PinAction();

    ComboBox comboBox = new ComboBox();

    public NodeCreateHudText(Graph graph) {
        super(graph);
        setName("Create Hud Text");


        customStringPin.setNode(this);
        addCustomInput(customStringPin);

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        comboBox.addOption("Left");
        comboBox.addOption("Top");
        comboBox.addOption("Right");

        comboBox.select(0);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = customStringPin.getData();
        PinData<ImString> outputData = outputPin.getData();

        if(customStringPin.isConnected()){
            Pin connectedPin = customStringPin.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            inputData.getValue().set(connectedData.getValue().get());
        }

        outputData.getValue().set(inputData.getValue().get());
    }

    @Override
    public String getOutput() {
        PinData<ImString> customString = customStringPin.getData();

        String location = comboBox.getSelectedValue();
        String output = "Create HUD Text(All Players(All Teams), Null, Custom String(\"" + customString.getValue().get() + "\"), Null, " + location + ", -50, Color(White), Color(White), Color(White), Visible To and String, Default Visibility);";
        return output;
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
