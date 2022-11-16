package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinOnGoing;
import ovs.graph.pin.PinString;

public class NodeRule extends Node{

    private PinOnGoing pinOnGoing;
    private PinString pinString, pinString2;

    public NodeRule(Graph graph) {
        super(graph);
        setName("Rule");

        pinOnGoing = new PinOnGoing();
        pinOnGoing.setNode(this);
        addCustomInput(pinOnGoing);

        pinString = new PinString();
        pinString.setNode(this);
        addCustomInput(pinString);

        pinString = new PinString();
        pinString.setNode(this);
        addCustomInput(pinString);

        pinString2 = new PinString();
        pinString2.setNode(this);
        addCustomOutput(pinString2);
    }

    @Override
    public String getOutput() {
        String out = "";
        out += "rule(\"" + getName() + "\")\n";
        out += "{\n\t";

        //EVENT
        out += "event\n";
        out += "\t{\n";
        PinData<ImString> onGoingData = pinOnGoing.getData();;
        out += "\t\tOngoing - " + onGoingData.getValue() + "\n";
        out += "\t}\n";

        out += "\n";

        //CONDITIONS

        //ACTIONS
        out+= "\tactions\n";
        out += "\t{\n";
        //TODO make array
        PinData<ImString> pinAction1 = pinString.getData();
        out += "\t\tDisable Movement Collision With Players(Event Player);\n";
        out += "\t}\n";

//        for(int i = 0; i < inputPins.size(); i++){
//            Pin pin = inputPins.get(i);
//
//            PinData<ImString> data = pin.getData();
//            String input = String.valueOf(data.value.get());
//
//            if(pin.connectedTo != -1){
//                Pin conenctedPin = getGraph().findPinById(pin.connectedTo);
//                input = conenctedPin.getNode().getOutput();
//            }
//
//            out += input;
//        }

        out += "}\n";

        return out;
    }

    @Override
    public void UI() {

    }
}
