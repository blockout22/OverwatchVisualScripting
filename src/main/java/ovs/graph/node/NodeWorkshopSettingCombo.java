package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinVar;

public class NodeWorkshopSettingCombo extends Node {

    PinVar pinCategory = new PinVar();
    PinVar pinName = new PinVar();
    PinVar pinDefault = new PinVar();
    PinVar pinOptions = new PinVar();
    PinVar pinSortOrder = new PinVar();
    PinVar output = new PinVar();

    public NodeWorkshopSettingCombo(Graph graph) {
        super(graph);
        setName("Workshop Setting Combo");

        pinCategory.setNode(this);
        pinCategory.setName("Category");
        addCustomInput(pinCategory);

        pinName.setNode(this);
        pinName.setName("Name");
        addCustomInput(pinName);

        pinDefault.setNode(this);
        pinDefault.setName("Default Value");
        addCustomInput(pinDefault);

        pinOptions.setNode(this);
        pinOptions.setName("Options");
        addCustomInput(pinOptions);

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
        PinData<ImString> defaultValueData = pinDefault.getData();
        PinData<ImString> optionsData = pinOptions.getData();
        PinData<ImString> sortOrderData = pinSortOrder.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinCategory, categoryData, "Custom String(\"\")");
        handlePinStringConnection(pinName, nameData, "Custom String(\"\")");
        handlePinStringConnection(pinDefault, defaultValueData, "0");
        handlePinStringConnection(pinOptions, optionsData, "Array()");
        handlePinStringConnection(pinSortOrder, sortOrderData, "0");


        outputData.getValue().set(getName() + "(" + categoryData.getValue().get() + ", " + nameData.getValue().get() + ", " + defaultValueData.getValue().get() + ", " + optionsData.getValue().get() + ", " + sortOrderData.getValue().get() + ")");
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
        return "Provides the value (a choice of custom strings) of a new option setting that will appear in the workshop settings card as a combo box, this value returns the index of the select choice.";
    }
}