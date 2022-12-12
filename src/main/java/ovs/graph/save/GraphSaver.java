package ovs.graph.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditor;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.Settings;
import ovs.graph.Variable;
import ovs.graph.node.Node;
import ovs.graph.pin.Pin;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GraphSaver {

    private transient GraphSave graphSave = new GraphSave();
    private static StringBuilder sb = new StringBuilder();

    String dir = Global.SCRIPTS_DIR;

    public boolean save(String fileName, Settings settings, Graph graph){
        validateDirExists(dir);
        validateDirExists(dir + File.separator + fileName);

        File file = new File(dir + File.separator + fileName + File.separator + "script.json");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        graphSave.nodeSaves.clear();
        graphSave.globalVariables.clear();
        graphSave.playerVariables.clear();
        graphSave.subroutines.clear();
        graphSave.saveSettings.extensionToggle.clear();
        graphSave.saveSettings.assaultSettings.MapToggle.clear();
        graphSave.saveSettings.controlSettings.MapToggle.clear();
        graphSave.saveSettings.escortSettings.MapToggle.clear();
        graphSave.saveSettings.hybridSettings.MapToggle.clear();
        graphSave.saveSettings.pushSettings.MapToggle.clear();
        graphSave.saveSettings.dmSettings.MapToggle.clear();

        graphSave.saveSettings.modeName = settings.modeName.get();
        graphSave.saveSettings.description = settings.description.get();
        graphSave.saveSettings.maxT1Players = settings.maxT1Players[0];
        graphSave.saveSettings.maxT2Players = settings.maxT2Players[0];
        graphSave.saveSettings.maxFFAPlayers = settings.maxFFAPlayers[0];

        graphSave.saveSettings.assaultMode = settings.assaultOnOff.get();
        graphSave.saveSettings.controlMode = settings.controlOnOff.get();
        graphSave.saveSettings.escortMode = settings.escortOnOff.get();
        graphSave.saveSettings.hybridMode = settings.hybridOnOff.get();
        graphSave.saveSettings.pushMode = settings.pushOnOff.get();
        graphSave.saveSettings.bountyHunterMode = settings.bountyHunterOnOff.get();
        graphSave.saveSettings.ctfMode = settings.ctfOnOff.get();
        graphSave.saveSettings.deathmatchMode = settings.deathmatchOnOff.get();
        graphSave.saveSettings.eliminationMode = settings.eliminationOnOff.get();
        graphSave.saveSettings.teamDeathmatchMode = settings.teamDeathmatchOnOff.get();
        graphSave.saveSettings.practiceRangeMode = settings.practiceRangeOnOff.get();
        graphSave.saveSettings.skirmishMode = settings.skirmishOnOff.get();

        for (int i = 0; i < settings.extensionBools.size(); i++) {
            Settings.BoolInfoWithName info = settings.extensionBools.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.extensionToggle.add(saveInfo);
        }

        //TODO save other game mode settings

        //Assault Options
        graphSave.saveSettings.assaultSettings.speedModifier = settings.assaultSpeedModifier[0];
        graphSave.saveSettings.assaultSettings.compRules = settings.assaultCompRulesOnOff.get();
        for (int i = 0; i < settings.assaultMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.assaultMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.assaultSettings.MapToggle.add(saveInfo);
        }

        //Control Options
        graphSave.saveSettings.controlSettings.speedModifier = settings.controlSpeedModifier[0];
        graphSave.saveSettings.controlSettings.compRules = settings.controlCompetitiveRules.get();
        graphSave.saveSettings.controlSettings.validPoints = settings.controlValidControlPointsSelection.get();
        graphSave.saveSettings.controlSettings.scoreToWin = settings.controlScoreToWin[0];
        graphSave.saveSettings.controlSettings.scoringSpeedModifier = settings.controlSpeedModifier[0];
        for (int i = 0; i < settings.controlMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.controlMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.controlSettings.MapToggle.add(saveInfo);
        }

        //Escort Options
        graphSave.saveSettings.escortSettings.compRules = settings.escortCompRulesOnOff.get();
        graphSave.saveSettings.escortSettings.speedModifier = settings.escortSpeedModifier[0];
        for (int i = 0; i < settings.escortMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.escortMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.escortSettings.MapToggle.add(saveInfo);
        }

        //Hybrid Options
        graphSave.saveSettings.hybridSettings.speedModifier = settings.hybridSpeedModifier[0];
        graphSave.saveSettings.hybridSettings.compRules = settings.hybridCompRulesOnOff.get();
        graphSave.saveSettings.hybridSettings.payloadSpeedModifier = settings.hybridPayloadSpeedModifier[0];
        for (int i = 0; i < settings.hybridMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.hybridMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.hybridSettings.MapToggle.add(saveInfo);
        }

        //Push Options
        graphSave.saveSettings.pushSettings.compRules = settings.pushCompRulesOnOff.get();
        graphSave.saveSettings.pushSettings.pushSpeedModifier = settings.pushPushSpeedModifier[0];
        graphSave.saveSettings.pushSettings.walkSpeedModifier = settings.pushWalkSpeedModifier[0];
        for (int i = 0; i < settings.pushMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.pushMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.pushSettings.MapToggle.add(saveInfo);
        }

        //DeathMatch Options
        graphSave.saveSettings.dmSettings.GameLenthDM = settings.gameTimeDM[0];
        graphSave.saveSettings.dmSettings.scoreToWinDM = settings.scoreToWinDM[0];
        graphSave.saveSettings.dmSettings.initRespawnDM = settings.initRespawnOnOffDM.get();
        for (int i = 0; i < settings.dmMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.dmMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.dmSettings.MapToggle.add(saveInfo);
        }


//        for(Variable var : graph.globalVariables.getList()){
        for (int i = 0; i < graph.globalVariables.size(); i++){
//            graphSave.globalVariables.add(var.ID + ":" + var.name);
            Variable var = graph.globalVariables.get(i);
//            graphSave.globalVariables.add(var.ID + ":" + var.name);
            graphSave.globalVariables.add(i + ":" + var.name);
        }

//        for(Variable var : graph.playerVariables.getList()){
        for (int i = 0; i < graph.playerVariables.size(); i++){
            Variable var = graph.playerVariables.get(i);
//            graphSave.playerVariables.add(var.ID + ":" + var.name);
            graphSave.playerVariables.add(i + ":" + var.name);
        }

        for (int i = 0; i < graph.subroutines.size(); i++) {
            graphSave.subroutines.add(i + ":" + graph.subroutines.get(i));
        }


        for(Node node : graph.getNodes().values()){
            String className = node.getClass().getName();
            //get the nodes position in the graph
            ImVec2 pos = new ImVec2();
            NodeEditor.getNodePosition(node.getID(), pos);

            NodeSave save = new NodeSave();
            save.className = className;
            save.nodeName = node.getName();
            save.x = pos.x;
            save.y = pos.y;

            for(String extraData : node.getExtraSaveData()){
                save.extraData.add(extraData);
            }

            for(Pin inputs : node.inputPins){
                PinSave pinSave = new PinSave();
                pinSave.ID = inputs.getID();
                pinSave.type = inputs.getClass().getName();
//                pinSave.connectedTo = inputs.connectedTo;
                pinSave.canDelete = inputs.isCanDelete();

                pinSave.connections.clear();

                for (int i = 0; i < inputs.connectedToList.size(); i++) {
                    pinSave.connections.add(inputs.connectedToList.get(i));
                }

                if(inputs.getData() != null){
                    if(inputs.getData().getValue() != null){
                        pinSave.value = inputs.getData().getValue().toString();
                    }
                }

                save.inputPins.add(pinSave);
            }

            for(Pin outputs : node.outputPins){
                PinSave pinSave = new PinSave();
                pinSave.ID = outputs.getID();
                pinSave.type = outputs.getClass().getName();
                pinSave.canDelete = outputs.isCanDelete();
//                pinSave.connectedTo = outputs.connectedTo;

                pinSave.connections.clear();
                for (int i = 0; i < outputs.connectedToList.size(); i++) {
                    pinSave.connections.add(outputs.connectedToList.get(i));
                }
                save.outputPins.add(pinSave);
            }

            graphSave.nodeSaves.add(save);
        }

        try{
            Gson json = new GsonBuilder().setPrettyPrinting().create();
            String output = json.toJson(graphSave);

            PrintWriter pw = new PrintWriter(file);
            pw.write(output);
            pw.flush();
            pw.close();
            return true;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }
    }

    private void validateDirExists(String directory) {
        File file = new File(directory);
        if(!file.exists()){
            file.mkdir();
        }
    }

    public Graph load(String fileName, Settings settings){
        try{
            File file = new File(dir + File.separator + fileName + File.separator + "script.json");
            BufferedReader br = new BufferedReader(new FileReader(file));

            Gson json = new GsonBuilder().setPrettyPrinting().create();

            GraphSave gs = json.fromJson(br, GraphSave.class);

            br.close();

            Node[] loaded = new Node[gs.nodeSaves.size()];

            //Load settings
            settings.modeName.set(gs.saveSettings.modeName);
            settings.description.set(gs.saveSettings.description);
            settings.maxT1Players[0] = (gs.saveSettings.maxT1Players);
            settings.maxT2Players[0] = (gs.saveSettings.maxT2Players);
            settings.maxFFAPlayers[0] = (gs.saveSettings.maxFFAPlayers);
            settings.assaultOnOff.set(gs.saveSettings.assaultMode);
            settings.controlOnOff.set(gs.saveSettings.controlMode);
            settings.escortOnOff.set(gs.saveSettings.escortMode);
            settings.hybridOnOff.set(gs.saveSettings.hybridMode);
            settings.pushOnOff.set(gs.saveSettings.pushMode);
            settings.bountyHunterOnOff.set(gs.saveSettings.bountyHunterMode);
            settings.ctfOnOff.set(gs.saveSettings.ctfMode);
            settings.deathmatchOnOff.set(gs.saveSettings.deathmatchMode);
            settings.eliminationOnOff.set(gs.saveSettings.eliminationMode);
            settings.teamDeathmatchOnOff.set(gs.saveSettings.teamDeathmatchMode);
            settings.practiceRangeOnOff.set(gs.saveSettings.practiceRangeMode);
            settings.skirmishOnOff.set(gs.saveSettings.skirmishMode);

            for (int i = 0; i < gs.saveSettings.extensionToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.extensionToggle.get(i);

                for (int j = 0; j < settings.extensionBools.size(); j++) {
                    Settings.BoolInfoWithName extInfo = settings.extensionBools.get(j);
                    if(info.name.equals(extInfo.name)){
                        extInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Assault Options
            settings.assaultSpeedModifier[0] = gs.saveSettings.assaultSettings.speedModifier;
            settings.assaultCompRulesOnOff.set(gs.saveSettings.assaultSettings.compRules);
            for (int i = 0; i < gs.saveSettings.assaultSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.assaultSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.assaultMap.size(); j++) {
                    Settings.BoolInfoWithName mapInfo = settings.assaultMap.get(j);
                    if(info.name.equals(mapInfo.name)){
                        mapInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Control Options
            settings.controlSpeedModifier[0] = gs.saveSettings.controlSettings.speedModifier;
            settings.controlCompetitiveRules.set(gs.saveSettings.controlSettings.compRules);
            settings.controlValidControlPointsSelection.set(gs.saveSettings.controlSettings.validPoints);
            settings.controlScoreToWin[0] = gs.saveSettings.controlSettings.scoreToWin;
            settings.controlScoringSpeedModifier[0] = gs.saveSettings.controlSettings.scoringSpeedModifier;
            for (int i = 0; i < gs.saveSettings.controlSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.controlSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.controlMap.size(); j++) {
                    Settings.BoolInfoWithName mapInfo = settings.controlMap.get(j);
                    if(info.name.equals(mapInfo.name)){
                        mapInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Escort Options
            settings.escortCompRulesOnOff.set(gs.saveSettings.escortSettings.compRules);
            settings.escortSpeedModifier[0] = gs.saveSettings.escortSettings.speedModifier;
            for (int i = 0; i < gs.saveSettings.escortSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.escortSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.escortMap.size(); j++) {
                    Settings.BoolInfoWithName mapInfo = settings.escortMap.get(j);
                    if(info.name.equals(mapInfo.name)){
                        mapInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Hybrid Options
            settings.hybridSpeedModifier[0] = gs.saveSettings.hybridSettings.speedModifier;
            settings.hybridCompRulesOnOff.set(gs.saveSettings.hybridSettings.compRules);
            settings.hybridPayloadSpeedModifier[0] = gs.saveSettings.hybridSettings.payloadSpeedModifier;
            for (int i = 0; i < gs.saveSettings.hybridSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.hybridSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.hybridMap.size(); j++) {
                    Settings.BoolInfoWithName mapInfo = settings.hybridMap.get(j);
                    if(info.name.equals(mapInfo.name)){
                        mapInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Push Options
            settings.pushCompRulesOnOff.set(gs.saveSettings.pushSettings.compRules);
            settings.pushPushSpeedModifier[0] = gs.saveSettings.pushSettings.pushSpeedModifier;
            settings.pushWalkSpeedModifier[0] = gs.saveSettings.pushSettings.walkSpeedModifier;
            for (int i = 0; i < gs.saveSettings.pushSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.pushSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.pushMap.size(); j++) {
                    Settings.BoolInfoWithName mapInfo = settings.pushMap.get(j);
                    if(info.name.equals(mapInfo.name)){
                        mapInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //DeathMatch Options
            settings.gameTimeDM[0] = (gs.saveSettings.dmSettings.GameLenthDM);
            settings.scoreToWinDM[0] = (gs.saveSettings.dmSettings.scoreToWinDM);
            settings.initRespawnOnOffDM.set(gs.saveSettings.dmSettings.initRespawnDM);
            for (int i = 0; i < gs.saveSettings.dmSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.dmSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.dmMap.size(); j++) {
                    Settings.BoolInfoWithName dmInfo = settings.dmMap.get(j);
                    if(info.name.equals(dmInfo.name)){
                        dmInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            Graph graph = new Graph();

            for(String var : gs.globalVariables){
                String[] split = var.split(":");
                Variable variable = new Variable();
                variable.type = Variable.Type.GLOBAL;
//                variable.ID = Integer.valueOf(split[0]);
                variable.name = split[1];
                graph.globalVariables.add(variable);
            }

            for(String var : gs.playerVariables){
                String[] split = var.split(":");
                Variable variable = new Variable();
                variable.type = Variable.Type.PLAYER;
//                variable.ID = Integer.valueOf(split[0]);
                variable.name = split[1];
                graph.playerVariables.add(variable);
            }

            for (String sub : gs.subroutines){
                String[] split = sub.split(":");
                graph.subroutines.add(split[1]);
            }

            for (int i = 0; i < gs.nodeSaves.size(); i++) {
                NodeSave save = gs.nodeSaves.get(i);
                Class classNode = null;

                try{
                    ClassLoader loader = GraphSaver.class.getClassLoader();
                    classNode = Class.forName(save.className, true, loader);
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(classNode == null){
                    System.out.println("Class was null, couldn't load: " + save.className);
//                    return null;
                    continue;
                }

                Node node = (Node) classNode.getDeclaredConstructor(Graph.class).newInstance(graph);
                node.setName(save.nodeName);
                graph.addNode(node);
                node.posX = save.x;
                node.posY = save.y;

                for(String extraData : save.extraData){
                    node.getExtraSaveData().add(extraData);
                }

                loaded[i] = node;
            }

            for (int i = 0; i < loaded.length; i++) {
                Node node = loaded[i];

                if(node != null){
                    NodeSave save = gs.nodeSaves.get(i);

                    //load input pins
                    for (int j = 0; j < save.inputPins.size(); j++) {
                        if(j >= node.inputPins.size()){
                            Class classNode = null;

                            ClassLoader loader = GraphSaver.class.getClassLoader();
                            classNode = Class.forName(save.inputPins.get(j).type, true, loader);

                            if(classNode == null){
                                System.out.println("Class " + save.inputPins.get(j).type + " was null, couldn't load");
                                return null;
                            }

//                            int id = Graph.getNextAvailablePinID();
                            Pin pin = (Pin) classNode.getDeclaredConstructor().newInstance();
                            pin.setNode(node);
                            pin.setCanDelete(save.inputPins.get(j).canDelete);

//                            pin.setCanDelete(true);
                            pin.setPinType(Pin.PinType.Input);
                            node.inputPins.add(pin);
                        }

                        node.inputPins.get(j).setID(save.inputPins.get(j).ID);

                        //Load any custom values set to pins that haven't got a connection
                        //
                        if(node.inputPins.get(j).getData() != null){
                            node.inputPins.get(j).loadValue(save.inputPins.get(j).value);
                        }
                    }

                    //load output pins
                    for (int j = 0; j < save.outputPins.size(); j++) {
                        if(j >= node.outputPins.size())
                        {
                            Class classNode = null;

                            ClassLoader loader = GraphSaver.class.getClassLoader();
                            classNode = Class.forName(save.outputPins.get(j).type, true, loader);

                            if(classNode == null){
                                System.out.println("Class " + save.inputPins.get(j).type + " was null, couldn't load");
                                return null;
                            }

                            Pin pin = (Pin) classNode.getDeclaredConstructor().newInstance();
                            pin.setNode(node);
                            pin.setCanDelete(save.outputPins.get(i).canDelete);

                            pin.setCanDelete(true);
                            pin.setPinType(Pin.PinType.Output);
                            node.outputPins.add(pin);
                        }

                        node.outputPins.get(j).setID(save.outputPins.get(j).ID);

                    }
                    node.onLoaded();

                    // Load and setup pin connections
                    for (int j = 0; j < save.inputPins.size(); j++)
                    {
                        //check and set the pin ID which this Pin is connected to
//                        int ID = save.inputPins.get(j).connectedTo;
//                        node.inputPins.get(j).connectedTo = ID;

                        for (int k = 0; k < save.inputPins.get(j).connections.size(); k++) {
                            int ID = save.inputPins.get(j).connections.get(k);
                            node.inputPins.get(j).connectedToList.add(ID);
                        }
//                        System.out.println(node.inputPins.get(j).connectedTo);
//                        System.out.println(node.inputPins.get(j).isConnected());
//                        System.out.println(node.inputPins.get(j).getConnectedPin());
//
//                        if(node.inputPins.get(j).getConnectedPin() == null){
//                            node.inputPins.get(j).connectedTo = -1;
//                        }
                    }

                    for (int j = 0; j < save.outputPins.size(); j++)
                    {
//                        if(save.outputPins.get(j).connectedTo != -1){
//                            if(node.outputPins.get(j).getNode().getGraph().findPinById(save.outputPins.get(j).connectedTo) != null)
                            {
//                                node.outputPins.get(j).connectedTo = save.outputPins.get(j).connectedTo;

                                for (int k = 0; k < save.outputPins.get(j).connections.size(); k++) {
                                    int ID = save.outputPins.get(j).connections.get(k);
                                    node.outputPins.get(j).connectedToList.add(ID);
                                }
//                                System.out.println(node.outputPins.get(j).connectedTo);
//                                System.out.println(node.outputPins.get(j).isConnected());
//                                System.out.println(node.outputPins.get(j).getConnectedPin());
//
//                                if(node.outputPins.get(j).getConnectedPin() == null){
//                                    node.outputPins.get(j).connectedTo = -1;
//                                }
                            }
//                        }
                    }

                }

            }

            return graph;

        }catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class GraphSave{
        //Settings
        private SaveSettings saveSettings = new SaveSettings();
        private ArrayList<String> globalVariables = new ArrayList<>();
        private ArrayList<String> playerVariables = new ArrayList<>();
        private ArrayList<String> subroutines = new ArrayList<>();
        private ArrayList<NodeSave> nodeSaves = new ArrayList<>();
    }

    private static class SaveSettings{
        private String modeName = "";
        private String description = "";

        private int maxT1Players = 5;
        private int maxT2Players = 5;
        private int maxFFAPlayers = 2;

        private boolean assaultMode;
        private boolean controlMode = true;
        private boolean escortMode = true;
        private boolean hybridMode = true;
        private boolean pushMode = true;
        private boolean bountyHunterMode;
        private boolean ctfMode;
        private boolean deathmatchMode;
        private boolean eliminationMode;
        private boolean teamDeathmatchMode;
        private boolean practiceRangeMode;
        private boolean skirmishMode;

        private ArrayList<BoolInfo> extensionToggle = new ArrayList<>();

        private AssaultSettings assaultSettings = new AssaultSettings();
        private ControlSettings controlSettings = new ControlSettings();
        private EscortSettings escortSettings = new EscortSettings();
        private HybridSettings hybridSettings = new HybridSettings();
        private PushSettings pushSettings = new PushSettings();
        private DeatchMatchSettings dmSettings = new DeatchMatchSettings();
    }

    private static class AssaultSettings{
        private int speedModifier = 100;
        private boolean compRules = false;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class ControlSettings{
        private int speedModifier = 100;
        private boolean compRules = false;
        private int validPoints = 0;
        private int scoreToWin = 2;
        private int scoringSpeedModifier = 100;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class EscortSettings{
        private boolean compRules = false;
        private int speedModifier = 100;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class HybridSettings{
        private int speedModifier = 100;
        private boolean compRules = false;
        private int payloadSpeedModifier = 100;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class PushSettings{
        private boolean compRules = false;
        private int pushSpeedModifier = 100;
        private int walkSpeedModifier = 100;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class DeatchMatchSettings{
        //Deathmatch Options
        private int GameLenthDM = 10;
        private int scoreToWinDM = 20;
        private boolean initRespawnDM = true;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class BoolInfo{
        String name;
        boolean value;
    }

    private static class PinSave{
        private Integer ID;
        private String type;
        private String value;
//        private Integer connectedTo;
        private ArrayList<Integer> connections = new ArrayList<>();
        private boolean canDelete;
    }


    private static class NodeSave{
        private String className;
        private String nodeName;
        private float x;
        private float y;
        private ArrayList<String> extraData = new ArrayList<>();
        //save Pin array
        private ArrayList<PinSave> inputPins = new ArrayList<>();
        private ArrayList<PinSave> outputPins = new ArrayList<>();
//        private ArrayList<Integer> connectedToList = new ArrayList<>();

    }
}
