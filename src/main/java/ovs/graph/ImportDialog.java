package ovs.graph;

import imgui.ImGui;
import imgui.type.ImString;

import static imgui.flag.ImGuiWindowFlags.*;

public class ImportDialog {

    private ImString outputText = new ImString(100000);

    private boolean isOpen = false;

    public void open(){
        ImGui.openPopup("ImportPopup");
        isOpen = true;
    }

    public boolean isOpen(){
        return isOpen;
    }

    public int show(){
        int state = -1;
        if(ImGui.isPopupOpen("ImportPopup")) {
            if(ImGui.beginPopupModal("ImportPopup", NoTitleBar | NoResize | AlwaysAutoResize)) {
                ImGui.text("Script Import (Partially Functional)");
                if (ImGui.inputTextMultiline("##teaxArea", outputText, 500, 500)) {
                }

                if(ImGui.button("Close")){
                    state = 0;
                    isOpen = false;
                    ImGui.closeCurrentPopup();
                }

                ImGui.sameLine();

                if(ImGui.button("Import")) {
                    state = 1;
                    isOpen = false;
                    ImGui.closeCurrentPopup();
                }

                ImGui.endPopup();
            }
        }

        return state;
    }

    public String getContent(){
        return outputText.get();
    }
}
