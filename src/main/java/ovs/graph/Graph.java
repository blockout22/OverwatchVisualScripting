package ovs.graph;

import ovs.graph.node.Node;
import ovs.graph.pin.Pin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {

//    private final AdvancedMap<Integer, Node> nodes = new AdvancedMap<>();
    public final AdvancedArrayList<Node> nodes = new AdvancedArrayList<>();
    private ArrayList<Integer> queuedForRemoval = new ArrayList<>();
    public final AdvancedArrayList<Variable> globalVariables = new AdvancedArrayList<>();
    public final AdvancedArrayList<Variable> playerVariables = new AdvancedArrayList<>();
    public final AdvancedArrayList<String> subroutines = new AdvancedArrayList<>();

    private static int nextNodeID = 1;
    private static int nextPinID = 1000;

    public static int getNextAvailablePinID(){
        return nextPinID++;
    }

    public boolean addNode(Node node){
        node.setID(nextNodeID++);
        node.setName(node.getName());
//        nodes.put(node.getID(), node);
        nodes.add(node);
        return true;
    }

    public void addGlobalVariable(String name){
        Variable var = new Variable();
        var.type = Variable.Type.GLOBAL;
//        var.ID = globalVariables.size();
        var.name = name;
        globalVariables.add(var);
    }

    public void addPlayerVariable(String name){
        Variable var = new Variable();
        var.type = Variable.Type.PLAYER;
//        var.ID = playerVariables.size();
        var.name = name;
        playerVariables.add(var);
    }

    public void addSubroutine(String name){
        subroutines.add(name);
    }

    public AdvancedArrayList<Node> getNodes()
    {
        return nodes;
    }

    public void update(){
        for(Integer q : queuedForRemoval){
            Node n = null;

            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                if(node.getID() == q){
                    n = node;
                    break;
                }
            }

            //Clear any pin existing connections
            for(Pin pin : n.outputPins){
                for (int i = 0; i < pin.connectedToList.size(); i++) {
                    Pin connection = findPinById(pin.connectedToList.get(i));
                    if(connection != null) {
                        connection.remove(pin.getID());
                    }
                }
//                if (pin.connectedTo != -1) {
//                    Pin oldPin = findPinById(pin.connectedTo);
//                    oldPin.connectedTo = -1;
//                }
            }
            for(Pin pin : n.inputPins){
                for (int i = 0; i < pin.connectedToList.size(); i++) {
                    Pin connection = findPinById(pin.connectedToList.get(i));
                    if(connection != null) {
                        connection.remove(pin.getID());
                    }
                }
//                if (pin.connectedTo != -1) {
//                    Pin oldPin = findPinById(pin.connectedTo);
//                    oldPin.connectedTo = -1;
//                }
            }
            nodes.remove(n);
        }
        queuedForRemoval.clear();
    }

    public void removeNode(int nodeId){
        queuedForRemoval.add(nodeId);
    }

    public void setHighestPinID(int id){
        if (id > nextPinID){
            nextPinID = id;
        }
    }

    public Pin findPinById(final int ID) {
//        for(Node node : nodes.values()){
        for(Node node : nodes.getList()){
            for(Pin pin : node.inputPins){
                if(pin.getID() == ID){
                    return pin;
                }
            }

            for(Pin pin : node.outputPins){
                if(pin.getID() == ID){
                    return pin;
                }
            }
        }

        return null;
    }
}
