package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeConst extends Node{
    PinCombo pinCombo = new PinCombo();

    PinVar output = new PinVar();

    public NodeConst(Graph graph) {
        super(graph);

        setName("Const");

        pinCombo.setNode(this);
        pinCombo.setName("Value");
        addCustomInput(pinCombo);

        output.setNode(this);
        addCustomOutput(output);

        populateCombobox();

        pinCombo.getComboBox().addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                String lastSelectedValue = pinCombo.getComboBox().getSelectedValue();
                populateCombobox();

                pinCombo.selectValue(lastSelectedValue);
                width = -1;
            }
        });

        pinCombo.getComboBox().addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                width = -1;
            }
        });
    }

    @Override
    public void execute() {
        PinData<ImString> constData = pinCombo.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinCombo, constData, getGraph().getConstantOutput(pinCombo.getComboBox().getSelectedValue()));

        outputData.getValue().set(constData.getValue().get());
    }

    @Override
    public void copy(Node node) {
        populateCombobox();
        if(node instanceof NodeConst){
            pinCombo.selectValue(((NodeConst) node).pinCombo.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Var:" + pinCombo.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        populateCombobox();
        for(String data : getExtraSaveData()){
            if(data.startsWith("Var")){
                try{
                    pinCombo.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinCombo.select(-1);
                }
            }
        }
    }

    public void populateCombobox(){
        pinCombo.getComboBox().clear();
        for (int i = 0; i < getGraph().constants.size(); i++) {
            pinCombo.addOption(getGraph().constants.get(i).key);
        }
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "Use this node if you have a constant value you have to set multiple times but may need to change some other time, the output of this node can be changed from the constant variables.";
    }
}
