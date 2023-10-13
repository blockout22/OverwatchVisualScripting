package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.Button;
import ovs.graph.UI.Listeners.LeftClickListener;
import ovs.graph.node.interfaces.NodeGroupOnly;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCondition;
import ovs.graph.pin.PinVar;

import java.util.ArrayList;

@NodeGroupOnly
public class NodeGroupInput extends Node {

//    PinVar output = new PinVar();

//    PinVar pinBinding = null;

    private ArrayList<Pin> bindingPins = new ArrayList<>();

//    ArrayList<PinCondition> pinConditions = new ArrayList<>();
//    ArrayList<PinVar> pinVars = new ArrayList<>();
//    ArrayList<PinAction> pinActions = new ArrayList<>();

    Button addCondition = new Button("Add Condition");
    Button addAction = new Button("Add Action");
    Button addVariable = new Button("Add Variable");

    public NodeGroupInput(Graph graph) {
        super(graph);
        setName("Group Input");
        canEditTitle = true;
        setColor(167, 205, 60);

//        output.setNode(this);
//        addCustomOutput(output);

        addUiComponent(addCondition);
        addUiComponent(addAction);
        addUiComponent(addVariable);

        addCondition.addLeftClickListener(() -> {
            PinCondition pin = new PinCondition();
            pin.setNode(self);
            pin.setCanDelete(true);
            addCustomOutput(pin);
        });

        addAction.addLeftClickListener(() -> {
            PinAction pin = new PinAction();
            pin.setNode(self);
            pin.setCanDelete(true);
            addCustomOutput(pin);
        });

        addVariable.addLeftClickListener(() -> {
            PinVar pin = new PinVar();
            pin.setNode(self);
            pin.setCanDelete(true);
            addCustomOutput(pin);
        });
    }

    @Override
    public void execute() {
        for (int i = 0; i < bindingPins.size(); i++) {
            Pin pin = bindingPins.get(i);

            PinData<ImString> data = pin.getData();
            PinData<ImString> outputData = outputPins.get(i).getData();

            handlePinStringConnection(pin, data);

            outputData.getValue().set(data.getValue().get());
        }
//        if(pinBinding != null){
//            PinData<ImString> bindingData = pinBinding.getData();
//            PinData<ImString> outputData = output.getData();
//
//            handlePinStringConnection(pinBinding, bindingData);
//
//            outputData.getValue().set(bindingData.getValue().get());
//            System.out.println("Outputting");
//        }
    }

    @Override
    public String getOutput() {
//        PinData<ImString> outputData = output.getData();

//        return outputData.getValue().get();
        return "";
    }

    @Override
    public void UI() {

    }

//    public void bind(PinVar pin){
//        this.pinBinding = pin;
//    }

    public void bind(Pin pin){
        bindingPins.add(pin);
//        outputPins.set(index, pin);
    }

//    public void bind(PinCondition pin){
//        this.pinBinding = pin;
//    }
//
//    public void bind(PinVar pin){
//        this.pinBinding = pin;
//    }
//
//    public void bind(PinAction pin){
//        this.pinBinding = pin;
//    }
}