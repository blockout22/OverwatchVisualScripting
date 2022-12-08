package ovs.graph;

import ovs.graph.node.Node;
import ovs.graph.node.NodeRule;

public class Compiler {

    public static String compile(Graph graph, Settings settings){
        StringBuilder output = new StringBuilder();

        output.append(settings.getOutput());
        output.append("\n");

        if (graph.globalVariables.size() > 0 || graph.playerVariables.size() > 0){
            output.append("variables\n");
            output.append("{\n");
            if(graph.globalVariables.size() > 0){
                output.append("global:\n");

                for (int i = 0; i < graph.globalVariables.size(); i++) {
//                    output.append("\t\t" + graph.globalVariables.get(i).ID + ": " + graph.globalVariables.get(i).name + "\n");
                    output.append(i + ": " + graph.globalVariables.get(i).name + "\n");
                }

                output.append("\n");
            }
            if(graph.playerVariables.size() > 0){
                output.append("player:\n");

                for (int i = 0; i < graph.playerVariables.size(); i++) {
//                    output.append("\t\t" + graph.playerVariables.get(i).ID + ": " + graph.playerVariables.get(i).name + "\n");
                    output.append(i + ": " + graph.playerVariables.get(i).name + "\n");
                }

            }
            output.append("}\n");
            output.append("\n");
        }

        output.append("rule(\"Credits\")\n");
        output.append("{\n");
        output.append("event\n");
        output.append("{\n");
        output.append("Ongoing - Global;\n");
        output.append("}\n");
        output.append("\n");
        output.append("actions\n");
        output.append("{\n");
        output.append("Create HUD Text(All Players(All Teams), Null, Custom String(\"Created using github.com/blockout22/OverwatchVisualScripting\"), Null, Right, -999, Color(White), Color(White), Color(White), Visible To and String, Default Visibility);\n");
        output.append("}\n");
        output.append("}\n");
        output.append("\n");

        for(Node node : graph.getNodes().values()){
            if(node instanceof NodeRule){
                output.append(handleNode(node));
                output.append("\n\n");
            }
        }

        return output.toString();
    }

    private static String handleNode(Node node){
        return node.getOutput();
    }
}
