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

public class NodeSetVariable extends Node{

    PinAction output = new PinAction();
    PinVar inputPin = new PinVar();
    private ComboBox comboBox = new ComboBox();

    //TODO allow users to select a specific players variable
    public NodeSetVariable(Graph graph) {
        super(graph);
        setName("Set Variable");
        setColor(0, 125, 255);

        inputPin.setNode(this);
        inputPin.setName("Value");
        addCustomInput(inputPin);

        output.setNode(this);
        addCustomOutput(output);

        comboBox.addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                String lastSelectedValue = comboBox.getSelectedValue();
                populateCombobox();

                comboBox.selectValue(lastSelectedValue);
                width = -1;
            }
        });

        comboBox.addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                width = -1;
            }
        });
    }

    @Override
    public void copy(Node node) {
        populateCombobox();
        if(node instanceof NodeSetVariable){
            comboBox.selectValue(((NodeSetVariable) node).comboBox.getSelectedValue());
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        //TODO get variable class and save ID and type
        getExtraSaveData().add("Var:" + comboBox.getSelectedValue());
    }

    private void populateCombobox(){
        comboBox.clear();
        for (int i = 0; i < getGraph().globalVariables.size(); i++) {
            comboBox.addOption("Global." + getGraph().globalVariables.get(i).name);
        }

        for (int i = 0; i < getGraph().playerVariables.size(); i++) {
            comboBox.addOption("Event Player." + getGraph().playerVariables.get(i).name);
        }

        for (int i = 0; i < comboBox.size(); i++) {
            String option = comboBox.getOptions()[i];
            if(option.startsWith("Global")){
                comboBox.setItemColor(i, new ImVec4(.75f, .75f, .75f, 255));
            }else{
                comboBox.setItemColor(i, new ImVec4(.75f, 1, .75f, 255));
            }
        }
    }

    @Override
    public void onLoaded() {
        populateCombobox();
        for(String data : getExtraSaveData()){
            if (data.startsWith("Var")) {
//                int index = Integer.valueOf(data.split(":")[1]);
//                if(index > -1){
//                    comboBox.select(index);
//                }
                try{
                    comboBox.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    comboBox.select(-1);
                }
            }
        }
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = inputPin.getData();
        PinData<ImString> outputData = output.getData();

        if(inputPin.isConnected()){
            Pin connectedPin = inputPin.getConnectedPin();

            if(connectedPin != null) {
                PinData<ImString> connectedData = connectedPin.getData();

                inputData.getValue().set(connectedData.getValue().get());
            }
        }

        if (output.isConnected() && comboBox.size() > 0 && comboBox.getSelectedIndex() != -1) {
//            PinData<ImString> data = outputPin.getData();
//            String[] val = comboBox.getSelectedValue().split(":");
//            data.getValue().set(val[0] + "." + val[1].replace(" ", ""));
            outputData.getValue().set(comboBox.getSelectedValue() + " = " + inputData.getValue().get() + ";");
        }


    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
//        if(inputPin.isConnected() && (comboBox.getSelectedIndex() != -1)) {
//
//            PinData<ImString> data = inputPin.getData();
//
////            String[] val = comboBox.getSelectedValue().split(":");
////            return val[0] + "." + val[1].replace(" ", "") + " = " + data.getValue().get() + ";";
//            return data.getValue().get();
//        }

        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
