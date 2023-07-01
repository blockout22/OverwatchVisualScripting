package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinAction;

public class NodeCallSubroutine extends Node{

    PinAction output = new PinAction();

    ComboBox comboBox = new ComboBox();

    public NodeCallSubroutine(Graph graph) {
        super(graph);
        setName("Call Subroutine");

        output.setNode(this);
        addCustomOutput(output);

        comboBox.addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                String lastSelectedValue = comboBox.getSelectedValue();

                comboBox.clear();
                for (int i = 0; i < getGraph().subroutines.size(); i++) {
                    comboBox.addOption(getGraph().subroutines.get(i));
                }

                comboBox.selectValue(lastSelectedValue);
                width = -1;
            }
        });

        comboBox.addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                width = -1;
            }
        });
    }

    @Override
    public void execute() {
        PinData<ImString> outputData = output.getData();

        if(comboBox.getSelectedIndex() != -1) {
            outputData.getValue().set("Call Subroutine(" + comboBox.getSelectedValue() + ");");
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Sub:" + comboBox.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        comboBox.clear();
        for (int i = 0; i < getGraph().subroutines.size(); i++) {
            comboBox.addOption(getGraph().subroutines.get(i));
        }

        for(String data : getExtraSaveData()){
            if(data.startsWith("Sub")){
                try{
                    comboBox.selectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    comboBox.select(-1);
                }
            }
        }
    }

    @Override
    public String getOutput() {
        PinData<ImString> outputData = output.getData();
        return outputData.getValue().get();
    }

    @Override
    public void UI() {
        comboBox.show();
    }
}
