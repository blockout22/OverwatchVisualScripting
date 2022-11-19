package ovs.graph.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditor;
import ovs.graph.Graph;
import ovs.graph.node.Node;
import ovs.graph.pin.Pin;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GraphSaver {

    private transient GraphSave graphSave = new GraphSave();
    private static StringBuilder sb = new StringBuilder();

    String dir = "Scripts";

    public void save(String fileName, Graph graph){
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

            for(Pin inputs : node.inputPins){
                PinSave pinSave = new PinSave();
                pinSave.ID = inputs.getID();
                pinSave.type = inputs.getClass().getName();
                pinSave.connectedTo = inputs.connectedTo;

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

    public Graph load(String fileName){
        try{
            File file = new File(dir + File.separator + fileName + File.separator + "script.json");
            BufferedReader br = new BufferedReader(new FileReader(file));

            Gson json = new GsonBuilder().setPrettyPrinting().create();

            GraphSave gs = json.fromJson(br, GraphSave.class);

            br.close();

            Node[] loaded = new Node[gs.nodeSaves.size()];

            Graph graph = new Graph();

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

//                            pin.setCanDelete(true);
                            node.outputPins.add(pin);
                        }

                        node.outputPins.get(j).setID(save.outputPins.get(j).ID);
                        if(save.outputPins.get(j).connectedTo != -1){
                            node.outputPins.get(j).connectedTo = save.outputPins.get(j).connectedTo;
                        }
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
        private ArrayList<NodeSave> nodeSaves = new ArrayList<>();
    }

    private static class PinSave{
        private Integer ID;
        private String type;
        private String value;
        private Integer connectedTo;
    }


    private static class NodeSave{
        private String className;
        private String nodeName;
        private float x;
        private float y;
        //save Pin array
        private ArrayList<PinSave> inputPins = new ArrayList<>();
        private ArrayList<PinSave> outputPins = new ArrayList<>();
//        private ArrayList<Integer> connectedToList = new ArrayList<>();

    }
}
