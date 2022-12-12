package ovs.graph.UI;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiColorEditFlags;
import ovs.graph.Popup;
import ovs.graph.PopupHandler;
import ovs.graph.UI.Listeners.OnOpenedListener;

import java.util.ArrayList;

public class ColorPicker extends UiComponent{

    private float[] colorValues = new float[]{1, 1, 1, 1};

    private boolean requestPopup = false;

    private ArrayList<OnOpenedListener> onOpenedListeners = new ArrayList<OnOpenedListener>();

    Popup popup = new Popup() {
        @Override
        public boolean show() {
            if (ImGui.colorPicker4("Color Picker", colorValues, ImGuiColorEditFlags.DisplayRGB | ImGuiColorEditFlags.AlphaPreview)) {

            }
            return false;
        }
    };

    public float[] getColor(){
        return colorValues;
    }

    public void setRGBA(float r, float g, float b, float a){
        colorValues[0] = r;
        colorValues[1] = g;
        colorValues[2] = b;
        colorValues[3] = a;
    }

    @Override
    public void show() {
        if(requestPopup){
            PopupHandler.open(popup);
            for (int i = 0; i < onOpenedListeners.size(); i++) {
                onOpenedListeners.get(i).onOpen();
            }
            requestPopup = false;
        }

        if(ImGui.button("Color Picker##" + uniqueID)){
            requestPopup = true;
        }
    }
}
