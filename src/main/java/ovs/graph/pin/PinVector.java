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

    @Override
    public void loadValue(String value) {
        String[] s = value.substring(7, value.length() - 1).replace(" ", "").split(",");

        float x = Float.valueOf(s[0].substring(2));
        float y = Float.valueOf(s[1].substring(2));
        float z = Float.valueOf(s[2].substring(2));
        float w = Float.valueOf(s[3].substring(2));

        data.getValue().set(x, y, z, w);
        vec3Float[0] = x;
        vec3Float[1] = y;
        vec3Float[2] = z;
    }
}
