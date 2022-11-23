package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeCreateHudText extends Node{

    PinVar input = new PinVar();
    PinAction outputPin = new PinAction();

    ComboBox comboBox = new ComboBox();

    public NodeCreateHudText(Graph graph) {
        super(graph);
        setName("Create Hud Text");


        input.setNode(this);
        addCustomInput(input);

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        comboBox.addOption("Left");
        comboBox.addOption("Top");
        comboBox.addOption("Right");

        comboBox.select(0);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = input.getData();
        PinData<ImString> outputData = outputPin.getData();

        if(input.isConnected()){
            Pin connectedPin = input.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            inputData.getValue().set(connectedData.getValue().get());
        }
        String location = comboBox.getSelectedValue();
        String output = "Create HUD Text(All Players(All Teams), Null, " + inputData.getValue().get() + ", Null, " + location + ", -50, Color(White), Color(White), Color(White), Visible To and String, Default Visibility);";
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
