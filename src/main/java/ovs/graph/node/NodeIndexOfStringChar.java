package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeIndexOfStringChar extends Node {

    PinVar pinString = new PinVar();
    PinVar pinCharacter = new PinVar();
    PinVar output = new PinVar();

    public NodeIndexOfStringChar(Graph graph) {
        super(graph);
        setName("Index Of String Char");

        pinString.setNode(this);
        pinString.setName("String");
        addCustomInput(pinString);

        pinCharacter.setNode(this);
        pinCharacter.setName("Character");
        addCustomInput(pinCharacter);

        output.setNode(this);
        addCustomOutput(output);
    }

    @Override
    public void execute() {
        PinData<ImString> stringData = pinString.getData();
        PinData<ImString> characterData = pinCharacter.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinString, stringData);
        handlePinStringConnection(pinCharacter, characterData);

        outputData.getValue().set("Index Of String Char(" + stringData.getValue().get() + ", " + characterData.getValue().get() + ")");
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {

    }

    @Override
    public String getTooltip() {
        return "The index of a character within a string or -1 if no such character can be found.";
    }
}