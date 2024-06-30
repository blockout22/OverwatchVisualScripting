package ovs.graph.node;

import imgui.ImVec4;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeSetPlayerVariable extends Node{

    private PinVar pinPlayer = new PinVar();
    private PinVar inputPin = new PinVar();
    private PinAction output = new PinAction();
    public ComboBox variableBox = new ComboBox();

    //TODO allow users to select a specific players variable
    public NodeSetPlayerVariable(Graph graph) {
        super(graph);
        setName("Set Player Variable");
        setColor(0, 125, 255);

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        inputPin.setNode(this);
        inputPin.setName("Value");
        addCustomInput(inputPin);

        output.setNode(this);
        addCustomOutput(output);

        variableBox.addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                String lastSelectedValue = variableBox.getSelectedValue();
                populateCombobox();

                variableBox.selectValue(lastSelectedValue);
                width = -1;
            }
        });

        variableBox.addChangeListener(new ChangeListener() {
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
            variableBox.selectValue(((NodeSetPlayerVariable) node).variableBox.getSelectedValue());
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Var:" + variableBox.getSelectedValue());
    }

    public void populateCombobox(){
        variableBox.clear();
        for (int i = 0; i < getGraph().playerVariables.size(); i++) {
            variableBox.addOption(getGraph().playerVariables.get(i).name);
        }

        for (int i = 0; i < variableBox.size(); i++) {
            variableBox.setItemColor(i, new ImVec4(.75f, 1, .75f, 255));
        }
    }

    @Override
    public void onLoaded() {
        populateCombobox();
        for(String data : getExtraSaveData()){
            if (data.startsWith("Var")) {
                try{
                    variableBox.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    variableBox.select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> inputData = inputPin.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinPlayer, playerData, "Event Player");
        handlePinStringConnection(inputPin, inputData, "0");

        outputData.getValue().set(playerData.getValue().get() + "." + variableBox.getSelectedValue() + " = " + inputData.getValue().get() + ";");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        variableBox.show();
    }
}
