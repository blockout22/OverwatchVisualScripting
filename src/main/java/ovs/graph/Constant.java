package ovs.graph;

import imgui.ImGui;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImFloat;
import imgui.type.ImInt;
import imgui.type.ImString;
import ovs.graph.UI.UiComponent;

public class Constant extends UiComponent {

    public ImString keyValue = new ImString();
    public ImFloat numberValue = new ImFloat();
    public ImString stringValue = new ImString();
    public float[] vectorValues = new float[3];
    public enum Type {
        NUMBER, STRING, VECTOR
    }

    private ImInt currentSelection = new ImInt();
    private String[] listOfTypes = new String[]{Type.NUMBER.name(), Type.STRING.name(), Type.VECTOR.name()};


    private Type type;

    public Constant(Type _type) {
        type = _type;
        currentSelection.set(type.ordinal());
    }

    private void showCombobox(){
        if(ImGui.combo("##ConstantType" + uniqueID, currentSelection, listOfTypes))
        {
            type = Type.values()[currentSelection.get()];
        }
    }
    public void show(){
        ImGui.pushItemWidth(100);
        if(ImGui.inputText("##Key" + uniqueID, keyValue, ImGuiInputTextFlags.CharsNoBlank))
        {

        }
        ImGui.popItemWidth();
        ImGui.sameLine();
        ImGui.pushItemWidth(125);
        showCombobox();
        ImGui.popItemWidth();
        ImGui.sameLine();
        if(ImGui.button("X##Constant_Remove" + uniqueID)){

        }
        switch (type){
            case NUMBER:
                if (ImGui.inputFloat("##ConstNumber" + uniqueID, numberValue, 0.1f)){

                }
                break;
            case STRING:
                if(ImGui.inputText("##ConstString" + uniqueID, stringValue)){

                }
                break;
            case VECTOR:
                if(ImGui.inputFloat3("##ConstVector" + uniqueID, vectorValues)){

                }
                break;
        }
    }

    public String getOutput(){
        switch (type){
            case NUMBER:
                return numberValue.toString();
            case STRING:
                return stringValue.toString();
            case VECTOR:
                return "Vector(" + vectorValues[0] + ", " + vectorValues[1] + ", " + vectorValues[2] + ")";
        }
        return "";
    }

    public Type getType(){
        return type;
    }
}
