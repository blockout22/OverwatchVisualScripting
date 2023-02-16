package ovs.graph.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditor;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.node.Node;
import ovs.graph.pin.Pin;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GroupSaver {

    private transient GraphSave graphSave = new GraphSave();
    private static StringBuilder sb = new StringBuilder();
    String dir = Global.NODE_GROUP_DIR;

    public boolean save(String fileName, Graph graph){
        validateDirExists(dir);
        validateDirExists(dir + File.separator + fileName);

        File file = new File(dir + File.separator + fileName + File.separator + "group.json");
        File backup = new File(dir + File.separator + fileName + File.separator + "backup_group.json");
        try {
            Global.createBackup(file, backup);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
                return false;
            }
        }

        graphSave.nodeSaves.clear();

        for(Node node : graph.getNodes().values())
        {
            String className = node.getClass().getName();

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

    public Graph load(String fileName){
        try{
            File file = new File(dir + File.separator + fileName + File.separator + "group.json");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            String content = "";
            while((line = br.readLine()) != null){
                content += line;
            }

            br.close();

            return loadFromString(content);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Graph loadFromString(String content){
        try{
            Gson json = new GsonBuilder().setPrettyPrinting().create();

            GraphSave gs = json.fromJson(content, GraphSave.class);

            Node[] loaded = new Node[gs.nodeSaves.size()];

            Graph graph = new Graph();

            for (int i = 0; i < gs.nodeSaves.size(); i++) {
                NodeSave save = gs.nodeSaves.get(i);
                Class classNode = null;

                try{
                    ClassLoader loader = GroupSaver.class.getClassLoader();
                    classNode = Class.forName(save.className, true, loader);
                }catch (Exception e){
                    e.printStackTrace();
                }

                if(classNode == null){
                    System.out.println("Class was null, couldn't load: " + save.className);
                    continue;
                }

                Node node = (Node) classNode.getDeclaredConstructor(Graph.class).newInstance(graph);
                node.setName(save.nodeName);
                graph.addNode(node);
                node.posX = save.x;
                node.posY = save.y;

                for (String extraData : save.extraData){
                    node.getExtraSaveData().add(extraData);
                }

                loaded[i] = node;
            }

            for (int i = 0; i < loaded.length; i++) {
                Node node = loaded[i];

                if(node != null){
                    NodeSave save = gs.nodeSaves.get(i);

                    for (int j = 0; j < save.inputPins.size(); j++) {
                        if(j >= node.inputPins.size()){
                            Class classNode = null;

                            ClassLoader loader = GroupSaver.class.getClassLoader();
                            classNode = Class.forName(save.inputPins.get(j).type, true, loader);

                            if(classNode == null){
                                System.out.println("Class " + save.inputPins.get(j).type + " was null, couldn't load");
                                continue;
                            }

                            Pin pin = (Pin) classNode.getDeclaredConstructor().newInstance();
                            pin.setNode(node);
                            pin.setCanDelete(save.inputPins.get(j).canDelete);
                            pin.setPinType(Pin.PinType.Input);
                            node.inputPins.add(pin);
                        }

                        node.inputPins.get(j).setID(save.inputPins.get(j).ID);

                        if(node.inputPins.get(j).getData() != null){
                            node.inputPins.get(j).loadValue(save.inputPins.get(j).value);
                        }
                    }

                    for (int j = 0; j < save.outputPins.size(); j++) {
                        if(j >= node.outputPins.size()){
                            Class classNode = null;

                            ClassLoader loader = GroupSaver.class.getClassLoader();
                            classNode = Class.forName(save.outputPins.get(j).type, true, loader);

                            if(classNode == null){
                                System.out.println("Class " + save.inputPins.get(j).type + " was null, couldn't load");
                                continue;
                            }

                            Pin pin = (Pin) classNode.getDeclaredConstructor().newInstance();
                            pin.setNode(node);
                            pin.setCanDelete(save.outputPins.get(i).canDelete);
                            pin.setPinType(Pin.PinType.Output);
                            node.outputPins.add(pin);
                        }

                        node.outputPins.get(j).setID(save.outputPins.get(j).ID);
                    }
                    node.onLoaded();

                    for (int j = 0; j < save.inputPins.size(); j++) {
                        for (int k = 0; k < save.inputPins.get(j).connections.size(); k++) {
                            int ID = save.inputPins.get(j).connections.get(k);
                            node.inputPins.get(j).connectedToList.add(ID);
                        }
                    }

                    for (int j = 0; j < save.outputPins.size(); j++) {
                        for (int k = 0; k < save.outputPins.get(j).connections.size(); k++) {
                            int ID = save.outputPins.get(j).connections.get(k);
                            node.outputPins.get(j).connectedToList.add(ID);
                        }
                    }
                }
            }

            for(Node node : graph.getNodes().values()){
                for (int i = 0; i < node.inputPins.size(); i++) {
                    Pin pin = node.inputPins.get(i);
                    pin.validateAllConnections();
                }

                for (int i = 0; i < node.outputPins.size(); i++) {
                    Pin pin = node.outputPins.get(i);
                    pin.validateAllConnections();
                }
            }

            return graph;
        }catch (InvocationTargetException e){
            e.printStackTrace();
            return null;
        }catch (InstantiationException e){
            e.printStackTrace();
            return null;
        }catch (IllegalAccessException e){
            e.printStackTrace();
            return null;
        }catch (NoSuchMethodException e){
            e.printStackTrace();
            return null;
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    private void validateDirExists(String directory) {
        File file = new File(directory);
        if(!file.exists()){
            file.mkdir();
        }
    }

    private static class GraphSave{
        private ArrayList<NodeSave> nodeSaves = new ArrayList<>();
    }

    private static class PinSave{
        private Integer ID;
        private String type;
        private String value;
        private ArrayList<Integer> connections = new ArrayList<>();
        private boolean canDelete;
    }

    private static class NodeSave{
        private String className;
        private String nodeName;
        private float x;
        private float y;
        private ArrayList<String> extraData = new ArrayList<>();
        private ArrayList<PinSave> inputPins = new ArrayList<>();
        private ArrayList<PinSave> outputPins = new ArrayList<>();
    }
}
