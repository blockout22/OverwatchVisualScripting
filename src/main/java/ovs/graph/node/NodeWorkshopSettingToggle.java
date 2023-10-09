package ovs.graph.node;

import imgui.type.ImBoolean;
import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinBoolean;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeWorkshopSettingToggle extends Node {

    PinVar pinCategory = new PinVar();
    PinVar pinName = new PinVar();
    PinBoolean pinBool = new PinBoolean();
    PinVar pinSortOrder = new PinVar();
    PinVar output = new PinVar();

    public NodeWorkshopSettingToggle(Graph graph) {
        super(graph);
        setName("Workshop Setting Toggle");

        pinCategory.setNode(this);
        pinCategory.setName("Category");
        addCustomInput(pinCategory);

        pinName.setNode(this);
        pinName.setName("Name");
        addCustomInput(pinName);

        pinBool.setNode(this);
        pinBool.setName("Default Value");
        addCustomInput(pinBool);

        pinSortOrder.setNode(this);
        pinSortOrder.setName("Sort Order");
        addCustomInput(pinSortOrder);

        output.setNode(this);
        addCustomOutput(output);

    }



    @Override
    public void execute() {
        PinData<ImString> categoryData = pinCategory.getData();
        PinData<ImString> nameData = pinName.getData();
        PinData<ImBoolean> boolData = pinBool.getData();
        PinData<ImString> sortOrderData = pinSortOrder.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinCategory, categoryData, "Custom String(\"\")");
        handlePinStringConnection(pinName, nameData, "Custom String(\"\")");
        handlePinStringConnection(pinSortOrder, sortOrderData, "0");


        outputData.getValue().set(getName() + "(" + categoryData.getValue().get() + ", " + nameData.getValue().get() + ", " + (boolData.getValue().get() ? "True" : "False") + ", " + sortOrderData.getValue().get() + ");");
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