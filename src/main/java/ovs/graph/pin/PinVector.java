package ovs.graph.pin;

import imgui.ImGui;
import imgui.ImVec4;
import ovs.graph.PinData;

public class PinVector extends Pin{

    private PinData<ImVec4> data = new PinData<>();

    private float[] vec3Float = new float[]{0, 0, 0};

    public PinVector(){
        setData(data);
        setColor(0.25f, 0.5f, 1, 1);
        data.setValue(new ImVec4());

    }

    @Override
    public boolean UI() {
        ImGui.pushItemWidth(150);
        if(ImGui.inputFloat3("##f3Slider" + getID(), vec3Float)){
            data.setValue(new ImVec4(vec3Float[0], vec3Float[1], vec3Float[2], 0));
        }
        ImGui.popItemWidth();
        return false;
    }
}
