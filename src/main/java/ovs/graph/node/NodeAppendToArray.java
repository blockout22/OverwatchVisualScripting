package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeAppendToArray extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinVariable = new PinVar();
    PinVar input = new PinVar();

    PinAction output = new PinAction();

    public NodeAppendToArray(Graph graph) {
        super(graph);
        setName("Append to Array");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
//        pinPlayer.setVisible(false);
        addCustomInput(pinPlayer);

        pinVariable.setNode(this);
        pinVariable.setName("Variable");
        addCustomInput(pinVariable);

        input.setNode(this);
        input.setName("Data");
        addCustomInput(input);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> varData = pinVariable.getData();
        PinData<ImString> inputData = input.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinVariable, varData);
        handlePinStringConnection(input, inputData);

//        System.out.println(varData);

        String type = "";
        String var = "";

        if(varData.getValue().get().contains(".")){
            if(varData.getValue().get().startsWith("Event Player")){
                type = "Player";
            }else if (varData.getValue().get().startsWith("Global")){
                type = "Global";
            }

            var = varData.getValue().get().split("\\.")[1];
        }

        if(type.equals("Player")) {
            pinPlayer.setVisible(true);
            PinData<ImString> playerData = pinPlayer.getData();

            handlePinStringConnection(pinPlayer, playerData);

            outputData.getValue().set("Modify " + (type) +" Variable(" + playerData.getValue().get() + ", " + var + ", Append To Array, " + inputData.getValue().get() + ");");
        }else{
            pinPlayer.setVisible(false);
            if(pinPlayer.isConnected()){
                pinPlayer.disconnectAll();
            }
            outputData.getValue().set("Modify " + (type) +" Variable(" + var + ", Append To Array, " + inputData.getValue().get() + ");");
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
}
