package ovs.graph.pin;

import imgui.type.ImString;
import ovs.graph.PinData;

public class PinAction extends Pin{

    public PinData<ImString> data = new PinData<>();

    public PinAction(){
        setData(data);
        setColor(0, 1, 0, 1);
        data.setValue(new ImString());
    }

    @Override
    public boolean UI() {
        return false;
    }
}