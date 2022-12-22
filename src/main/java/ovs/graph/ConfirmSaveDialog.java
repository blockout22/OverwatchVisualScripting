package ovs.graph;

import imgui.ImGui;

import static imgui.flag.ImGuiWindowFlags.*;

public class ConfirmSaveDialog {
    private boolean isOpen = false;

    public int show() {
        int state = -1;

        if(ImGui.isPopupOpen("ConfirmSave")){

            if(ImGui.beginPopupModal("ConfirmSave", NoTitleBar | NoResize | AlwaysAutoResize)){
                ImGui.text("Do you want to save?");

                if(ImGui.button("No")){
                    state = 0;
                    isOpen = false;
                    ImGui.closeCurrentPopup();
                }

                ImGui.sameLine();

                if(ImGui.button("Yes")){
                    state = 1;
                    isOpen = false;
                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }
        }

        if(!isOpen){
            ImGui.openPopup("ConfirmSave");
            System.out.println("Opening");
            isOpen = true;
        }

        return state;

    }
}
