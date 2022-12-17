package ovs.graph.pin;

import imgui.type.ImString;
import ovs.graph.PinData;

public class PinElse extends Pin{

    PinData<ImString> data = new PinData<>();

    public PinElse() {
        setData(data);
        setColor(0.368627451f, 0.658823529f, 0.450980392f, 1f);
        data.setValue(new ImString(5000));
    }

    @Override
    public boolean UI() {
        return false;
    }
}
