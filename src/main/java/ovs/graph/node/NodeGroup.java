package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.PinVar;
import ovs.graph.save.GroupSaver;

import java.io.File;

public class NodeGroup extends Node{

    private ComboBox group = new ComboBox();

    private Graph groupGraph = null;

    public NodeGroup(Graph graph) {
        super(graph);
        setName("Node Group");
        createPins();

        group.addOnOpenedListener(new OnOpenedListener() {
            @Override
            public void onOpen() {
                String lastSelectedValue = group.getSelectedValue();
                populateCombobox();

                group.selectValue(lastSelectedValue);
                width = -1;
            }
        });

        group.addChangeListener(new ChangeListener() {
            @Override
            public void onChanged(String oldValue, String newValue) {
                width = -1;
                //TODO Pins are created again after loading from file thus new pin IDs are assigned
                createPins();
            }
        });
    }

    private void createPins(){
        inputPins.clear();
        outputPins.clear();

        GroupSaver gs = new GroupSaver();
        Graph g = gs.load("TestGroupFile");
        groupGraph = g;

        //find Input Nodes

        for (Node node : g.getNodes().values()){
            if(node instanceof NodeGroupInput){

                PinVar pinVar = new PinVar();
                pinVar.setNode(self);
                pinVar.setName(node.getName());
                addCustomInput(pinVar);

                ((NodeGroupInput) node).bind(pinVar);
            }

            if(node instanceof NodeGroupOutput){
                PinVar pinVar = new PinVar();
                pinVar.setNode(self);
                addCustomOutput(pinVar);

                ((NodeGroupOutput) node).bind(pinVar);
            }
        }
    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("Group:" + group.getSelectedValue());
    }

    @Override
    public void onLoaded() {
        populateCombobox();
        for(String data : getExtraSaveData()){
            if(data.startsWith("Group")){
                try {
                    group.silentSelectValue(data.split(":")[1]);
                }catch (ArrayIndexOutOfBoundsException e){
                    group.select(-1);
                }
            }
        }
    }

    private void populateCombobox(){
        group.clear();

        File groupDir = new File(Global.NODE_GROUP_DIR);

        for(File fileGroup : groupDir.listFiles()){
            group.addOption(fileGroup.getName());
        }
    }

    @Override
    public void execute() {
        if(groupGraph != null){
            for(Node node : groupGraph.getNodes().values()){
                node.execute();
            }
        }
    }

    @Override
    public String getOutput() {
        if(groupGraph != null){
            for(Node node : groupGraph.getNodes().values()){
                if(node instanceof NodeGroupOutput){
                    return node.getOutput();
                }
            }
        }
        return "";
    }

    @Override
    public void UI() {
        group.show();
    }
}
