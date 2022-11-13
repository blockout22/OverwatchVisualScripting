package ovs.graph;

import ovs.graph.node.Node;
import ovs.graph.pin.Pin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    private final Map<Integer, Node> nodes = new HashMap<>();
    private ArrayList<Integer> queuedForRemoval = new ArrayList<>();

    private static int nextNodeID = 1;
    private static int nextPinID = 1000;

    public static int getNextAvailablePinID(){
        return nextPinID++;
    }

    public boolean addNode(Node node){
        node.setID(nextNodeID++);
        node.setName(node.getName());
        nodes.put(node.getID(), node);
        return true;
    }

    public Map<Integer, Node> getNodes()
    {
        return nodes;
    }

    public void update(){
        for(Integer q : queuedForRemoval){
            Node n = nodes.get(q);

            //Clear any pin existing connections
            for(Pin pin : n.outputPins){
                if (pin.connectedTo != -1) {
                    Pin oldPin = findPinById(pin.connectedTo);
                    oldPin.connectedTo = -1;
                }
            }
            for(Pin pin : n.inputPins){
                if (pin.connectedTo != -1) {
                    Pin oldPin = findPinById(pin.connectedTo);
                    oldPin.connectedTo = -1;
                }
            }
            nodes.remove(q);
        }
        queuedForRemoval.clear();
    }

    public void removeNode(int node){
        queuedForRemoval.add(node);
    }

    public Pin findPinById(final int ID) {
        for(Node node : nodes.values()){
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
