package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinVar;

public class NodeGetVariable extends Node{

    PinVar outputPin = new PinVar();
    private ComboBox comboBox = new ComboBox();

    //private int lastVariableCount = 0;

    public NodeGetVariable(Graph graph) {
        super(graph);
        setName("Get Variable");

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        comboBox.addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                comboBox.clear();
                for (int i = 0; i < getGraph().globalVariables.size(); i++) {
                    comboBox.addOption("Global: " + getGraph().globalVariables.get(i).name);
                }

                for (int i = 0; i < getGraph().playerVariables.size(); i++) {
                    comboBox.addOption("Player: " + getGraph().playerVariables.get(i).name);
                }

                //lastVariableCount = getGraph().globalVariables.size() + getGraph().playerVariables.size();
            }
        });
    }

    @Override
    public void execute() {
//        if(lastVariableCount != getGraph().globalVariables.size() + getGraph().playerVariables.size()){
//
//            return;
//        }
        if (outputPin.isConnected() && comboBox.size() > 0 && comboBox.getSelectedIndex() != -1) {
            PinData<ImString> data = outputPin.getData();
            String[] val = comboBox.getSelectedValue().replace(" ", "").split(":");
            data.getValue().set(val[0] + "." + val[1]);
        }
    }

    @Override
    public String getOutput() {
        String[] val = comboBox.getSelectedValue().replace(" ", "").split(":");
        return val[0] + "." + val[1];
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
