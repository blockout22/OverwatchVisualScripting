package ovs.graph.pin;

import imgui.type.ImString;
import ovs.graph.PinData;

public class PinVisibleTo extends Pin{

    PinData<ImString> data = new PinData<>();

    public PinVisibleTo(){
        setData(data);
        setColor(1f, 0.5f, 1f, 1f);
        data.setValue(new ImString("All Players(All Teams)"));
    }

    @Override
    public boolean UI() {
        return false;
    }
}
