package ovs.graph.pin;

import imgui.type.ImString;
import ovs.graph.PinData;

public class PinCondition extends Pin{

    PinData<ImString> data = new PinData<>();

    public PinCondition() {
        setData(data);
        setColor(0f, 0.2f, 1f, 1f);
        data.setValue(new ImString(500));

    }

    @Override
    public boolean UI() {
        return false;
    }
}
