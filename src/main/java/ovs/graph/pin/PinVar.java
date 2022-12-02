package ovs.graph.pin;

import imgui.type.ImString;
import ovs.graph.PinData;

public class PinVar extends Pin{

    private PinData<ImString> data = new PinData<>();

    //TODO Pin Var can accept my types therefore I should add a custom option to toggle between all data types String, Boolean, Float etc... and display UI element based on type
    //e.g. scaling a player is a float however it's probably best to use a player variable
    public PinVar(){
        setData(data);
        setColor(1, 0, 0, 1);
        data.setValue(new ImString());

    }

    @Override
    public boolean UI() {
        return false;
    }
}
