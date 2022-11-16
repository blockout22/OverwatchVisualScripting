package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.pin.PinString;

public class NodeRule extends Node{

    private PinString pinString, pinString2;

    ComboBox comboEventOnGoing = new ComboBox();
    ComboBox comboTeam = new ComboBox();
    ComboBox comboPlayers = new ComboBox();

    boolean isGlobal = false;

    public NodeRule(Graph graph) {
        super(graph);
        setName("Rule");

        pinString = new PinString();
        pinString.setNode(this);
        addCustomInput(pinString);

        pinString = new PinString();
        pinString.setNode(this);
        addCustomInput(pinString);

        pinString2 = new PinString();
        pinString2.setNode(this);
        addCustomOutput(pinString2);

        comboEventOnGoing.addOption("Global");
        comboEventOnGoing.addOption("Each Player");

        comboTeam.addOption("All");
        comboTeam.addOption("Team 1");
        comboTeam.addOption("Team 2");

        comboPlayers.addOption("All");
        comboPlayers.addOption("Slot 0");
        comboPlayers.addOption("Slot 1");
        comboPlayers.addOption("Slot 2");
        comboPlayers.addOption("Slot 3");
        comboPlayers.addOption("Slot 4");
        comboPlayers.addOption("Slot 5");
        comboPlayers.addOption("Slot 6");
        comboPlayers.addOption("Slot 7");
        comboPlayers.addOption("Slot 8");
        comboPlayers.addOption("Slot 9");
        comboPlayers.addOption("Slot 10");
        comboPlayers.addOption("Slot 11");

        comboEventOnGoing.addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                if(newValue == "Global"){
                    isGlobal = true;
                }else{
                    isGlobal = false;
                }
            }
        });

        comboEventOnGoing.select(0);
        comboTeam.select(0);
        comboPlayers.select(0);
    }

    @Override
    public String getOutput() {
        String out = "";
        out += "rule(\"" + getName() + "\")\n";
        out += "{\n\t";

        //EVENT
        out += "event\n";
        out += "\t{\n";
        out += "\t\tOngoing - " + comboEventOnGoing.getSelectedValue() + ";\n";

        if(!isGlobal){
            out += "\t\t" + comboTeam.getSelectedValue() + ";\n";
            out += "\t\t" + comboPlayers.getSelectedValue() + ";\n";
        }

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
        comboEventOnGoing.show();

        if(!isGlobal){
            comboTeam.show();
            comboPlayers.show();
        }
    }
}
