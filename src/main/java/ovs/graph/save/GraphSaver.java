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

    public void save(String fileName, Settings settings, Graph graph){
        validateDirExists(dir);
        validateDirExists(dir + File.separator + fileName);

        File file = new File(dir + File.separator + fileName + File.separator + "script.json");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        graphSave.nodeSaves.clear();
        graphSave.globalVariables.clear();
        graphSave.playerVariables.clear();

        graphSave.saveSettings.modeName = settings.getModeName();
        graphSave.saveSettings.description = settings.getDescription();
        graphSave.saveSettings.maxT1Players = settings.getMaxT1Players();
        graphSave.saveSettings.maxT2Players = settings.getMaxT2Players();
        graphSave.saveSettings.maxFFAPlayers = settings.getMaxFFAPlayers();

        graphSave.saveSettings.assaultMode = settings.getAssaultOnOff();
        graphSave.saveSettings.controlMode = settings.getControlOnOff();
        graphSave.saveSettings.escortMode = settings.getEscortOnOff();
        graphSave.saveSettings.hybridMode = settings.getHybridOnOff();
        graphSave.saveSettings.pushMode = settings.getPushOnOff();
        graphSave.saveSettings.bountyHunterMode = settings.getBountyHunterOnOff();
        graphSave.saveSettings.ctfMode = settings.getCtfOnOff();
        graphSave.saveSettings.deathmatchMode = settings.getDeathmatchOnOff();
        graphSave.saveSettings.eliminationMode = settings.getEliminationOnOff();
        graphSave.saveSettings.teamDeathmatchMode = settings.getTeamDeathmatchOnOff();
        graphSave.saveSettings.practiceRangeMode = settings.getPracticeRangeOnOff();
        graphSave.saveSettings.skirmishMode = settings.getSkirmishOnOff();

        for(Variable var : graph.globalVariables.getList()){
            graphSave.globalVariables.add(var.ID + ":" + var.name);
        }

        for(Variable var : graph.playerVariables.getList()){
            graphSave.playerVariables.add(var.ID + ":" + var.name);
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
                pinSave.connectedTo = inputs.connectedTo;
                pinSave.canDelete = inputs.isCanDelete();

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

                pinSave.connectedTo = outputs.connectedTo;

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
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return;
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
            settings.setModeName(gs.saveSettings.modeName);
            settings.setDescription(gs.saveSettings.description);
            settings.setMaxT1Players(gs.saveSettings.maxT1Players);
            settings.setMaxT2Players(gs.saveSettings.maxT2Players);
            settings.setMaxFFAPlayers(gs.saveSettings.maxFFAPlayers);
            settings.setAssaultOnOff(gs.saveSettings.assaultMode);
            settings.setControlOnOff(gs.saveSettings.controlMode);
            settings.setEscortOnOff(gs.saveSettings.escortMode);
            settings.setHybridOnOff(gs.saveSettings.hybridMode);
            settings.setPushOnOff(gs.saveSettings.pushMode);
            settings.setBountyHunterOnOff(gs.saveSettings.bountyHunterMode);
            settings.setCtfOnOff(gs.saveSettings.ctfMode);
            settings.setDeathmatchOnOff(gs.saveSettings.deathmatchMode);
            settings.setEliminationOnOff(gs.saveSettings.eliminationMode);
            settings.setTeamDeathmatchOnOff(gs.saveSettings.teamDeathmatchMode);
            settings.setPracticeRangeOnOff(gs.saveSettings.practiceRangeMode);
            settings.setSkirmishOnOff(gs.saveSettings.skirmishMode);

            Graph graph = new Graph();

            for(String var : gs.globalVariables){
                String[] split = var.split(":");
                Variable variable = new Variable();
                variable.type = Variable.Type.GLOBAL;
                variable.ID = Integer.valueOf(split[0]);
                variable.name = split[1];
                graph.globalVariables.add(variable);
            }

            for(String var : gs.playerVariables){
                String[] split = var.split(":");
                Variable variable = new Variable();
                variable.type = Variable.Type.PLAYER;
                variable.ID = Integer.valueOf(split[0]);
                variable.name = split[1];
                graph.playerVariables.add(variable);
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
                    System.out.println("Class was null, couldn't load");
                    return null;
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
                            node.inputPins.add(pin);
                        }

                        node.inputPins.get(j).setID(save.inputPins.get(j).ID);

                        //Load any custom values set to pins that haven't got a connection
                        //
                        if(node.inputPins.get(j).getData() != null){
                            node.inputPins.get(j).loadValue(save.inputPins.get(j).value);
                        }

                        //check and set the pin ID which this Pin is connected to
                        if(save.inputPins.get(j).connectedTo != -1){
                            node.inputPins.get(j).connectedTo = save.inputPins.get(j).connectedTo;
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
                            node.outputPins.add(pin);
                        }

                        node.outputPins.get(j).setID(save.outputPins.get(j).ID);
                        if(save.outputPins.get(j).connectedTo != -1){
                            node.outputPins.get(j).connectedTo = save.outputPins.get(j).connectedTo;
                        }
                    }
                }

                node.onLoaded();
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
    }

    private static class PinSave{
        private Integer ID;
        private String type;
        private String value;
        private Integer connectedTo;
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
