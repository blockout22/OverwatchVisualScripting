package ovs.graph.pin;

import imgui.ImGui;
import imgui.type.ImString;
import ovs.graph.PinData;

public class PinString extends Pin{

    private PinData<ImString> data = new PinData<>();

    public PinString()
    {
        setData(data);
        setColor(0, 0.392156863f, 0.392156863f, 1);
        data.setValue(new ImString());
    }

    @Override
    public void loadValue(String value) {
        data.getValue().set(value);
    }

    @Override
    public boolean UI() {
        if(ImGui.inputText("##" + getID(), data.getValue())){

        };

        return true;
    }
}
