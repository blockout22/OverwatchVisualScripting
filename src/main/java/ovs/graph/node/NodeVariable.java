package ovs.graph.node;

import ovs.graph.Graph;
import ovs.graph.UI.ComboBox;

public class NodeVariable extends Node{

    private ComboBox comboBox = new ComboBox();

    private int lastVariableCount = 0;

    public NodeVariable(Graph graph) {
        super(graph);
        setName("Variable");
    }

    @Override
    public void execute() {
        if(lastVariableCount != getGraph().globalVariables.size() + getGraph().playerVariables.size()){
            comboBox.clear();
            for (int i = 0; i < getGraph().globalVariables.size(); i++) {
                comboBox.addOption("Global: " + getGraph().globalVariables.get(i).name);
            }

            for (int i = 0; i < getGraph().playerVariables.size(); i++) {
                comboBox.addOption("Player: " + getGraph().playerVariables.get(i).name);
            }

            lastVariableCount = getGraph().globalVariables.size() + getGraph().playerVariables.size();
        }
    }

    @Override
    public String getOutput() {
        return "Get Variable Name";
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
