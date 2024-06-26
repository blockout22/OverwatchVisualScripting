package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeChaseGlobalVariableAtRate extends Node{

    ComboBox variables = new ComboBox();
    ComboBox reevaluation = new ComboBox("Destination And Rate", "None");

    PinVar pinDest = new PinVar();
    PinVar pinRate = new PinVar();

    PinAction output = new PinAction();

    public NodeChaseGlobalVariableAtRate(Graph graph) {
        super(graph);
        setName("Chase Global Variable At Rate");

        pinDest.setNode(this);
        pinDest.setName("Destination");
        addCustomInput(pinDest);

        pinRate.setNode(this);
        pinRate.setName("Rate");
        addCustomInput(pinRate);

        output.setNode(this);
        addCustomOutput(output);

        variables.addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                String lastSelectedValue = variables.getSelectedValue();
                populateCombobox();

                variables.selectValue(lastSelectedValue);
                width = -1;
            }
        });

        variables.addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                width = -1;
            }
        });
    }

    @Override
    public void copy(Node node) {
        populateCombobox();
        if(node instanceof NodeChaseGlobalVariableAtRate){
            variables.selectValue(((NodeChaseGlobalVariableAtRate) node).variables.getSelectedValue());
        }
    }

    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Var:" + variables.getSelectedValue());
        getExtraSaveData().add("Reeval:" + reevaluation.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        populateCombobox();
        for(String data : getExtraSaveData()){
            if(data.startsWith("Var")){
                try{
                    variables.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    variables.select(-1);
                }
            }else if(data.startsWith("Reeval")){
                try{
                    reevaluation.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    reevaluation.select(-1);
                }
            }
        }
    }

    private void populateCombobox(){
        variables.clear();
        for (int i = 0; i < getGraph().globalVariables.size(); i++) {
            variables.addOption("Global." + getGraph().globalVariables.get(i).name);
        }
    }

    @Override
    public void execute() {
        PinData<ImString> destData = pinDest.getData();
        PinData<ImString> rateData = pinRate.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinDest, destData);
        handlePinStringConnection(pinRate, rateData, "1");

        if(variables.getSelectedIndex() != -1) {
            String var = variables.getSelectedValue().split("\\.")[1];
            outputData.getValue().set("Chase Global Variable At Rate(" + var + ", " + destData.getValue().get() + ", " + rateData.getValue().get() + ", " + reevaluation.getSelectedValue() + ");");
        }
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        variables.show();
        reevaluation.show();
    }

    @Override
    public String getTooltip() {
        return "Gradually modifies the value of a global variables at a specific rate. (a global variable is a variable that belongs to the game itself).";
    }
}
