package ovs.graph;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import imgui.ImVec2;
import imgui.ImVec4;
import imgui.extension.nodeditor.NodeEditor;
import ovs.graph.node.Node;
import ovs.graph.pin.Pin;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class NodeCopyPasteHandler {

//    private static ArrayList<NodeCopy> nodeList = new ArrayList<>();

    public static void copy(ArrayList<Node> listOfNodes){
//        nodeList.clear();
        ArrayList<NodeCopy> nodeList = new ArrayList<>();

        for(Node node : listOfNodes){
            String className = node.getClass().getName();

            ImVec2 pos = new ImVec2();
            NodeEditor.getNodePosition(node.getID(), pos);

            NodeCopy copy = new NodeCopy();
            copy.className = className;
            copy.nodeName = node.getName();
            copy.comment = node.getComment().get();
            copy.x = pos.x;
            copy.y = pos.y;

            for(String extraData : node.getExtraSaveData()){
                copy.extraData.add(extraData);
            }

            for(Pin inputs : node.inputPins.getList()){
                PinCopy pinCopy = new PinCopy();
                pinCopy.ID = inputs.getID();
                pinCopy.name = inputs.getName();
                pinCopy.type = inputs.getClass().getName();
                pinCopy.canDelete = inputs.isCanDelete();
                pinCopy.connections.clear();

                for (int i = 0; i < inputs.connectedToList.size(); i++) {
                    pinCopy.connections.add(inputs.connectedToList.get(i));
                }

                if(inputs.getData() != null){
                    if(inputs.getData().getValue() != null){
                        pinCopy.value = inputs.getData().getValue().toString();
                    }
                }

                copy.inputPins.add(pinCopy);
            }

            for(Pin inputs : node.outputPins.getList()){
                PinCopy pinCopy = new PinCopy();
                pinCopy.ID = inputs.getID();
                pinCopy.name = inputs.getName();
                pinCopy.type = inputs.getClass().getName();
                pinCopy.canDelete = inputs.isCanDelete();
                pinCopy.connections.clear();

                for (int i = 0; i < inputs.connectedToList.size(); i++) {
                    pinCopy.connections.add(inputs.connectedToList.get(i));
                }

                copy.outputPins.add(pinCopy);
            }

            nodeList.add(copy);
        }

        Gson json = new GsonBuilder().setPrettyPrinting().create();
        String output = json.toJson(nodeList);

        StringSelection selection = new StringSelection(output);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);

//        averageX = 0;
//        averageY = 0;
//        nodeList.clear();
//        for (int i = 0; i < listOfNodes.size(); i++) {
//            NodeData newData = new NodeData();
//            ImVec2 position = new ImVec2();
//            NodeEditor.getNodePosition(listOfNodes.get(i).getID(), position);
//            newData.position = position;
//            newData.node = listOfNodes.get(i);
//
//            for (int j = 0; j < listOfNodes.get(i).inputPins.size(); j++) {
//                Pin pin = listOfNodes.get(i).inputPins.get(j);
//                if(pin.isConnected()) {
//                    for(Integer connection : pin.connectedToList)
//                    newData.connection.add(connection);
//                }
//            }
//
//            nodeList.add(newData);
//
//            averageX += nodeList.get(i).position.x;
//            averageY += nodeList.get(i).position.y;
//        }
//
//        averageX /= nodeList.size();
//        averageY /= nodeList.size();
    }

    public static void paste(Graph graph){
        String content = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        if(clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)){
            try{
                content = (String) clipboard.getData(DataFlavor.stringFlavor);
            }catch (Exception e){

            }
        }

        if(content.length() > 0){
            try{
                Gson json = new GsonBuilder().setPrettyPrinting().create();

                Type type = new TypeToken<ArrayList<NodeCopy>>(){}.getType();
                ArrayList<NodeCopy> nodeList = json.fromJson(content, type);

                Node[] loaded = new Node[nodeList.size()];

                System.out.println(nodeList);

                int lowestCopiedPinID = Integer.MAX_VALUE;

                for(NodeCopy copy : nodeList){
                    for(PinCopy pinCopy : copy.inputPins){
                        if(pinCopy.ID < lowestCopiedPinID){
                            lowestCopiedPinID = pinCopy.ID;
                        }
                    }

                    for(PinCopy pinCopy : copy.outputPins){
                        if(pinCopy.ID < lowestCopiedPinID){
                            lowestCopiedPinID = pinCopy.ID;
                        }
                    }
                }

                for (int i = 0; i < nodeList.size(); i++) {
                    NodeCopy copy = nodeList.get(i);
                    Class classNode = null;

                    try {
                        ClassLoader loader = NodeCopyPasteHandler.class.getClassLoader();
                        classNode = Class.forName(copy.className, true, loader);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (classNode == null) {
                        System.out.println("Class was null, couldn't load: " + copy.className);
                        continue;
                    }

                    Node node = (Node) classNode.getDeclaredConstructor(Graph.class).newInstance(graph);
                    node.setName(copy.nodeName);
                    node.setComment(copy.comment != null ? copy.comment : "");
                    graph.addNode(node);

                    float nodeRelativePositionX = copy.x;
                    float nodeRelativePositionY = copy.y;
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    NodeEditor.setNodePosition(node.getID(), NodeEditor.toCanvasX(point.x  * (1 / NodeEditor.getCurrentZoom())) + nodeRelativePositionX, NodeEditor.toCanvasY(point.y * (1 / NodeEditor.getCurrentZoom())) + nodeRelativePositionY);

                    for(String extraData : copy.extraData){
                        node.getExtraSaveData().add(extraData);
                    }

                    loaded[i] = node;
                }

                int highestPinID = Graph.getNextAvailablePinID();
                int offset = highestPinID - lowestCopiedPinID;

                for (int i = 0; i < loaded.length; i++) {
                    Node node = loaded[i];
                    if (node != null) {
                        NodeCopy copy = nodeList.get(i);

                        for (int j = 0; j < copy.inputPins.size(); j++) {
                            if (j >= node.inputPins.size()) {
                                Class classNode = null;

                                ClassLoader loader = NodeCopyPasteHandler.class.getClassLoader();
                                classNode = Class.forName(copy.inputPins.get(j).type, true, loader);

                                Pin pin = (Pin) classNode.getDeclaredConstructor().newInstance();
                                pin.setNode(node);
                                pin.setCanDelete(copy.inputPins.get(j).canDelete);

                                if(copy.inputPins.get(j).name != null){
                                    pin.setName(copy.inputPins.get(j).name);
                                }
                                pin.setPinType(Pin.PinType.Input);
                                node.inputPins.add(pin);
                            }

                            node.inputPins.get(j).setID(copy.inputPins.get(j).ID + offset);

                            if(node.inputPins.get(j).getData() != null){
                                node.inputPins.get(j).loadValue(copy.inputPins.get(j).value);
                            }
                        }

                        for (int j = 0; j < copy.outputPins.size(); j++) {
                            if (j >= node.outputPins.size()) {
                                Class classNode = null;

                                ClassLoader loader = NodeCopyPasteHandler.class.getClassLoader();
                                classNode = Class.forName(copy.outputPins.get(j).type, true, loader);

                                Pin pin = (Pin) classNode.getDeclaredConstructor().newInstance();
                                pin.setNode(node);
                                pin.setCanDelete(copy.outputPins.get(j).canDelete);

                                if(copy.outputPins.get(j).name != null){
                                    pin.setName(copy.outputPins.get(j).name);
                                }
                                pin.setPinType(Pin.PinType.Output);
                                node.outputPins.add(pin);
                            }

                            node.outputPins.get(j).setID(copy.outputPins.get(j).ID + offset);
                        }

                        node.onLoaded();

                        for (int j = 0; j < copy.inputPins.size(); j++) {
                            for (int k = 0; k < copy.inputPins.get(j).connections.size(); k++) {
                                System.out.println(copy.inputPins.size() + " : " + node.inputPins.size());
                                int ID = copy.inputPins.get(j).connections.get(k) + offset;
                                node.inputPins.get(j).connectedToList.add(ID);
                            }
                        }

                        for (int j = 0; j < copy.outputPins.size(); j++) {
                            for (int k = 0; k < copy.outputPins.get(j).connections.size(); k++) {
                                //TDOD apply offset with the highest ID
                                int ID = copy.outputPins.get(j).connections.get(k) + offset;
                                node.outputPins.get(j).connectedToList.add(ID);
                            }
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
//        HashMap<Integer, Integer> pinIdMap = new HashMap<>();
//        for (int i = 0; i < nodeList.size(); i++) {
//            Node newInstance = null;
//            try {
//                Node target = nodeList.get(i).node;
//                newInstance = target.getClass().getDeclaredConstructor(Graph.class).newInstance(graph);
//                graph.addNode(newInstance);
//
//                float nodeRelativePositionX = nodeList.get(i).position.x;
//                float nodeRelativePositionY = nodeList.get(i).position.y;
//                Point point = MouseInfo.getPointerInfo().getLocation();
//                NodeEditor.setNodePosition(newInstance.getID(), NodeEditor.toCanvasX(point.x - averageX * (1 / NodeEditor.getCurrentZoom())) + nodeRelativePositionX, NodeEditor.toCanvasY(point.y - averageY * (1 / NodeEditor.getCurrentZoom())) + nodeRelativePositionY);
//
//                int defaultTotal = newInstance.inputPins.size();
//                for (int j = 0; j < target.inputPins.size(); j++) {
//                    Pin oldPin = target.inputPins.get(j);
//                    Pin newPin = target.inputPins.get(j).getClass().getDeclaredConstructor().newInstance();
//                    if(j > defaultTotal - 1){
//                        newPin.setNode(newInstance);
//                        newPin.setName(target.inputPins.get(j).getName());
//                        newPin.setCanDelete(true);
//                        newInstance.addCustomInput(newPin);
//                    }
//
////                    pinIdMap.put(oldPin.getID(), newPin.getID());
//                }
//
//                for (int j = 0; j < target.inputPins.size(); j++) {
//                    Pin oldPin = target.inputPins.get(j);
//                    Pin newPin = newInstance.inputPins.get(j);
//                    int oldValue = oldPin.getID();
//
//                    try {
//                        for (int k = 0; k < oldPin.connectedToList.size(); k++) {
////                            int newConnection = pinIdMap.get(k);
//                            Pin pin = oldPin.getConnectedPin();
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//
//                newInstance.copy(target);
////                System.out.println("Added New Instance: " + newInstance.getName());
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }

    public static class NodeCopy {
        private String className;
        private String nodeName;
        private float x;
        private float y;
        private String comment;
        private ArrayList<String> extraData = new ArrayList<>();
        private ArrayList<PinCopy> inputPins = new ArrayList<>();
        private ArrayList<PinCopy> outputPins = new ArrayList<>();
//        ImVec2 position;
//        Node node;
//        ArrayList<Integer> connection = new ArrayList<>();
    }

    public static class PinCopy{
        private Integer ID;
        private String name;
        private String type;
        private String value;
        //        private Integer connectedTo;
        private ArrayList<Integer> connections = new ArrayList<>();
        private boolean canDelete;
    }
}
