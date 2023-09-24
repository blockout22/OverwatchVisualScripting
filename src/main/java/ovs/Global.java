package ovs;

import imgui.ImVec2;
import ovs.graph.node.Node;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Global {

    protected static int BUILD = -1;
    protected static int LATEST_BUILD = -1;
    protected static boolean devMode = false;
    public static String SCRIPTS_DIR = "Scripts";
    public static String NODE_GROUP_DIR = "NodeGroups";

    private static ImVec2 textSize = new ImVec2();

    public static ArrayList<String> maps = new ArrayList<>();
    public static ArrayList<String> heroes = new ArrayList<>();
    public static ArrayList<String> buttons = new ArrayList<>();
    public static ArrayList<String> effectType = new ArrayList<>();
    public static ArrayList<String> playEffectType = new ArrayList<>();
    public static ArrayList<String> gameModes = new ArrayList<>();
    public static ArrayList<String> status = new ArrayList<>();
    public static ArrayList<String> healthType = new ArrayList<>();
    public static ArrayList<String> icons = new ArrayList<>();
    public static ArrayList<String> communicationLines = new ArrayList<>();
    public static ArrayList<String> barriersLOS = new ArrayList<>();
    public static ArrayList<String> stats = new ArrayList<>();
    public static ArrayList<String> LOSCheck = new ArrayList<>();

    public static ArrayList<String> colors = new ArrayList<>();

    private static Map<String, Object> storage = new HashMap<>();

    static {
        loadFromJarIntoList(heroes, "heroes.txt");
        loadFromJarIntoList(maps, "maps.txt");
//        heroes.add("Ana");
//        heroes.add("Ashe");
//        heroes.add("Baptiste");
//        heroes.add("Bastion");
//        heroes.add("Brigitte");
//        heroes.add("Cassidy");
//        heroes.add("D.Va");
//        heroes.add("Doomfist");
//        heroes.add("Echo");
//        heroes.add("Genji");
//        heroes.add("Hanzo");
//        heroes.add("Illari");
//        heroes.add("Junker Queen");
//        heroes.add("Junkrat");
//        heroes.add("Kiriko");
//        heroes.add("Lifeweaver");
//        heroes.add("Lúcio");
//        heroes.add("Mei");
//        heroes.add("Mercy");
//        heroes.add("Moira");
//        heroes.add("Orisa");
//        heroes.add("Pharah");
//        heroes.add("Ramattra");
//        heroes.add("Reaper");
//        heroes.add("Reinhardt");
//        heroes.add("Roadhog");
//        heroes.add("Sigma");
//        heroes.add("Sojourn");
//        heroes.add("Soldier: 76");
//        heroes.add("Sombra");
//        heroes.add("Symmetra");
//        heroes.add("Torbjörn");
//        heroes.add("Tracer");
//        heroes.add("Widowmaker");
//        heroes.add("Winston");
//        heroes.add("Wrecking Ball");
//        heroes.add("Zarya");
//        heroes.add("Zenyatta");

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

        playEffectType.add("Good Explosion");
        playEffectType.add("Bad Explosion");
        playEffectType.add("Ring Explosion");
        playEffectType.add("Good Pickup Effect");
        playEffectType.add("Bad Pickup Effect");
        playEffectType.add("Debuff Impact Sound");
        playEffectType.add("Buff Impact Sound");
        playEffectType.add("Ring Explosion Sound");
        playEffectType.add("Buff Explosion Sound");
        playEffectType.add("Explosion Sound");

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

        gameModes.add("Assault");
        gameModes.add("Bounty Hunter");
        gameModes.add("Control ");
        gameModes.add("Capture The Flag");
        gameModes.add("Elimination");
        gameModes.add("Escort");
        gameModes.add("Deathmatch");
        gameModes.add("Hybrid");
        gameModes.add("Practice Range");
        gameModes.add("Push");
        gameModes.add("Skirmish");
        gameModes.add("Team Deathmatch");

        status.add("Asleep");
        status.add("Burning");
        status.add("Frozen");
        status.add("Hacked");
        status.add("Invisible");
        status.add("Knocked Down");
        status.add("Phased Out");
        status.add("Rooted");
        status.add("Stunned");
        status.add("Unkillable");

        healthType.add("Health");
        healthType.add("Armor");
        healthType.add("Shields");

        icons.add("Arrow: Down");
        icons.add("Arrow: Left");
        icons.add("Arrow: Right");
        icons.add("Arrow: Up");
        icons.add("Asterisk");
        icons.add("Bolt");
        icons.add("Checkmark");
        icons.add("Circle");
        icons.add("Club");
        icons.add("Diamond");
        icons.add("Dizzy");
        icons.add("Exclamation Mark");
        icons.add("Eye");
        icons.add("Fire");
        icons.add("Flag");
        icons.add("Halo");
        icons.add("Happy");
        icons.add("Heart");
        icons.add("Moon");
        icons.add("No");
        icons.add("Plus");
        icons.add("Poison");
        icons.add("Poison 2");
        icons.add("Question Mark");
        icons.add("Radioactive");
        icons.add("Recycle");
        icons.add("Ring Thick");
        icons.add("Ring Thin");
        icons.add("Sad");
        icons.add("Skull");
        icons.add("Spade");
        icons.add("Spiral");
        icons.add("Stop");
        icons.add("Trashcan");
        icons.add("Warning");
        icons.add("X");

        communicationLines.add("Acknowledge");
        communicationLines.add("Attacking");
        communicationLines.add("Countdown");
        communicationLines.add("Defending");
        communicationLines.add("Emote Down");
        communicationLines.add("Emote Left");
        communicationLines.add("Emote Right");
        communicationLines.add("Emote Up");
        communicationLines.add("Fall Back");
        communicationLines.add("Go");
        communicationLines.add("Going In");
        communicationLines.add("Goodbye");
        communicationLines.add("Group Up");
        communicationLines.add("Hello");
        communicationLines.add("Incoming");
        communicationLines.add("Need Healing");
        communicationLines.add("Need Help");
        communicationLines.add("No");
        communicationLines.add("On My Way");
        communicationLines.add("Press The Attack");
        communicationLines.add("Push Forward");
        communicationLines.add("Ready");
        communicationLines.add("Sorry");
        communicationLines.add("Spray Down");
        communicationLines.add("Spray Left");
        communicationLines.add("Spray Right");
        communicationLines.add("Spray Up");
        communicationLines.add("Thanks");
        communicationLines.add("Ultimate Status");
        communicationLines.add("Voice Line Down");
        communicationLines.add("Voice Line Left");
        communicationLines.add("Voice Line Right");
        communicationLines.add("Voice Line Up");
        communicationLines.add("With You");
        communicationLines.add("Yes");
        communicationLines.add("You Are Welcome");

        barriersLOS.add("Barriers Do Not Block Los");
        barriersLOS.add("All Barriers Block LOS");
        barriersLOS.add("Enemy Barriers Block LOS");

        stats.add("All Damage Dealt");
        stats.add("Barrier Damage Dealt");
        stats.add("Critical Hit Accuracy");
        stats.add("Critical Hits");
        stats.add("Damage Blocked");
        stats.add("Damage Taken");
        stats.add("Deaths");
        stats.add("Defensive Assists");
        stats.add("Eliminations");
        stats.add("Environmental Deaths");
        stats.add("Environmental Kills");
        stats.add("Final Blows");
        stats.add("Healing Dealt");
        stats.add("Healing Received");
        stats.add("Hero Damage Dealt");
        stats.add("Multikill Best");
        stats.add("Mulltikills");
        stats.add("Objective Kills");
        stats.add("Objective Assists");
        stats.add("Scoped Accuracy");
        stats.add("Scoped Critical Hit Accuracy");
        stats.add("Scoped Critical Hit Kills");
        stats.add("Scoped Critical Hits");
        stats.add("Scoped Hits");
        stats.add("Scoped Shots");
        stats.add("Self Healing");
        stats.add("Shots Fired");
        stats.add("Shots Hit");
        stats.add("Shots Missed");
        stats.add("Solo Kills");
        stats.add("Ultimates Earned");
        stats.add("Ultimates Used");
        stats.add("Weapon Accuracy");

        LOSCheck.add("Off");
        LOSCheck.add("Surfaces");
        LOSCheck.add("Surfaces And All Barriers");
        LOSCheck.add("Surfaces And Enemy Barriers");
    }

    public static void writeArrayToFile(ArrayList<String> arrayList, File file){
        try {
            PrintWriter pw = new PrintWriter(file);
            for (String s : arrayList) {
                pw.println(s);
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static StringBuilder readFromJar(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream stream = OwVS.class.getClassLoader().getResourceAsStream(fileName);

        BufferedReader br = new BufferedReader(new InputStreamReader(stream));

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        br.close();

        return sb;
    }

    public static void loadFromJarIntoList(ArrayList<String> list, String fileName){
        try {
            StringBuilder sb = Global.readFromJar(fileName);
            for(String s : sb.toString().split("\n")){
                if(s.length() > 0)
                    list.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    public static ArrayList<Class> findAllNodes(Class... ignoredAnnotations) throws Exception {
        String packageName = "ovs.graph.node";
        ArrayList<Class> nodeList = new ArrayList<>();

        InputStream stream;
        BufferedReader br;

        if(!Global.devMode){
            ArrayList<String> paths = clasesFromJar();
            for(String name : paths){
                String toStream = name + ".class";
                stream  = ClassLoader.getSystemClassLoader().getResourceAsStream(toStream);
                if(stream != null) {
                    try {
                        String className = toStream.replaceAll("/", "\\.").substring(0, toStream.lastIndexOf('.'));
                        Class node = Class.forName(className);
                        Class topSuperClass = getTopmostSuperclass(node, Node.class);
//                        if (node.getSuperclass().equals(Node.class)) {
                        boolean canAdd = true;
                        if (topSuperClass.equals(Node.class)) {
                            for(Class c : ignoredAnnotations){
                                if(node.getAnnotation(c) != null){
                                    canAdd = false;
                                }
                            }

                            if(canAdd){
                                nodeList.add(node);
                            }
//                            if (node.getAnnotation(NodeDisabled.class) == null) {
//                                nodeList.add(node);
//                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    System.out.println("Stream is null for: " + name);
                }
            }
        }else {

            stream = Global.class.getClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
            System.out.println(stream == null);
//        URL url = Global.class.getClassLoader().getResource("ovs/graph/node");
            br = new BufferedReader(new InputStreamReader(stream));

            String line;
            while ((line = br.readLine()) != null) {
                if (line.endsWith(".class") && !line.contains("$")) {
                    try {
                        Class node = Class.forName(packageName + "." + line.substring(0, line.lastIndexOf('.')));
                        Class topSuperClass = getTopmostSuperclass(node, Node.class);
//                        if (node.getSuperclass().equals(Node.class)) {
                        boolean canAdd = true;
                        if (topSuperClass.equals(Node.class)) {
                            for(Class c : ignoredAnnotations){
                                if(node.getAnnotation(c) != null){
                                    canAdd = false;
                                }
                            }

                            if(canAdd){
                                nodeList.add(node);
                            }
//                            if (node.getAnnotation(NodeDisabled.class) == null) {
//                                nodeList.add(node);
//                            }
                        }
                    } catch (Exception e) {

                    }
                }
            }
            br.close();
        }

        return nodeList;
    }

    public static Class getTopmostSuperclass(Class clazz, Class targetClass) {
        while (clazz.getSuperclass() != null && !clazz.getSuperclass().equals(targetClass)) {
            clazz = clazz.getSuperclass();
        }
        if (clazz.getSuperclass() != null) {
            clazz = clazz.getSuperclass();
        }
        return clazz;
    }



    private static ArrayList<String> clasesFromJar()
    {
        JarFile jf = null;
        try{
            ArrayList<String> paths = new ArrayList<>();
            String s = new File(Global.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            jf = new JarFile(s);
            Enumeration<JarEntry> entries = jf.entries();

            while(entries.hasMoreElements()){
                JarEntry je = entries.nextElement();
                if(je.getName().startsWith("ovs/graph/node/") && je.getName().endsWith(".class")){
                    paths.add(je.getName().split("\\.")[0]);
                }
            }

            return paths;
        }catch (IOException e) {
//            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {
            if(jf != null) {
                try {
                    jf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

        return null;
    }

    public static void createBackup(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try{
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while((length = is.read(buffer)) > 0){
                os.write(buffer, 0, length);
            }

        }finally {

        }
    }

    public static float lerpFloat(float start, float end, float t)
    {
        return start + (end - start) * t;
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
