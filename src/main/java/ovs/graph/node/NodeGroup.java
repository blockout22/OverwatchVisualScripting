package ovs.graph.node;

import imgui.type.ImString;
import ovs.Global;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.ComboBox;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinAction;
import ovs.graph.pin.PinCondition;
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

                for (int i = 0; i < inputPins.size(); i++) {
                    Pin pin = inputPins.get(i);
                    pin.disconnectAll();
                }

                for (int i = 0; i < outputPins.size(); i++) {
                    Pin pin = outputPins.get(i);
                    pin.disconnectAll();
                }

                createPins();
            }
        });
    }

    private void createPins(){
        GroupSaver gs = new GroupSaver();
        Graph g = gs.load(group.getSelectedValue());

        if(g == null){
            return;
        }

        inputPins.clear();
        outputPins.clear();

        groupGraph = g;

        //find Input Nodes

        for (Node node : g.getNodes().getList()){

            if(node instanceof NodeGroupInput){

                PinVar pinVar = new PinVar();
                pinVar.setNode(self);
                pinVar.setName(node.getName());
                addCustomInput(pinVar);

                ((NodeGroupInput) node).bind(pinVar);
            }

            if(node instanceof NodeGroupOutput){
                PinCondition pinCondition = new PinCondition();
                pinCondition.setNode(self);
                addCustomOutput(pinCondition);

                PinAction pinAction = new PinAction();
                pinAction.setNode(self);
                addCustomOutput(pinAction);

                PinVar pinVar = new PinVar();
                pinVar.setNode(self);
                addCustomOutput(pinVar);

                ((NodeGroupOutput) node).bindCondition(pinCondition);
                ((NodeGroupOutput) node).bindAction(pinAction);
                ((NodeGroupOutput) node).bindVariable(pinVar);
            }
        }
    }

    private void loadAndBind(){
        GroupSaver gs = new GroupSaver();
        Graph g = gs.load(group.getSelectedValue());

        if(g == null){
            return;
        }

        groupGraph = g;

        int inputIndex = 0;
        for(Node node : groupGraph.getNodes().getList()){
            if(node instanceof NodeGroupInput){
                ((NodeGroupInput) node).bind((PinVar) inputPins.get(inputIndex));
                inputIndex++;
            }

            if(node instanceof NodeGroupOutput){
                for (int i = 0; i < node.inputPins.size(); i++) {
                    Pin pin = node.inputPins.get(i);

                    if(pin instanceof PinCondition){
                        for (int j = 0; j < outputPins.size(); j++) {
                            Pin outputPin = outputPins.get(j);

                            if(outputPin instanceof PinCondition){
                                ((NodeGroupOutput) node).bindCondition((PinCondition) outputPin);
                            }
                        }
                    }

                    if(pin instanceof PinAction){
                        for (int j = 0; j < outputPins.size(); j++) {
                            Pin outputPin = outputPins.get(j);

                            if(outputPin instanceof PinAction){
                                ((NodeGroupOutput) node).bindAction((PinAction) outputPin);
                            }
                        }
                    }

                    if(pin instanceof PinVar){
                        for (int j = 0; j < outputPins.size(); j++) {
                            Pin outputPin = outputPins.get(j);

                            if(outputPin instanceof PinVar){
                                ((NodeGroupOutput) node).bindVariable((PinVar) outputPin);
                            }
                        }
                    }
                }
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

                    loadAndBind();

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
            for(Node node : groupGraph.getNodes().getList()){
                node.execute();
            }
        }
    }

    @Override
    public String getOutput() {
        if(groupGraph != null){
            for(Node node : groupGraph.getNodes().getList()){
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
