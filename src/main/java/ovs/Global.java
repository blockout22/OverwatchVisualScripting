package ovs;

import imgui.ImVec2;
import ovs.graph.node.Node;
import ovs.graph.node.interfaces.NodeDisabled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Global {

    protected static int BUILD = -1;
    public static String SCRIPTS_DIR = "Scripts";

    private static ImVec2 textSize = new ImVec2();

    public static ArrayList<String> heroes = new ArrayList<>();
    public static ArrayList<String> buttons = new ArrayList<>();
    public static ArrayList<String> effectType = new ArrayList<>();

    public static ArrayList<String> colors = new ArrayList<>();

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

        effectType.add("Bad Aura");
        effectType.add("Bad Aura Sound");
        effectType.add("Beacon Sound");
        effectType.add("Cloud");
        effectType.add("Decal Sound");
        effectType.add("Energy Sound");
        effectType.add("Good Aura");
        effectType.add("Good Aura Sound");
        effectType.add("Light Shaft");
        effectType.add("Orb");
        effectType.add("Pick-up Sound");
        effectType.add("Ring");
        effectType.add("Smoke Sound");
        effectType.add("Sparkles");
        effectType.add("Sparkles Sound");
        effectType.add("Sphere");
        effectType.add("Echo Focusing Beam Sound");
        effectType.add("Junkrat Trap Chain Sound");
        effectType.add("Mercy Heal Beam Sound");
        effectType.add("Mercy Boost Beam Sound");
        effectType.add("Moira Grasp Connected Sound");
        effectType.add("Moira Orb Damage Sound");
        effectType.add("Moira Orb Heal Sound");
        effectType.add("Moira Coalescence Sound");
        effectType.add("Orisa Amplifier Sound");
        effectType.add("Orisa Halt Tendril Sound");
        effectType.add("Symmetra Projector Sound");
        effectType.add("Winston Tesla Cannon Sound");
        effectType.add("Zarya Particle Beam Sound");
        effectType.add("Omnic Slicer Beam Sound");
        effectType.add("Ana Nano Boosted Sound");
        effectType.add("Baptiste Immortality Field Projected Sound");
        effectType.add("Echo Cloning Sound");
        effectType.add("Lúcio Sound Barrier Protected Sound");
        effectType.add("Mei Frozen Sound");
        effectType.add("Mercy Damage Boosted Sound");
        effectType.add("Sigma Grvitic Flux Target Sound");
        effectType.add("Sombra Hacking Sound");
        effectType.add("Sombra Hacked Sound");
        effectType.add("Torbjörn Overloading Sound");
        effectType.add("Widowmaker Venom Mine Target Sound");
        effectType.add("Winston Tesla Cannon Target Sound");
        effectType.add("Winstron Primal Rage Sound");
        effectType.add("Wrecking Ball Adaptive Sheild Target Sound");
        effectType.add("Wrecking Ball Piledriver Fire Sound");
        effectType.add("Zenyatta Orb Of Discord Target Sound");
        effectType.add("Heal Target Active Effect");
        effectType.add("Heal Target Effect");
        effectType.add("Ana Biotic Grenade Increased Healing Effect");
        effectType.add("Ana Nano Boosted Effect");
        effectType.add("Baptiste Immortality Field Protected Effect");
        effectType.add("Echo Cloning Effect");
        effectType.add("Lúcio Sound Barrier Protected Effect");
        effectType.add("Mercy Damage Boosted Effect");
        effectType.add("Reaper Wraith Form Effect");
        effectType.add("Soldier: 76 Sprinting Effect");
        effectType.add("Torbjörn Overloading Effect");
        effectType.add("Winston Primal Rage Effect");
        effectType.add("Wrecking Ball Adaptive Shield Target Effect");
        effectType.add("Wrecking Ball Piledriver Fire Effect");
        effectType.add("Ana Biotic Grenade No Healing Effect");
        effectType.add("Ana Sleeping Effect");
        effectType.add("Ashe Dynamite Burning Particle Effect");
        effectType.add("Ashe Dynamite Burning Material Effect");
        effectType.add("Cassidy Flashbang Stunned Effect");
        effectType.add("Mei Frozen Effect");
        effectType.add("Sigma Gravitic Flux Target Effect");
        effectType.add("Sombra Hacked Looping Effect");
        effectType.add("Widowmaker Venom Mine Target Effect");
        effectType.add("Winston Tesla Cannon Target Effect");
        effectType.add("Zenyatta Orb Of Discord Target Effect");

        colors.add("White");
        colors.add("Aqua");
        colors.add("Black");
        colors.add("Blue");
        colors.add("Gray");
        colors.add("Green");
        colors.add("Lime Green");
        colors.add("Orange");
        colors.add("Purple");
        colors.add("Red");
        colors.add("Rose");
        colors.add("Sky Blue");
        colors.add("Team 1");
        colors.add("Team 2");
        colors.add("Turquoise");
        colors.add("Violet");
        colors.add("Yellow");
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

    public static float map(float value, float min1, float max1, float min2, float max2) {
        return min2 + (value - min1) * (max2 - min2) / (max1 - min1);
    }


    public static ArrayList<Class> findAllNodes() throws IOException {
        String packageName = "ovs.graph.node";
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));

        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        ArrayList<Class> nodeList = new ArrayList<>();


        String line;
        while((line = br.readLine()) != null){
            if(line.endsWith(".class") && !line.contains("$")){
                try {
                    Class node = Class.forName(packageName + "." + line.substring(0, line.lastIndexOf('.')));
                    if(node.getSuperclass().equals(Node.class)){
                        if(node.getAnnotation(NodeDisabled.class) == null){
                            nodeList.add(node);
                        }
                    }
                }catch (Exception e){

                }
            }
        }
        br.close();

        return nodeList;
    }

    public static int getBuild(){
        return BUILD;
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
