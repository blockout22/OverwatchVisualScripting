package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeIf extends Node{

    ComboBox box = new ComboBox();

    PinVar leftPin = new PinVar();
    PinVar rightPin = new PinVar();

    PinAction ifActionPin = new PinAction();

    PinAction output = new PinAction();

    public NodeIf(Graph graph) {
        super(graph);

        setName("If");

        box.addOption("<");
        box.addOption(">");
        box.addOption("<=");
        box.addOption(">=");
        box.addOption("!=");
        box.addOption("==");

        box.select(5);

        leftPin.setNode(this);
        addCustomInput(leftPin);

        rightPin.setNode(this);
        addCustomInput(rightPin);

        ifActionPin.setNode(this);
        addCustomInput(ifActionPin);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        if(leftPin.isConnected() && rightPin.isConnected() && ifActionPin.isConnected()){
            PinData<ImString> dataLeft = leftPin.getData();
            PinData<ImString> dataRight = rightPin.getData();
            PinData<ImString> outputData = output.getData();
            PinData<ImString> ruleData = ifActionPin.getData();

            dataLeft.getValue().set(((ImString)leftPin.getConnectedPin().getData().getValue()).get());
            dataRight.getValue().set(((ImString)rightPin.getConnectedPin().getData().getValue()).get());
            ruleData.getValue().set(((ImString)ifActionPin.getConnectedPin().getData().getValue()).get());

            String out = "";
            String[] lines = ruleData.getValue().get().split("\n");
            for (int i = 0; i < lines.length; i++) {
                out += "\t\t\t" + lines[i] + "\n";
            }

            outputData.getValue().set("If(" + dataLeft.getValue().get() + " " + box.getSelectedValue() + " " + dataRight.getValue().get() + ");\n" +
                    out +
                    "\t\tEnd;");
        }
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        box.show();
    }
}