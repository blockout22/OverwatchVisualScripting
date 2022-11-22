package ovs.graph.pin;

import imgui.type.ImString;
import ovs.graph.PinData;

public class PinVar extends Pin{

    private PinData<ImString> data = new PinData<>();

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
