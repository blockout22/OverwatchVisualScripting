package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinElse;
import ovs.graph.pin.PinIf;

public class NodeElse extends Node{

    ComboBox condition = new ComboBox();

    PinIf ifPin = new PinIf();
    PinAction actionPin = new PinAction();
    PinElse elsePin = new PinElse();
    PinElse output = new PinElse();

    public NodeElse(Graph graph) {
        super(graph);
        setName("Else");

        condition.addOption("Else");
        condition.addOption("Else If");

        ifPin.setNode(this);
        addCustomInput(ifPin);

        actionPin.setNode(this);
        addCustomInput(actionPin);

        elsePin.setNode(this);
        elsePin.setName("Else");
        addCustomInput(elsePin);

        output.setNode(this);
        addCustomOutput(output);

        condition.addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                if(newValue.equals("Else If")){
                    ifPin.setVisible(true);
                    elsePin.setVisible(true);
                }else if(newValue.equals("Else")){
                    ifPin.setVisible(false);
                    elsePin.setVisible(false);
                }
            }
        });
        condition.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Condition:" + condition.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for (String data : getExtraSaveData()){
            if(data.startsWith("Condition")){
                condition.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> ifData = ifPin.getData();
        PinData<ImString> actionData = actionPin.getData();
        PinData<ImString> elseData = elsePin.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(ifPin, ifData);
        handlePinStringConnection(actionPin, actionData);
        handlePinStringConnection(elsePin, elseData);

        boolean elseIf = condition.getSelectedValue().equals("Else If");
        if(!elseIf) {
            outputData.getValue().set("Else;\n" + actionData.getValue().get());
        }else{
            String out = "";
            String[] lines = actionData.getValue().get().split("\n");
            for (int i = 0; i < lines.length; i++) {
                out += "" + lines[i] + "\n";
            }
            outputData.getValue().set("Else If(" + ifData.getValue().get() + ");\n" +
                    out +
                    (elsePin.isConnected() ? elseData.getValue().get() : ""));
        }
    }

    @Override
    public String getOutput() {
        return "Else;";
    }

    @Override
    public void UI() {
        condition.show();
    }
}
