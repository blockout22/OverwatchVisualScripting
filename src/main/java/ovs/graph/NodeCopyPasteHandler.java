package ovs.graph;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditor;
import ovs.graph.node.Node;
import ovs.graph.pin.Pin;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NodeCopyPasteHandler {

    private static ArrayList<NodeData> nodeList = new ArrayList<>();

    private static float averageX = 0;
    private static float averageY = 0;

    public static void copy(ArrayList<Node> listOfNodes){
        averageX = 0;
        averageY = 0;
        nodeList.clear();
        for (int i = 0; i < listOfNodes.size(); i++) {
            NodeData newData = new NodeData();
            ImVec2 position = new ImVec2();
            NodeEditor.getNodePosition(listOfNodes.get(i).getID(), position);
            newData.position = position;
            newData.node = listOfNodes.get(i);

            for (int j = 0; j < listOfNodes.get(i).inputPins.size(); j++) {
                Pin pin = listOfNodes.get(i).inputPins.get(j);
                if(pin.isConnected()) {
                    for(Integer connection : pin.connectedToList)
                    newData.connection.add(connection);
                }
            }

            nodeList.add(newData);

            averageX += nodeList.get(i).position.x;
            averageY += nodeList.get(i).position.y;
        }

        averageX /= nodeList.size();
        averageY /= nodeList.size();
    }

    public static void paste(Graph graph){

//        HashMap<Integer, Integer> pinIdMap = new HashMap<>();
        for (int i = 0; i < nodeList.size(); i++) {
            Node newInstance = null;
            try {
                Node target = nodeList.get(i).node;
                newInstance = target.getClass().getDeclaredConstructor(Graph.class).newInstance(graph);
                graph.addNode(newInstance);

                float nodeRelativePositionX = nodeList.get(i).position.x;
                float nodeRelativePositionY = nodeList.get(i).position.y;
                Point point = MouseInfo.getPointerInfo().getLocation();
                NodeEditor.setNodePosition(newInstance.getID(), NodeEditor.toCanvasX(point.x - averageX * (1 / NodeEditor.getCurrentZoom())) + nodeRelativePositionX, NodeEditor.toCanvasY(point.y - averageY * (1 / NodeEditor.getCurrentZoom())) + nodeRelativePositionY);

                int defaultTotal = newInstance.inputPins.size();
                for (int j = 0; j < target.inputPins.size(); j++) {
                    Pin oldPin = target.inputPins.get(j);
                    Pin newPin = target.inputPins.get(j).getClass().getDeclaredConstructor().newInstance();
                    if(j > defaultTotal - 1){
                        newPin.setNode(newInstance);
                        newPin.setName(target.inputPins.get(j).getName());
                        newPin.setCanDelete(true);
                        newInstance.addCustomInput(newPin);
                    }

//                    pinIdMap.put(oldPin.getID(), newPin.getID());
                }

                for (int j = 0; j < target.inputPins.size(); j++) {
                    Pin oldPin = target.inputPins.get(j);
                    Pin newPin = newInstance.inputPins.get(j);
                    int oldValue = oldPin.getID();

                    try {
                        for (int k = 0; k < oldPin.connectedToList.size(); k++) {
//                            int newConnection = pinIdMap.get(k);
                            Pin pin = oldPin.getConnectedPin();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                newInstance.copy(target);
//                System.out.println("Added New Instance: " + newInstance.getName());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static class NodeData{
        ImVec2 position;
        Node node;
        ArrayList<Integer> connection = new ArrayList<>();
    }
}
