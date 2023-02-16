package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeGroupOutput extends Node {

    PinVar input = new PinVar();

    PinVar pinBinding;

    public NodeGroupOutput(Graph graph) {
        super(graph);
        setName("Group Output");
        setColor(167, 205, 60);


        input.setNode(this);
        addCustomInput(input);
    }

    @Override
    public void execute() {
        PinData<ImString> inputData = input.getData();

        handlePinStringConnection(input, inputData);

        if(pinBinding != null) {
            PinData<ImString> bindingData = pinBinding.getData();

            bindingData.getValue().set(inputData.getValue().get());
        }


//        outputData.getValue().set(getName());
    }

    public void bind(PinVar pin){
        this.pinBinding = pin;
    }

    @Override
    public String getOutput() {
        PinData<ImString> inputData = input.getData();
        return inputData.getValue().get();
    }

    @Override
    public void UI() {

    }
}
