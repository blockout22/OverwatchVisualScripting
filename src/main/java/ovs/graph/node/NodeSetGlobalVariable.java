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

public class NodeSetGlobalVariable extends Node{

    private PinCombo pinVariable = new PinCombo();
    private PinVar inputPin = new PinVar();
    private PinAction output = new PinAction();

    //TODO allow users to select a specific players variable
    public NodeSetGlobalVariable(Graph graph) {
        super(graph);
        setName("Set Global Variable");
        setColor(0, 125, 255);

        pinVariable.setNode(this);
        pinVariable.setName("Variable");
        addCustomInput(pinVariable);

        inputPin.setNode(this);
        inputPin.setName("Value");
        addCustomInput(inputPin);

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
        if(node instanceof NodeSetGlobalVariable){
            pinVariable.selectValue(((NodeSetGlobalVariable) node).pinVariable.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Var:" + pinVariable.getComboBox().getSelectedValue());
    }

    public void populateCombobox(){
        pinVariable.getComboBox().clear();
        for (int i = 0; i < getGraph().globalVariables.size(); i++) {
            pinVariable.getComboBox().addOption(getGraph().globalVariables.get(i).name);
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
                    pinVariable.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinVariable.select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> variableData = pinVariable.getData();
        PinData<ImString> inputData = inputPin.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVariable, variableData, pinVariable.getComboBox().getSelectedValue());
        handlePinStringConnection(inputPin, inputData, "0");

        outputData.getValue().set("Global" + "." + variableData.getValue().get() + " = " + inputData.getValue().get() + ";");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI(){
    }

    @Override
    public String getTooltip() {
        return "Stores a value into a global variable, which is a variable that belongs to the game itself.";
    }
}
