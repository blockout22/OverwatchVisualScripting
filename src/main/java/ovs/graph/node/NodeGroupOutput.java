package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.Button;
import ovs.graph.node.interfaces.NodeGroupOnly;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCondition;
import ovs.graph.pin.PinVar;

import java.util.ArrayList;

@NodeGroupOnly()
public class NodeGroupOutput extends Node {

//    PinCondition pinCondition = new PinCondition();
//    PinAction pinAction = new PinAction();
//    PinVar pinVar = new PinVar();

    private ArrayList<Pin> bindingPins = new ArrayList<>();

//    PinCondition conditionBinding;
//    PinAction actionBinding;
//    PinVar varBinding;

    Button addCondition = new Button("Add Condition");
    Button addAction = new Button("Add Action");
    Button addVariable = new Button("Add Variable");

    public NodeGroupOutput(Graph graph) {
        super(graph);
        setName("Group Output");
        setColor(167, 205, 60);

        addUiComponent(addCondition);
        addUiComponent(addAction);
        addUiComponent(addVariable);

        addCondition.addLeftClickListener(() -> {
            PinCondition pin = new PinCondition();
            pin.setNode(self);
            pin.setCanDelete(true);
            addCustomInput(pin);
        });

        addAction.addLeftClickListener(() -> {
            PinAction pin = new PinAction();
            pin.setNode(self);
            pin.setCanDelete(true);
            addCustomInput(pin);
        });

        addVariable.addLeftClickListener(() -> {
            PinVar pin = new PinVar();
            pin.setNode(self);
            pin.setCanDelete(true);
            addCustomInput(pin);
        });

//        pinCondition.setNode(this);
//        addCustomInput(pinCondition);
//
//        pinAction.setNode(this);
//        addCustomInput(pinAction);
//
//        pinVar.setNode(this);
//        addCustomInput(pinVar);
    }

    @Override
    public void execute() {
        for (int i = 0; i < bindingPins.size(); i++) {
            Pin pin = bindingPins.get(i);
            Pin input = inputPins.get(i);

            PinData<ImString> data = pin.getData();
            PinData<ImString> inputData = input.getData();

            handlePinStringConnection(input, inputData);

            data.getValue().set(inputData.getValue().get());
        }
        System.out.println(bindingPins.size());
//        PinData<ImString> conditionData = pinCondition.getData();
//        PinData<ImString> actionData = pinAction.getData();
//        PinData<ImString> varData = pinVar.getData();
//
//        handlePinStringConnection(pinCondition, conditionData);
//        handlePinStringConnection(pinAction, actionData);
//        handlePinStringConnection(pinVar, varData);
//
//
//        if(conditionBinding != null){
//            PinData<ImString> conditionBindingData = conditionBinding.getData();
//
//            conditionBindingData.getValue().set(conditionData.getValue().get());
//        }
//
//        if(actionBinding != null){
//            PinData<ImString> actionBindingData = actionBinding.getData();
//
//            actionBindingData.getValue().set(actionData.getValue().get());
//        }
//
//
//        if(varBinding != null) {
//            PinData<ImString> varBindingData = varBinding.getData();
//
//            varBindingData.getValue().set(varData.getValue().get());
//        }


//        outputData.getValue().set(getName());
    }

//    public void bindCondition(PinCondition pin){ this.conditionBinding = pin; }
//
//    public void bindAction(PinAction pin){
//        this.actionBinding = pin;
//    }
//
//    public void bindVariable(PinVar pin){
//        this.varBinding = pin;
//    }

    public void bind(Pin pin){
        bindingPins.add(pin);
    }


    @Override
    public String getOutput() {
//        PinData<ImString> conditionData = pinCondition.getData();
//        PinData<ImString> actionData = pinAction.getData();
//        PinData<ImString> varData = pinVar.getData();
//        return conditionData.getValue().get() + "\n" + actionData.getValue().get() + "\n" + varData.getValue().get();
        return "";
    }

    @Override
    public void UI() {
    }
}
