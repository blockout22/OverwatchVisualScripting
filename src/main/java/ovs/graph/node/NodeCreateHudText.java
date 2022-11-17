package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinString;

public class NodeCreateHudText extends Node{

    PinString customStringPin = new PinString();
    PinString outputPin = new PinString();

    public NodeCreateHudText(Graph graph) {
        super(graph);
        setName("Create Hud Text");


        customStringPin.setNode(this);
        addCustomInput(customStringPin);

        outputPin.setNode(this);
        addCustomOutput(outputPin);
    }

    @Override
    public String getOutput() {
        PinData<ImString> customString = customStringPin.getData();

        String location = "Top";
        String output = "Create HUD Text(All Players, Null, Null, Custom String(\"" + customString.getValue().get() + "\"), " + location + ", -50, Color(White), Color(White), Color(White), Visible To and String, Default Visibility);";
        return output;
    }

    @Override
    public void UI() {

    }
}
