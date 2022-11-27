package ovs.graph.node;

import imgui.ImGui;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.Button;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.LeftClickListener;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;

public class NodeRule extends Node{

    private PinAction inputPin;

    ComboBox comboEventOnGoing = new ComboBox();
    ComboBox comboTeam = new ComboBox();
    ComboBox comboPlayers = new ComboBox();

    boolean isGlobal = false;

    Button button = new Button("Add Input");

    public NodeRule(Graph graph) {
        super(graph);
        setName("Rule");
        canEditTitle = true;

        inputPin = new PinAction();
        inputPin.setNode(this);
        addCustomInput(inputPin);

        comboEventOnGoing.addOption("Ongoing - Global");
        comboEventOnGoing.addOption("Ongoing - Each Player");
        comboEventOnGoing.addOption("Player Earned Elimination");
        comboEventOnGoing.addOption("Player Dealt Final Blow");
        comboEventOnGoing.addOption("Player Took Damage");
        comboEventOnGoing.addOption("Player Dealt Damage");
        comboEventOnGoing.addOption("Player Died");

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
                if(newValue == "Ongoing - Global"){
                    isGlobal = true;
                }else{
                    isGlobal = false;
                }
            }
        });

        comboEventOnGoing.select(0);
        comboTeam.select(0);
        comboPlayers.select(0);

        addUiComponent(button);

        button.addLeftClickListener(new LeftClickListener() {
            @Override
            public void onClicked() {
                Pin pin = new PinAction();
                pin.setNode(self);
                pin.setCanDelete(true);
                addCustomInput(pin);
            }
        });
    }

    public void onSaved()
    {
        getExtraSaveData().clear();
        getExtraSaveData().add("EventOnGoing:" + comboEventOnGoing.getSelectedValue());
        getExtraSaveData().add("Team:" + comboTeam.getSelectedValue());
        getExtraSaveData().add("Player:" + comboPlayers.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for (String data : getExtraSaveData()){
            if(data.startsWith("EventOnGoing"))
            {
                comboEventOnGoing.selectValue(data.split(":")[1]);
            }else if(data.startsWith("Team"))
            {
                comboTeam.selectValue(data.split(":")[1]);
            }else if(data.startsWith("Player")){
                comboPlayers.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        for (int i = 0; i < inputPins.size(); i++) {
            Pin pin = inputPins.get(i);

            PinData<ImString> data = pin.getData();

            if(pin.isConnected()){
                Pin connectedPin = pin.getConnectedPin();

                PinData<ImString> connectedData = connectedPin.getData();
                data.getValue().set(connectedData.getValue().get());
            }
        }
    }

    @Override
    public String getOutput() {
        String out = "";
        out += "rule(\"" + getName() + "\")\n";
        out += "{\n\t";

        //EVENT
        out += "event\n";
        out += "\t{\n";
        out += "\t\t" + comboEventOnGoing.getSelectedValue() + ";\n";

        if(!isGlobal){
            out += "\t\t" + comboTeam.getSelectedValue() + ";\n";
            out += "\t\t" + comboPlayers.getSelectedValue() + ";\n";
        }

        out += "\t}\n";

        out += "\n";

        //CONDITIONS

        //ACTIONS
        out+= "\tactions\n";
        {
            out += "\t{\n";
            //TODO make array
            for (int i = 0; i < inputPins.size(); i++) {
                Pin pin = inputPins.get(i);
                PinData<ImString> data = pin.getData();

                String tempOut = ""; // data.getValue().get() + "\n";

                if(pin.isConnected())
                {
                    tempOut = "";
                    Pin connectedPin = pin.getConnectedPin();
                    tempOut += "\t\t" + connectedPin.getNode().getOutput() + "\n";
                }

                out += tempOut;
            }

            out += "\t}\n";
        }

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
        ImGui.text("Event");
        comboEventOnGoing.show();

        if(!isGlobal){
            comboTeam.show();
            comboPlayers.show();
        }
    }
}
