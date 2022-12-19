package ovs;

import imgui.ImColor;
import imgui.ImGui;

public class Debugger {

    public static void drawImGuiBounds(){
        ImGui.getWindowDrawList().addRect(ImGui.getItemRectMinX(), ImGui.getItemRectMinY(), ImGui.getItemRectMaxX(), ImGui.getItemRectMaxY(), ImColor.floatToColor(1, 0, 0, 1));
//        ImGui.getWindowDrawList().addRect(ImGui.getItemRectMin(), ImGui.getItemRectMax(), ImColor.floatToColor(1, 0, 0, 1));
    }
}
