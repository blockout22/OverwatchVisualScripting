package ovs.graph.pin;

import imgui.type.ImInt;
import imgui.type.ImString;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;

public class PinOnGoing extends Pin{

    private String[] items = {"Global", "Each Player"};
    private ImInt currentItem = new ImInt();

    private PinData<ImString> data = new PinData<>();

    ComboBox comboBox = new ComboBox();

    public PinOnGoing() {
        setColor(1, 0.47f, 0f, 1f);

        setData(data);
        data.setValue(new ImString());

        comboBox.addOption("Global");
        comboBox.addOption("Each Player");

        comboBox.addChangeListener(new ChangeListener() {
            @Override
            public void onChanged() {
                data.getValue().set(comboBox.getSelectedValue());
            }
        });

        comboBox.select(0);
    }

    @Override
    public boolean UI() {
        comboBox.show();

        return true;
    }
}
