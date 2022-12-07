package ovs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Global {

    public static String SCRIPTS_DIR = "Scripts";

    public static ArrayList<String> heroes = new ArrayList<>();

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
}
