package ovs.graph.pin;

import imgui.ImGui;
import imgui.type.ImFloat;
import ovs.graph.PinData;

public class PinFloat extends Pin{

    private PinData<ImFloat> data = new PinData<>();

    ImFloat floatData = new ImFloat();

    public PinFloat(){
        setData(data);
        setColor(1, 0.5f, 1, 1);
        data.setValue(new ImFloat(5));

        floatData.set(data.getValue().get());
    }

    @Override
    public boolean UI() {
        ImGui.pushItemWidth(150);
        if(ImGui.inputFloat("##Slider" + getID(), floatData, 0.1f))
        {
            data.getValue().set(floatData.get());
        }
        ImGui.popItemWidth();
        return true;
    }
}
