package ovs.graph.pin;

import imgui.type.ImString;
import ovs.graph.PinData;

public class PinIf extends Pin{

    PinData<ImString> data = new PinData<>();

    public PinIf(){
        setData(data);
        setColor(0.1f, 0.75f, 0.5f, 1f);
        data.setValue(new ImString(500));
    }

    @Override
    public boolean UI() {
        return false;
    }
}
