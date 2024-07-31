package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeChasePlayerVariableAtRate extends Node{


    PinVar pinPlayer = new PinVar();
    PinCombo variables = new PinCombo();
    PinVar pinDest = new PinVar();
    PinVar pinRate = new PinVar();
    PinCombo reevaluation = new PinCombo();

    PinAction output = new PinAction();

    public NodeChasePlayerVariableAtRate(Graph graph) {
        super(graph);
        setName("Chase Player Variable At Rate");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        variables.setNode(this);
        variables.setName("Variable");
        addCustomInput(variables);

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

        variables.getComboBox().addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                String lastSelectedValue = variables.getComboBox().getSelectedValue();
                populateCombobox();

                variables.selectValue(lastSelectedValue);
                width = -1;
            }
        });

        variables.getComboBox().addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                width = -1;
            }
        });
    }

    @Override
    public void copy(Node node) {
        populateCombobox();
        if(node instanceof NodeChasePlayerVariableAtRate){
            variables.selectValue(((NodeChasePlayerVariableAtRate) node).variables.getComboBox().getSelectedValue());
        }
    }

    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Var:" + variables.getComboBox().getSelectedValue());
        getExtraSaveData().add("Reeval:" + reevaluation.getComboBox().getSelectedValue());
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
        variables.getComboBox().clear();
        for (int i = 0; i < getGraph().playerVariables.size(); i++) {
            variables.addOption("Event Player." + getGraph().playerVariables.get(i).name);
        }
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> varData = variables.getData();
        PinData<ImString> destData = pinDest.getData();
        PinData<ImString> rateData = pinRate.getData();
        PinData<ImString> reevaluationData = reevaluation.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData);
        handlePinStringConnection(variables, varData, variables.getComboBox().getSelectedValue());
        handlePinStringConnection(pinDest, destData);
        handlePinStringConnection(pinRate, rateData, "1");
        handlePinStringConnection(reevaluation, reevaluationData, reevaluation.getComboBox().getSelectedValue());

//        if(variables.getSelectedIndex() != -1) {
//            String var = variables.getSelectedValue().split("\\.")[1];
            outputData.getValue().set("Chase Player Variable At Rate(" + playerData.getValue().get() + ", " + varData.getValue().get() + ", " + destData.getValue().get() + ", " + rateData.getValue().get() + ", " + reevaluationData.getValue().get() + ");");
//        }
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
        return "Gradually modifies the value of a player variables at a specific rate. (a player variable is a variable that belongs to a specific player).";
    }
}
