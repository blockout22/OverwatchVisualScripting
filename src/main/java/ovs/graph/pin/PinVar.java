package ovs.graph.pin;

import imgui.ImGui;
import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.PinData;

public class PinVar extends Pin{

    private PinData<ImString> data = new PinData<>();

    ImFloat floatData = new ImFloat();

    Type type = Type.Null;

    //TODO Pin Var can accept my types therefore I should add a custom option to toggle between all data types String, Boolean, Float etc... and display UI element based on type
    //e.g. scaling a player is a float however it's probably best to use a player variable
    public PinVar(){
        setData(data);
        setColor(1, 0, 0, 1);
        data.setValue(new ImString(10000));
    }

    @Override
    public boolean UI() {
        if(type == Type.Null){
            return false;
        }
        if(type == Type.String){
            ImGui.inputText("##" + getID(), data.getValue());
        }

        if(type == Type.Float){
            if(ImGui.inputFloat("##" + getID(), floatData)){
                if(!isConnected()){
                    data.getValue().set(floatData.get());
                }else{
//                    floatData.set();
                }
            }
        }

        return true;
    }

    public void setType(Type type){
        this.type = type;
    }

    public enum Type{
        Null,
        String,
        Float
    }
}
