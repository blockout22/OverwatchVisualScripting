package ovs;

import imgui.ImGui;
import imgui.flag.ImGuiCond;

import java.awt.*;
import java.util.ArrayList;

import static imgui.flag.ImGuiWindowFlags.*;

public class Notification {

    private static ArrayList<String> notifactions = new ArrayList<>();

    private static long currentTime = System.currentTimeMillis();
    private static long startTime;
    private static long delay = 3000;

    private static float posX = -1;
    private static float posY = -1;

    public static void add(String message){
        if(notifactions.size() <= 0){
            startTime = System.currentTimeMillis();
        }
        notifactions.add(message);

        if(posX <= 0 || posY <= 0){
            Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

            int taskBarHeight = scrnSize.height - winSize.height;

            posX = scrnSize.width - 350;
            posY = scrnSize.height - taskBarHeight - 100;
        }
    }

    public static void show(){
        currentTime = System.currentTimeMillis();

        ImGui.setNextWindowPos(posX, posY, ImGuiCond.Always);
        ImGui.setNextWindowSize(350, 100, ImGuiCond.Always);
        if(notifactions.size() > 0) {
            if (ImGui.begin("NotificationPopup", NoMove | NoResize | NoTitleBar | NoDocking | NoCollapse | NoNav | NoInputs)) {

                ImGui.text("Message");
                ImGui.separator();

                ImGui.text(notifactions.get(0));
            }
            ImGui.end();

            if(startTime + delay < currentTime){
                notifactions.remove(0);
                startTime = System.currentTimeMillis();
            }
        }
    }
}
