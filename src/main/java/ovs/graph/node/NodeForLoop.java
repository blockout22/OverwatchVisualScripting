package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinVar;

public class NodeForLoop extends Node {

    PinVar pinPlayer = new PinVar();
    PinVar pinControl = new PinVar();
    PinVar pinRangeStart = new PinVar();
    PinVar pinRangeStop = new PinVar();
    PinVar pinStep = new PinVar();
    PinAction input = new PinAction();
    PinAction output = new PinAction();

    public NodeForLoop(Graph graph) {
        super(graph);
        setName("For Loop");

        pinPlayer.setNode(this);
        pinPlayer.setName("Control Player");
        pinPlayer.setVisible(false);
        addCustomInput(pinPlayer);

        pinControl.setNode(this);
        pinControl.setName("Control Variable");
        addCustomInput(pinControl);

        pinRangeStart.setNode(this);
        pinRangeStart.setName("Range Start");
        addCustomInput(pinRangeStart);

        pinRangeStop.setNode(this);
        pinRangeStop.setName("Range Stop");
        addCustomInput(pinRangeStop);

        pinStep.setNode(this);
        pinStep.setName("Step");
        addCustomInput(pinStep);

        input.setNode(this);
        input.setName("Inside Loop");
        addCustomInput(input);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> varData = pinControl.getData();
        PinData<ImString> rangeStartData = pinRangeStart.getData();
        PinData<ImString> rangeStopData = pinRangeStop.getData();
        PinData<ImString> stepData = pinStep.getData();
        PinData<ImString> inputData = input.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinControl, varData);
        handlePinStringConnection(pinRangeStart, rangeStartData, "0");
        handlePinStringConnection(pinRangeStop, rangeStopData, "1");
        handlePinStringConnection(pinStep, stepData, "1");
        handlePinStringConnection(input, inputData);

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

            outputData.getValue().set("For " + type + " Variable(" + playerData.getValue().get() + ", " + var + ", " + rangeStartData.getValue().get() + ", " + rangeStopData.getValue().get() + ", " + stepData.getValue().get() + ");\n" + inputData.getValue().get() + "\nEnd;");
        }else{
            pinPlayer.setVisible(false);
            if(pinPlayer.isConnected()){
                pinPlayer.disconnectAll();
            }
            outputData.getValue().set("For " + type + " Variable(" + var + ", " + rangeStartData.getValue().get() + ", " + rangeStopData.getValue().get() + ", " + stepData.getValue().get() + ");\n" + inputData.getValue().get() + "\nEnd;");
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