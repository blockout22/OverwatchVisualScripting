package ovs.graph.node;

import imgui.ImVec4;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeSetPlayerVariable extends Node{

    private PinVar pinPlayer = new PinVar();
    public PinCombo pinVariable = new PinCombo();
    private PinVar pinValue = new PinVar();
    private PinAction output = new PinAction();
//    public ComboBox variableBox = new ComboBox();

    //TODO allow users to select a specific players variable
    public NodeSetPlayerVariable(Graph graph) {
        super(graph);
        setName("Set Player Variable");
        setColor(0, 125, 255);

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinVariable.setNode(this);
        pinVariable.setName("Variable");
        addCustomInput(pinVariable);

        pinValue.setNode(this);
        pinValue.setName("Value");
        addCustomInput(pinValue);

        output.setNode(this);
        addCustomOutput(output);

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
    public void copy(Node node) {
        populateCombobox();
        if(node instanceof NodeSetPlayerVariable){
            pinVariable.selectValue(((NodeSetPlayerVariable) node).pinVariable.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Var:" + pinVariable.getComboBox().getSelectedValue());
    }

    public void populateCombobox(){
        pinVariable.getComboBox().clear();
        System.out.println("Populating...");
        for (int i = 0; i < getGraph().playerVariables.size(); i++) {
            pinVariable.getComboBox().addOption(getGraph().playerVariables.get(i).name);
            System.out.println("added Variable: " + getGraph().playerVariables.get(i).name);
        }

        for (int i = 0; i < pinVariable.getComboBox().size(); i++) {
            pinVariable.getComboBox().setItemColor(i, new ImVec4(.75f, 1, .75f, 255));
        }
    }

    @Override
    public void onLoaded() {
        populateCombobox();
        for(String data : getExtraSaveData()){
            if (data.startsWith("Var")) {
                try{
                    pinVariable.getComboBox().selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinVariable.getComboBox().select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> variableData = pinVariable.getData();
        PinData<ImString> inputData = pinValue.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");
        handlePinStringConnection(pinVariable, variableData, pinVariable.getComboBox().getSelectedValue());
        handlePinStringConnection(pinValue, inputData, "0");

        outputData.getValue().set(playerData.getValue().get() + "." + variableData.getValue().get() + " = " + inputData.getValue().get() + ";");
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
        return "Stores a value into a player variable, which is a variable that belongs to a specific player.";
    }
}
