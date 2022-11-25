package ovs.graph.pin;

import imgui.ImGui;
import imgui.type.ImBoolean;
import ovs.graph.PinData;

public class PinBoolean extends Pin{

    private PinData<ImBoolean> data = new PinData<>();
    private ImBoolean bool = new ImBoolean();

    public PinBoolean(){
        setData(data);
        setColor(0.11372549f, 0.2f, 0.890196078f, 1);
        data.setValue(new ImBoolean());
    }

    @Override
    public boolean UI() {
        if(ImGui.checkbox("##" + getID(), bool))
        {
            data.getValue().set(bool.get());
        }
        return true;
    }

    @Override
    public void loadValue(String value) {
        boolean val = Boolean.valueOf(value);
        data.getValue().set(val);
        bool.set(val);
    }
}
