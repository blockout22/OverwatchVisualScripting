package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinVar;

public class NodeWorkshopSettingHero extends Node {

    PinVar pinCategory = new PinVar();
    PinVar pinName = new PinVar();
    PinCombo pinDefault = new PinCombo();
    PinVar pinSortOrder = new PinVar();
    PinVar output = new PinVar();

    public NodeWorkshopSettingHero(Graph graph) {
        super(graph);
        setName("Workshop Setting Hero");

        pinCategory.setNode(this);
        pinCategory.setName("Category");
        addCustomInput(pinCategory);

        pinName.setNode(this);
        pinName.setName("Name");
        addCustomInput(pinName);

        pinDefault.setNode(this);
        pinDefault.setName("Default Value");
        addCustomInput(pinDefault);

        pinSortOrder.setNode(this);
        pinSortOrder.setName("Sort Order");
        addCustomInput(pinSortOrder);

        output.setNode(this);
        addCustomOutput(output);

        for (int i = 0; i < Global.heroes.size(); i++) {
            pinDefault.addOption(Global.heroes.get(i));
        }

        pinDefault.select(0);
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Default:" + pinDefault.getComboBox().getSelectedValue());
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("Default")){
                try {
                    pinDefault.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    pinDefault.select(0);
                }
            }
        }
    }

    @Override
    public void copy(Node node) {
        if(node instanceof NodeWorkshopSettingHero){
            pinDefault.selectValue(((NodeWorkshopSettingHero) node).pinDefault.getComboBox().getSelectedValue());
        }
    }

    @Override
    public void execute() {
        PinData<ImString> categoryData = pinCategory.getData();
        PinData<ImString> nameData = pinName.getData();
        PinData<ImString> sortOrderData = pinSortOrder.getData();
        PinData<ImString> outputData = output.getData();

        handlePinStringConnection(pinCategory, categoryData, "Custom String(\"\")");
        handlePinStringConnection(pinName, nameData, "Custom String(\"\")");
        handlePinStringConnection(pinSortOrder, sortOrderData, "0");


        outputData.getValue().set(getName() + "(" + categoryData.getValue().get() + ", " + nameData.getValue().get() + ", " + pinDefault.getComboBox().getSelectedValue() + ", " + sortOrderData.getValue().get() + ")");
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