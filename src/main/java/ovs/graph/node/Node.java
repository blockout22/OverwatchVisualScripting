package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.UiComponent;
import ovs.graph.pin.Pin;

import java.util.ArrayList;

public abstract class Node {

    public final Node self;
    private final Graph graph;

    public ArrayList<Pin> outputPins = new ArrayList<>();
    public ArrayList<Pin> inputPins = new ArrayList<>();
    public ArrayList<UiComponent> uiComponents = new ArrayList<>();
    private ArrayList<String> extraSaveData = new ArrayList<>();

    private int ID;
    private String name = "";
    private boolean hasTitleBar = true;
    public boolean isEditingTitle = false;
    public boolean canEditTitle = false;

    public float width = -1;

    public float posX = 0;
    public float posY = 0;

    public Node(Graph graph){
        this.self = this;
        this.graph = graph;
    }

    public void onSaved(){
    }

    public void onLoaded(){};

    public void addCustomInput(Pin pin){
        int id = Graph.getNextAvailablePinID();
        pin.setID(id);
        pin.setPinType(Pin.PinType.Input);
        inputPins.add(pin);
    }

    public void addCustomOutput(Pin pin){
        int id = Graph.getNextAvailablePinID();
        pin.setID(id);
        pin.setPinType(Pin.PinType.Output);
        outputPins.add(pin);
    }

    public boolean removePinById(int id){
        boolean found = false;
        for (int i = 0; i < inputPins.size(); i++) {
            Pin targetPin = inputPins.get(i);
            if(targetPin.getID() == id){
                found = true;
                if(targetPin.isConnected()){
                    for (int j = 0; j < targetPin.connectedToList.size(); j++) {
                        Pin connection = getGraph().findPinById(targetPin.connectedToList.get(j));
                        connection.remove(targetPin.getID());
                    }
                }
//                if(targetPin.connectedTo != -1){
//                    Pin connection = getGraph().findPinById(targetPin.connectedTo);
//                    connection.connectedTo = -1;
//                }
//                targetPin.connectedTo = -1;
                inputPins.remove(i);
                break;
            }
        }

        if(!found){
            for (int i = 0; i < outputPins.size(); i++) {
                Pin targetPin = outputPins.get(i);
                if(targetPin.getID() == id){
                    found = true;

                    if(targetPin.isConnected()){
                        for (int j = 0; j < targetPin.connectedToList.size(); j++) {
                            Pin connection = getGraph().findPinById(targetPin.connectedToList.get(j));
                            connection.remove(targetPin.getID());
                        }
                    }
//                    if(targetPin.connectedTo != -1){
//                        Pin connection = getGraph().findPinById(targetPin.connectedTo);
//                        connection.connectedTo = -1;
//                    }
//                    targetPin.connectedTo = -1;
                    outputPins.remove(i);
                    break;
                }
            }
        }

        return found;
    }

    //I seem to be writing this a lot so I will try using these functions instead
    protected void handlePinStringConnection(Pin pin, PinData<ImString> pinData){
        if(pin.isConnected()){
            Pin connectedPin = pin.getConnectedPin();

            if(connectedPin != null) {
                PinData<ImString> connectedData = connectedPin.getData();
                pinData.getValue().set(connectedData.getValue().get());
            }
        }else{
            pinData.getValue().set("");
        }
    }

    protected void handlePinStringConnection(Pin pin, PinData<ImString> pinData, String defaultValue){
        if(pin.isConnected()){
            Pin connectedPin = pin.getConnectedPin();

            if(connectedPin != null) {
                PinData<ImString> connectedData = connectedPin.getData();
                pinData.getValue().set(connectedData.getValue().get());
            }
        }else{
            pinData.getValue().set(defaultValue);
        }
    }

    public void addUiComponent(UiComponent uiComponent){
        this.uiComponents.add(uiComponent);
    }

    public int getID(){
        return ID;
    }

    public void setID(int id){
        this.ID = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Graph getGraph()
    {
        return graph;
    }

    public boolean hasTitleBar() {
        return hasTitleBar;
    }

    public void setHasTitleBar(boolean hasTitleBar) {
        this.hasTitleBar = hasTitleBar;
    }

    public ArrayList<String> getExtraSaveData() {
        return extraSaveData;
    }

    /**
     * used to handle the copying of pin data to new node e.g. when you duplicate a node
     * @param node
     */
    public void copy(Node node){}

    public abstract void execute();
    public abstract String getOutput();
    public abstract void UI();
}
