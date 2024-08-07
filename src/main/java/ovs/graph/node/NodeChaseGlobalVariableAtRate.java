package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeChaseGlobalVariableAtRate extends Node{

    public PinCombo pinVariable = new PinCombo();
    PinVar pinDest = new PinVar();
    PinVar pinRate = new PinVar();
    PinCombo reevaluation = new PinCombo();

    PinAction output = new PinAction();

    public NodeChaseGlobalVariableAtRate(Graph graph) {
        super(graph);
        setName("Chase Global Variable At Rate");

        pinVariable.setNode(this);
        pinVariable.setName("Variable");
        addCustomInput(pinVariable);

        pinDest.setNode(this);
        pinDest.setName("Destination");
        addCustomInput(pinDest);

        pinRate.setNode(this);
        pinRate.setName("Rate");
        addCustomInput(pinRate);

        reevaluation.setNode(this);
        reevaluation.setName("Reevaluation");
        addCustomInput(reevaluation);

        reevaluation.addOption("Destination And Rate");
        reevaluation.addOption("None");

        reevaluation.select(0);

        output.setNode(this);
        addCustomOutput(output);

        pinVariable.getComboBox().addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                String lastSelectedValue = pinVariable.getComboBox().getSelectedValue();
                populateCombobox();

                pinVariable.selectValue(lastSelectedValue);
                width = -1;
            }
        });

        pinVariable.getComboBox().addChangeListener(new ChangeListener() {
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
            pinVariable.selectValue(((NodeChaseGlobalVariableAtRate) node).pinVariable.getComboBox().getSelectedValue());
        }
    }

    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Var:" + pinVariable.getComboBox().getSelectedValue());
        getExtraSaveData().add("Reeval:" + reevaluation.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        populateCombobox();
        for(String data : getExtraSaveData()){
            if(data.startsWith("Var")){
                try{
                    pinVariable.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinVariable.select(-1);
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

    public void populateCombobox(){
        pinVariable.getComboBox().clear();
        for (int i = 0; i < getGraph().globalVariables.size(); i++) {
            pinVariable.addOption(getGraph().globalVariables.get(i).name);
        }
    }

    @Override
    public void execute() {
        PinData<ImString> varData = pinVariable.getData();
        PinData<ImString> destData = pinDest.getData();
        PinData<ImString> rateData = pinRate.getData();
        PinData<ImString> reevaluationData = reevaluation.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVariable, varData, pinVariable.getComboBox().getSelectedValue());
        handlePinStringConnection(pinDest, destData);
        handlePinStringConnection(pinRate, rateData, "1");
        handlePinStringConnection(reevaluation, reevaluationData, reevaluation.getComboBox().getSelectedValue());

            outputData.getValue().set("Chase Global Variable At Rate(" + varData.getValue().get() + ", " + destData.getValue().get() + ", " + rateData.getValue().get() + ", " + reevaluationData.getValue().get() + ");");
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
        return "Gradually modifies the value of a global variables at a specific rate. (a global variable is a variable that belongs to the game itself).";
    }
}
