package ovs.graph.pin;

import imgui.ImGui;
import imgui.type.ImFloat;
import ovs.graph.PinData;

public class PinFloat extends Pin{

    private PinData<ImFloat> data = new PinData<>();

    public PinFloat(){
        setData(data);
        setColor(1, 0.5f, 1, 1);
        data.setValue(new ImFloat(0));
    }

    @Override
    public boolean UI() {
        ImGui.pushItemWidth(150);
        if(ImGui.inputFloat("##Slider" + getID(), data.getValue(), 0.1f))
        {
            data.getValue().set(data.getValue().get());
        }
        ImGui.popItemWidth();
        return true;
    }

    @Override
    public void loadValue(String value) {
        float val = Float.valueOf(value);

        data.getValue().set(val);
    }
}
