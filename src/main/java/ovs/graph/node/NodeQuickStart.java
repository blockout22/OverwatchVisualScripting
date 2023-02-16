package ovs.graph.node;

import imgui.type.ImBoolean;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinBoolean;

public class NodeQuickStart extends NodeEntry {

    private PinBoolean skipAssembleHeroes = new PinBoolean();
    private PinBoolean skipSetup = new PinBoolean();

    public NodeQuickStart(Graph graph) {
        super(graph);
        setName("QuickStart");
        setColor(175, 255, 50);

        skipAssembleHeroes.setNode(this);
        skipAssembleHeroes.setName("Skip Assemble Heroes");
        addCustomInput(skipAssembleHeroes);

        skipSetup.setNode(this);
        skipSetup.setName("Skip Setup");
        addCustomInput(skipSetup);
    }

    @Override
    public void execute() {
    }

    @Override
    public String getOutput() {
        PinData<ImBoolean> skipAssembleData = skipAssembleHeroes.getData();
        PinData<ImBoolean> skipSetupData = skipSetup.getData();

        StringBuilder sb = new StringBuilder();

        if (skipAssembleData.getValue().get()){
            String out = "";
            out += "rule(\"Skip Assemble Heroes\")\n";
            out += "{\n";

            out += "event\n";
            out += "{\n";
            out += "Ongoing - Global;\n";
            out += "}\n";

            out += "\n";

            out += "conditions\n";
            out += "{\n";
            out += "Is In Setup == True;\n";
            out += "}\n";

            out += "\n";

            out += "actions\n";
            out += "{\n";
            out += "Set Match Time(0.0);\n";
            out += "}\n";

            out += "}\n";

            sb.append(out);
        }

        if(skipSetupData.getValue().get()){
            sb.append("\n");
            String out = "";
            out += "rule(\"Skip Setup\")\n";
            out += "{\n";

            out += "event\n";
            out += "{\n";
            out += "Ongoing - Global;\n";
            out += "}\n";

            out += "\n";

            out += "conditions\n";
            out += "{\n";
            out += "Is In Setup == True;\n";
            out += "}\n";

            out += "\n";

            out += "actions\n";
            out += "{\n";
            out += "Set Match Time(0.0);\n";
            out += "}\n";

            out += "}\n";

            sb.append(out);
        }

        return sb.toString();
    }

    @Override
    public void UI() {

    }
}