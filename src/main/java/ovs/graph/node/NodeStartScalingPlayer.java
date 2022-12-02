package ovs.graph.node;

import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.*;

public class NodeStartScalingPlayer extends Node{

    PinVar pinPlayer = new PinVar();
    PinVar pinScale = new PinVar();
    PinBoolean pinReeval = new PinBoolean();

    PinAction output = new PinAction();

    public NodeStartScalingPlayer(Graph graph) {
        super(graph);
        setName("Start Scaling Player");

        pinPlayer.setNode(this);
        pinPlayer.setName("Player");
        addCustomInput(pinPlayer);

        pinScale.setNode(this);
        pinScale.setName("Scale");
        addCustomInput(pinScale);

        pinReeval.setNode(this);
        pinReeval.setName("Reevaluation");
        addCustomInput(pinReeval);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> playerData = pinPlayer.getData();
        PinData<ImString> scaleData = pinScale.getData();
        PinData<ImBoolean> reelvalData = pinReeval.getData();
        PinData<ImString> outputData = output.getData();

        if(pinPlayer.isConnected()){
            Pin connectedPin = pinPlayer.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            playerData.getValue().set(connectedData.getValue().get());
        }else{
            playerData.getValue().set("Null");
        }

        if(pinScale.isConnected()){
            Pin connectedPin = pinScale.getConnectedPin();

            PinData<ImString> connectedData = connectedPin.getData();
            scaleData.getValue().set(connectedData.getValue().get());
        }

        if(pinReeval.isConnected()){
            Pin connectedPin = pinReeval.getConnectedPin();

            PinData<ImBoolean> connectedData = connectedPin.getData();
            reelvalData.getValue().set(connectedData.getValue().get());
        }

        outputData.getValue().set("Start Scaling Player(" + playerData.getValue().get() + ", " + scaleData.getValue().get() + ", " + reelvalData.getValue().get() + ");");

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
