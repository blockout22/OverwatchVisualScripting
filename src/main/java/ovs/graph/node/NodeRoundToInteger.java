package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinVar;

public class NodeRoundToInteger extends Node {

    ComboBox round = new ComboBox("Up", "Down", "To Nearest");

    PinVar pinValue = new PinVar();
    PinVar output = new PinVar();

    public NodeRoundToInteger(Graph graph) {
        super(graph);
        setName("Round To Integer");

        pinValue.setNode(this);
        pinValue.setName("Value");
        addCustomInput(pinValue);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Round:" + round.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Round")){
                try{
                    round.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    round.select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> valueData = pinValue.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinValue, valueData);

        outputData.getValue().set(getName() + "(" + valueData.getValue().get() + ", " + round.getSelectedValue() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        round.show();
    }

    @Override
    public String getTooltip() {
        return "The integer to which the specified values rounds.";
    }
}