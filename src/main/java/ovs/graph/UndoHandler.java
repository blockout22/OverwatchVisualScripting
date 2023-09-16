package ovs.graph;

import imgui.extension.nodeditor.NodeEditor;
import ovs.graph.node.Node;

import java.util.ArrayList;

public class UndoHandler {

    private ArrayList<State> graphHistory = new ArrayList<>();

    public void addState(Graph graph){
        State state = new State();
        for(Node node : graph.getNodes().getList()){
            state.nodeStates.add(new NodeState(node.getID(), node.posX, node.posY));
        }
        graphHistory.add(state);
    }

    public void undo(Graph graph){
        State lastState = graphHistory.get(graphHistory.size() - 1);
        for(Node node : graph.getNodes().getList())
        {
            for (int i = 0; i < lastState.nodeStates.size(); i++) {
                NodeState nodeState = lastState.nodeStates.get(i);

                if(nodeState.nodeID == node.getID()){
                    NodeEditor.setNodePosition(node.getID(), nodeState.x, nodeState.y);
                    break;
                }
            }
        }

        graphHistory.remove(graphHistory.size() - 1);
    }

    private class State{
        public ArrayList<NodeState> nodeStates = new ArrayList<>();
    }

    private class NodeState{
        public long nodeID;
        public float x;
        public float y;

        public NodeState(long nodeID, float x, float y) {
            this.nodeID = nodeID;
            this.x = x;
            this.y = y;
        }
    }
}
