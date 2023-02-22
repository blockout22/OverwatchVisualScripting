package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.node.interfaces.NodeGroupOnly;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCondition;
import ovs.graph.pin.PinVar;

@NodeGroupOnly()
public class NodeGroupOutput extends Node {

    PinCondition pinCondition = new PinCondition();
    PinAction pinAction = new PinAction();
    PinVar pinVar = new PinVar();

    PinCondition conditionBinding;
    PinAction actionBinding;
    PinVar varBinding;

    public NodeGroupOutput(Graph graph) {
        super(graph);
        setName("Group Output");
        setColor(167, 205, 60);

        pinCondition.setNode(this);
        addCustomInput(pinCondition);

        pinAction.setNode(this);
        addCustomInput(pinAction);

        pinVar.setNode(this);
        addCustomInput(pinVar);
    }

    @Override
    public void execute() {
        PinData<ImString> conditionData = pinCondition.getData();
        PinData<ImString> actionData = pinAction.getData();
        PinData<ImString> varData = pinVar.getData();

        handlePinStringConnection(pinCondition, conditionData);
        handlePinStringConnection(pinAction, actionData);
        handlePinStringConnection(pinVar, varData);


        if(conditionBinding != null){
            PinData<ImString> conditionBindingData = conditionBinding.getData();

            conditionBindingData.getValue().set(conditionData.getValue().get());
        }

        if(actionBinding != null){
            PinData<ImString> actionBindingData = actionBinding.getData();

            actionBindingData.getValue().set(actionData.getValue().get());
        }


        if(varBinding != null) {
            PinData<ImString> varBindingData = varBinding.getData();

            varBindingData.getValue().set(varData.getValue().get());
        }


//        outputData.getValue().set(getName());
    }

    public void bindCondition(PinCondition pin){ this.conditionBinding = pin; }

    public void bindAction(PinAction pin){
        this.actionBinding = pin;
    }

    public void bindVariable(PinVar pin){
        this.varBinding = pin;
    }


    @Override
    public String getOutput() {
        PinData<ImString> conditionData = pinCondition.getData();
        PinData<ImString> actionData = pinAction.getData();
        PinData<ImString> varData = pinVar.getData();
        return conditionData.getValue().get() + "\n" + actionData.getValue().get() + "\n" + varData.getValue().get();
    }

    @Override
    public void UI() {
    }
}
