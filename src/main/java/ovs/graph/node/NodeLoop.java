package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.pin.PinAction;

public class NodeLoop extends Node{

    ComboBox type = new ComboBox();

    private PinAction output = new PinAction();

    public NodeLoop(Graph graph) {
        super(graph);
        setName("Loop");

        output.setNode(this);
        addCustomOutput(output);

        type.addOption("Loop");
        type.addOption("Loop If Condition Is True");
        type.addOption("Loop If Condition Is False");

        type.select(0);

        type.addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                width = -1;
            }
        });

        addUiComponent(type);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("LoopType:" + type.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for (String data : getExtraSaveData()){
            if(data.startsWith("LoopType")){
                type.selectValue(data.split(":")[1]);
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        outputData.getValue().set("Loop;");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {

    }
}
