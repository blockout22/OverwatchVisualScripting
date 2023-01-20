package ovs.graph;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditor;
import ovs.graph.node.Node;
import ovs.graph.pin.Pin;

import java.util.ArrayList;

public class NodeCopyPasteHandler {

    private static ArrayList<NodeData> nodeList = new ArrayList<>();

    private static float copyLocationX = 0;
    private static float copyLocationY = 0;

    public static void copy(ArrayList<Node> listOfNodes){
        copyLocationX = ImGui.getMousePosX();
        copyLocationY = ImGui.getMousePosY();
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
        }
    }

    public static void paste(Graph graph){
        for (int i = 0; i < nodeList.size(); i++) {
            Node newInstance = null;
            try {
                Node target = nodeList.get(i).node;
                newInstance = target.getClass().getDeclaredConstructor(Graph.class).newInstance(graph);
                graph.addNode(newInstance);
//                NodeEditor.setNodePosition(newInstance.getID(), NodeEditor.toCanvasX(ImGui.getCursorScreenPosX()) + nodeList.get(i).position.x, NodeEditor.toCanvasY(ImGui.getCursorScreenPosY() + + nodeList.get(i).position.y));
//                NodeEditor.setNodePosition(newInstance.getID(), NodeEditor.toCanvasX(ImGui.getMousePosX() + nodeList.get(i).position.x), NodeEditor.toCanvasY(ImGui.getMousePosY() + nodeList.get(i).position.y));
                float xOffset = 0;//copyLocationX - ImGui.getMousePosX() + nodeList.get(i).position.x;
                float yOffset = 0; //copyLocationY - ImGui.getMousePosY() + nodeList.get(i).position.y;
                System.out.println(nodeList.get(i).position.x + " : " + copyLocationX + " : " + ImGui.getMousePosX());
                NodeEditor.setNodePosition(newInstance.getID(), NodeEditor.toCanvasX(ImGui.getMousePosX())  + xOffset, NodeEditor.toCanvasY(ImGui.getMousePosY()) + yOffset);

                int defaultTotal = newInstance.inputPins.size();
                for (int j = 0; j < target.inputPins.size(); j++) {
                    if(j > defaultTotal - 1){
                        Pin newPin = target.inputPins.get(j).getClass().getDeclaredConstructor().newInstance();
                        newPin.setNode(newInstance);
                        newPin.setName(target.inputPins.get(j).getName());
                        newPin.setCanDelete(true);
                        newInstance.addCustomInput(newPin);

                    }
//                    for (Integer connection : target.inputPins.get(i).connectedToList) {
//                        graph.findPinById(connection);
////                        newInstance.inputPins.get(j).connectedToList.add(connection);
//                    }
                }

                newInstance.copy(target);
                System.out.println("Added New Instance: " + newInstance.getName());
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
