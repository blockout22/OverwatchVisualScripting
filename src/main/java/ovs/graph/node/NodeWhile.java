package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeWhile extends Node{

    ComboBox box = new ComboBox();

    PinVar leftPin = new PinVar();
    PinVar rightPin = new PinVar();

    PinAction actionPin = new PinAction();

    PinAction output = new PinAction();
    public NodeWhile(Graph graph) {
        super(graph);
        setName("While");

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

        actionPin.setNode(this);
        addCustomInput(actionPin);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Condition:" + box.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Condition:"))
            {
                box.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        if(leftPin.isConnected() && rightPin.isConnected() && actionPin.isConnected()){
            PinData<ImString> dataLeft = leftPin.getData();
            PinData<ImString> dataRight = rightPin.getData();
            PinData<ImString> actionData = actionPin.getData();
            PinData<ImString> outputData = output.getData();

            dataLeft.getValue().set(((ImString)leftPin.getConnectedPin().getData().getValue()).get());
            dataRight.getValue().set(((ImString)rightPin.getConnectedPin().getData().getValue()).get());
            actionData.getValue().set(((ImString)actionPin.getConnectedPin().getData().getValue()).get());

            String out = "";
            String[] lines = actionData.getValue().get().split("\n");
            for (int i = 0; i < lines.length; i++) {
                out += "\t\t\t" + lines[i] + "\n";
            }


            outputData.getValue().set("While(" + dataLeft.getValue().get() + " " + box.getSelectedValue() + " " + dataRight.getValue().get() + ");\n" +
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
