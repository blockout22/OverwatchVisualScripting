package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.node.interfaces.NodeGroupOnly;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinVar;

@NodeGroupOnly
public class NodeGroupInput extends Node {

    PinVar output = new PinVar();

    PinVar pinBinding = null;

    public NodeGroupInput(Graph graph) {
        super(graph);
        setName("Group Input");
        canEditTitle = true;
        setColor(167, 205, 60);


        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        if(pinBinding != null){
            PinData<ImString> bindingData = pinBinding.getData();
            PinData<ImString> outputData = output.getData();

            handlePinStringConnection(pinBinding, bindingData);

            outputData.getValue().set(bindingData.getValue().get());
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

    public void bind(PinVar pin){
        this.pinBinding = pin;
    }
}