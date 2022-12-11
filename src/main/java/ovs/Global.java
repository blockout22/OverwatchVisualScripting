package ovs;

import imgui.ImGui;
import imgui.ImVec2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Global {

    public static String SCRIPTS_DIR = "Scripts";

    private static ImVec2 textSize = new ImVec2();

    public static ArrayList<String> heroes = new ArrayList<>();
    public static ArrayList<String> buttons = new ArrayList<>();

    private static Map<String, Object> storage = new HashMap<>();

    static {
        heroes.add("Ana");
        heroes.add("Ashe");
        heroes.add("Baptiste");
        heroes.add("Bastion");
        heroes.add("Brigitte");
        heroes.add("Doomfist");
        heroes.add("D.Va");
        heroes.add("Echo");
        heroes.add("Genji");
        heroes.add("Wrecking Ball");
        heroes.add("Hanzo");
        heroes.add("Junker Queen");
        heroes.add("Junkrat");
        heroes.add("Kiriko");
        heroes.add("Lúcio");
        heroes.add("Cassidy");
        heroes.add("Mei");
        heroes.add("Mercy");
        heroes.add("Moira");
        heroes.add("Orisa");
        heroes.add("Pharah");
        heroes.add("Ramattra");
        heroes.add("Reaper");
        heroes.add("Reinhardt");
        heroes.add("Roadhog");
        heroes.add("Sigma");
        heroes.add("Sojourn");
        heroes.add("Soldier: 76");
        heroes.add("Sombra");
        heroes.add("Symmetra");
        heroes.add("Torbjörn");
        heroes.add("Tracer");
        heroes.add("Windowmaker");
        heroes.add("Winston");
        heroes.add("Zarya");
        heroes.add("Zenyatta");

        buttons.add("Ability 1");
        buttons.add("Ability 2");
        buttons.add("Crouch");
        buttons.add("Interact");
        buttons.add("Jump");
        buttons.add("Melee");
        buttons.add("Primary Fire");
        buttons.add("Reload");
        buttons.add("Secondary Fire");
        buttons.add("Ultimate");
    }

    public static void setStorage(String key, Object obj){
        storage.put(key, obj);
    }

    public static Object getStorage(String key){
        Object value = storage.get(key);
        if(value == null){
            return 0;
        }
        return storage.get(key);
    }

    //TODO add a function to allow elements to align to the right side of the screen
//    public static void alignRight(String text){
//        ImGui.nextColumn();
//        ImGui.calcTextSize(textSize, text);
//        float posX = ImGui.getCursorPosX() - textSize.x - ImGui.getScrollX() - 2 * ImGui.getStyle().getItemSpacingX();
//        posX = posX * 0.5f;
////        float posX = (ImGui.getWindowSizeX() - textSize.x) * 0.5f;
//        if(posX > ImGui.getCursorPosX())
//        {
//            ImGui.setCursorPosX(posX);
//        }
//    }
}
