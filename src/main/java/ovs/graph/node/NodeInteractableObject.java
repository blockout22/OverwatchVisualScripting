package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

import java.util.ArrayList;

public class NodeInteractableObject extends NodeEntry{

    PinVar pinVisibleTo = new PinVar();
    PinCombo pinType = new PinCombo();
    PinVar pinColor = new PinVar();
    PinVar pinPosition = new PinVar();
    PinVar pinRadius = new PinVar();
    PinCombo pinReevaluation = new PinCombo();
    PinVar pinPositionVar = new PinVar();
    PinVar pinRadiusVar = new PinVar();

    PinAction pinAction = new PinAction();

    public NodeInteractableObject(Graph graph) {
        super(graph);
        setName("Interactable Object");
        setColor(0, 255, 0);
        canEditTitle = true;

        pinVisibleTo.setNode(this);
        pinVisibleTo.setName("Visible To");
//        addCustomInput(pinVisibleTo);

        pinType.setNode(this);
        pinType.setName("Type");
        addCustomInput(pinType);

        for (int i = 0; i < Global.effectType.size(); i++) {
            String effectType = Global.effectType.get(i);
            pinType.addOption(effectType);
        }
        pinType.sort();
        pinType.select(0);

        pinColor.setNode(this);
        pinColor.setName("Color");
        addCustomInput(pinColor);

        pinPosition.setNode(this);
        pinPosition.setName("Position");
        addCustomInput(pinPosition);

        pinRadius.setNode(this);
        pinRadius.setName("Radius");
        addCustomInput(pinRadius);

        pinReevaluation.setNode(this);
        pinReevaluation.setName("Reevaluation");
        addCustomInput(pinReevaluation);

        pinPositionVar.setNode(this);
        pinPositionVar.setName("Position Variable");
        addCustomInput(pinPositionVar);

        pinRadiusVar.setNode(this);
        pinRadiusVar.setName("Radius Variable");
        addCustomInput(pinRadiusVar);

        pinAction.setNode(this);
        pinAction.setName("On Interact");
        addCustomInput(pinAction);

        ArrayList<String> reevalOptions = new ArrayList<>();
        reevalOptions.add("Visible To Position And Radius");
        reevalOptions.add("Position And Radius");
        reevalOptions.add("Visible To");
        reevalOptions.add("None");
        reevalOptions.add("Visible To, Position, Radius, And Color");
        reevalOptions.add("Position, Radius, And Color");
        reevalOptions.add("Visible To And Color");
        reevalOptions.add("Color");

        pinReevaluation.getComboBox().addOptions(reevalOptions);
        pinReevaluation.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Type:" + pinType.getComboBox().getSelectedValue());
        getExtraSaveData().add("Reevaluation:" + pinReevaluation.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Type")){
                try{
                    pinType.getComboBox().selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinType.getComboBox().select(-1);
                }
            }

            if(data.startsWith("Reevaluation")){
                try {
                    pinReevaluation.selectValue(data.split(":")[1]);
                }catch (IndexOutOfBoundsException e){
                    pinReevaluation.select(0);
                }
            }
        }
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeCreateEffect){
            pinType.getComboBox().selectValue(((NodeCreateEffect) node).pinType.getComboBox().getSelectedValue());
            pinReevaluation.selectValue(((NodeCreateEffect) node).pinReevaluation.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void execute() {
    }

    @Override
    public String getOutput() {
        PinData<ImString> visibleToData = pinVisibleTo.getData();
        PinData<ImString> typeData = pinType.getData();
        PinData<ImString> colorData = pinColor.getData();
        PinData<ImString> positionData = pinPosition.getData();
        PinData<ImString> radiusData = pinRadius.getData();
        PinData<ImString> reevauationData = pinReevaluation.getData();
        PinData<ImString> positionVariableData = pinPositionVar.getData();
        PinData<ImString> radiusVaribleData = pinRadiusVar.getData();
        PinData<ImString> actionData = pinAction.getData();

        handlePinStringConnection(pinVisibleTo, visibleToData, "All Players(All Teams)");
        handlePinStringConnection(pinType, typeData, pinType.getComboBox().getSelectedValue());
        handlePinStringConnection(pinColor, colorData);
        handlePinStringConnection(pinPosition, positionData);
        handlePinStringConnection(pinRadius, radiusData);
        handlePinStringConnection(pinReevaluation, reevauationData, pinReevaluation.getComboBox().getSelectedValue());
        handlePinStringConnection(pinPositionVar, positionVariableData);
        handlePinStringConnection(pinRadiusVar, radiusVaribleData);
        handlePinStringConnection(pinAction, actionData);

        String out = "";
        out += "rule(\"" + getName() +"\")\n";
        out += "{\n";
        {
            out += "event\n";
            out += "{\n";
            out += "Ongoing - Global;\n";
            out += "}\n";

            out += "\n";

            out += "actions\n";
            out += "{\n";
            {
                out += positionVariableData.getValue().get() + " = " + positionData.getValue().get() + ";\n";
                out += radiusVaribleData.getValue().get() + " = " + radiusData.getValue().get() + ";\n";
                out += "Create Effect (" + visibleToData.getValue().get() + ", " + typeData.getValue().get() + ", " + colorData.getValue().get() + ", " + positionVariableData.getValue().get() + ", " + radiusVaribleData.getValue().get() + ", " + reevauationData.getValue().get() + ");\n";
            }
            out += "}\n";
        }
        out += "}\n";

        out += "rule(\"" + getName() + " (Interact)" +"\")\n";
        out += "{\n";
        {
            out += "event\n";
            out += "{\n";
            out += "Ongoing - Each Player;\n";
            out += "All;\n";
            out += "All;\n";
            out += "}\n";

            out += "\n";

            out += "conditions\n";
            out += "{\n";
            {
                out += "(Distance Between(Position Of(Event Player), " + positionVariableData.getValue().get() + ") <= " + radiusVaribleData.getValue().get() + ") == (True);\n";
                out += "(Is Button Held(Event Player, Button(Interact))) == (True);\n";
            }
            out += "}\n";

            out += "\n";

            out += "actions\n";
            out += "{\n";
            {
                out += actionData.getValue().get() + "\n";
            }
            out += "}\n";
        }
        out += "}\n";


        return out;
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "Adds an effect into the world and allows you to interact with it";
    }
}
