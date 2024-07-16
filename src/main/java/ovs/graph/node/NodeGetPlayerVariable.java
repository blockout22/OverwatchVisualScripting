package ovs.graph.node;

import imgui.ImVec4;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeGetPlayerVariable extends Node{

    private PinVar pinPlayer = new PinVar();
    private PinVar outputPin = new PinVar();
    public PinCombo pinVariable = new PinCombo();

    public NodeGetPlayerVariable(Graph graph) {
        super(graph);
        setName("Get Player Variable");
        setColor(0, 125, 255);

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinVariable.setNode(this);
        pinVariable.setName("Variable");
        addCustomInput(pinVariable);

        outputPin.setNode(this);
        addCustomOutput(outputPin);

        populateCombobox();

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
    public void execute() {
//        if (outputPin.isConnected() && variableBox.size() > 0 && variableBox.getSelectedIndex() != -1) {
            PinData<ImString> playerData = pinPlayer.getData();
            PinData<ImString> variableData = pinVariable.getData();
            PinData<ImString> data = outputPin.getData();

            handlePinStringConnection(pinPlayer, playerData, "Event Player");
            data.getValue().set(playerData.getValue().get() + "." + variableData.getValue().get());
//        }
    }

    @Override
    public void copy(Node node) {
        populateCombobox();
        if(node instanceof NodeGetPlayerVariable){
            pinVariable.selectValue(((NodeGetPlayerVariable) node).pinVariable.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Var:" + pinVariable.getComboBox().getSelectedValue());
    }

    public void populateCombobox(){
        //Populate combox to allow selection of saved variables
        pinVariable.getComboBox().clear();
        for (int i = 0; i < getGraph().playerVariables.size(); i++) {
            pinVariable.addOption(getGraph().playerVariables.get(i).name);
        }

        for (int i = 0; i < pinVariable.getComboBox().size(); i++) {
            pinVariable.getComboBox().setItemColor(i, new ImVec4(.75f, 1, .75f, 255));
        }
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
            }
        }
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = outputPin.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
    }

    @Override
    public String getTooltip() {
        return "The current value of a player variable, which is a variable that belongs to a specific player.";
    }
}
