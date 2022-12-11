package ovs.graph.pin;

import imgui.ImGui;
import imgui.type.ImString;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;

public class PinCombo extends Pin{

    private PinData<ImString> data = new PinData<>();
    ComboBox comboBox = new ComboBox();

    public PinCombo(){
        setData(data);
        setColor(0.25f, 0.8f, 0.6f, 1);
        data.setValue(new ImString());

        comboBox.addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                PinData<ImString> data = getData();
                data.getValue().set(newValue);
                getNode().width = -1;
            }
        });

        comboBox.setSearchable(true);
    }

    public void sort(){
        comboBox.sort();
    }

    @Override
    public void loadValue(String value) {
        comboBox.selectValue(value);
    }

    public void addOption(String option){
        ImGui.pushItemWidth(150);
        comboBox.addOption(option);
        ImGui.popItemWidth();
    }

    @Override
    public boolean UI() {
        comboBox.show();
        return true;
    }
}
