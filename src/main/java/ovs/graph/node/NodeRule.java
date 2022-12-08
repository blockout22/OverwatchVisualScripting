package ovs.graph.node;

import imgui.ImGui;
import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCondition;

public class NodeRule extends Node{

    private PinCondition conditionPin = new PinCondition();
    private PinAction actionPin = new PinAction();

    ComboBox comboEventOnGoing = new ComboBox();
    ComboBox comboTeam = new ComboBox();
    ComboBox comboPlayers = new ComboBox();

    boolean isGlobal = false;

    public NodeRule(Graph graph) {
        super(graph);
        setName("Rule");
        canEditTitle = true;

        conditionPin.setNode(this);
        addCustomInput(conditionPin);

        actionPin.setNode(this);
        addCustomInput(actionPin);

        comboEventOnGoing.addOption("Ongoing - Global");
        comboEventOnGoing.addOption("Ongoing - Each Player");
        comboEventOnGoing.addOption("Player Earned Elimination");
        comboEventOnGoing.addOption("Player Dealt Final Blow");
        comboEventOnGoing.addOption("Player Took Damage");
        comboEventOnGoing.addOption("Player Dealt Damage");
        comboEventOnGoing.addOption("Player Dealt Healing");
        comboEventOnGoing.addOption("Player Dealt Knockback");
        comboEventOnGoing.addOption("Player Received Healing");
        comboEventOnGoing.addOption("Player Received Knockback");
        comboEventOnGoing.addOption("Player Joined Match");
        comboEventOnGoing.addOption("Player Left Match");
        comboEventOnGoing.addOption("Player Died");
        comboEventOnGoing.addOption("Subroutine");

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

        for (int i = 0; i < Global.heroes.size(); i++) {
            comboPlayers.addOption(Global.heroes.get(i));
        }

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
//        for (int i = 0; i < inputPins.size(); i++) {
//            Pin pin = inputPins.get(i);
//
//            PinData<ImString> data = pin.getData();
//
//            if(pin.isConnected()){
//                Pin connectedPin = pin.getConnectedPin();
//
//                PinData<ImString> connectedData = connectedPin.getData();
//                data.getValue().set(connectedData.getValue().get());
//            }
//        }

        PinData<ImString> conditionData = conditionPin.getData();
        PinData<ImString> actionData = actionPin.getData();

        handlePinStringConnection(conditionPin, conditionData);
        handlePinStringConnection(actionPin, actionData);
    }

    @Override
    public String getOutput() {
        String out = "";
        out += "rule(\"" + getName() + "\")\n";
        out += "{\n";

        //EVENT
        out += "event\n";
        out += "{\n";
        out += "" + comboEventOnGoing.getSelectedValue() + ";\n";

        if(!isGlobal){
            out += "" + comboTeam.getSelectedValue() + ";\n";
            out += "" + comboPlayers.getSelectedValue() + ";\n";
        }

        out += "}\n";

        out += "\n";

        //CONDITIONS

        if(conditionPin.isConnected()){
            out += "conditions\n";
            out += "{\n";

            PinData<ImString> conditionData = conditionPin.getData();
            Pin connectedPin = conditionPin.getConnectedPin();
            boolean handleTabs = true;
            if(connectedPin.getNode() instanceof NodeConditionList){
                handleTabs = false;
            }

            out += (handleTabs ? "" : "") + conditionData.getValue().get() + "\n";

            out += "}\n";
            out += "\n";
        }

        //ACTIONS
        out+= "actions\n";
        {
            out += "{\n";
            //TODO make array
            PinData<ImString> actionData = actionPin.getData();
            if(actionPin.isConnected()){

                Pin connectedPin = actionPin.getConnectedPin();
                boolean handleTabs = true;
                if(connectedPin.getNode() instanceof NodeActionList){
                    handleTabs = false;
                }

                out += (handleTabs ? "" : "") + actionData.getValue().get() + "\n";
            }
//            for (int i = 0; i < inputPins.size(); i++) {
//                Pin pin = inputPins.get(i);
//                PinData<ImString> data = pin.getData();
//
//                String tempOut = ""; // data.getValue().get() + "\n";
//
//                if(pin.isConnected())
//                {
//                    tempOut = "";
//                    Pin connectedPin = pin.getConnectedPin();
//                    tempOut += "\t\t" + connectedPin.getNode().getOutput() + "\n";
//                }
//
//                out += tempOut;
//            }

            out += "}\n";
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
