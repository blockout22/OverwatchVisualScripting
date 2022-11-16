package ovs.graph;

import imgui.ImGui;

public class PopupHandler {

    //    protected static ArrayList<Popup> openPopups = new ArrayList<>();
    protected static Popup currentPopup = null;
//    protected static ArrayList<Popup> openPopupsToRemove = new ArrayList<>();

    public static void open(Popup popup){
        ImGui.openPopup(popup.id.toString());
        currentPopup = popup;
//        openPopups.add(popup);
    }

    protected static void remove(Popup popup){
//        openPopupsToRemove.add(popup);
        currentPopup = null;
    }
}
