package ovs.graph.node;

import imgui.type.ImString;
import ovs.graph.AdvancedArrayList;
import ovs.graph.Graph;
import ovs.graph.PinData;
import ovs.graph.UI.UiComponent;
import ovs.graph.pin.Pin;

import java.util.ArrayList;

public abstract class Node {

    public final Node self;
    private final Graph graph;

    public AdvancedArrayList<Pin> outputPins = new AdvancedArrayList<>();
    public AdvancedArrayList<Pin> inputPins = new AdvancedArrayList<>();
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

    private int Red = 255;
    private int Green = 255;
    private int Blue = 255;
    private int Alpha = 45;

    private ImString comment = new ImString();

    public Node(Graph graph){
        this.self = this;
        this.graph = graph;

        setComment("");
    }

    public void onSaved(){
    }

    public void onLoaded(){};

    public void addCustomInput(Pin pin){
        int id = Graph.getNextAvailablePinID();
//        pin.setNode(this);
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

    public void setColor(int red, int green, int blue){
        this.Red = red;
        this.Blue = blue;
        this.Green = green;
    }

    public void setColor(int red, int green, int blue, int alpha){
        this.Red = red;
        this.Blue = blue;
        this.Green = green;
        this.Alpha = alpha;
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

    public int getRed() {
        return Red;
    }

    public void setRed(int red) {
        Red = red;
    }

    public int getGreen() {
        return Green;
    }

    public void setGreen(int green) {
        Green = green;
    }

    public int getBlue() {
        return Blue;
    }

    public void setBlue(int blue) {
        Blue = blue;
    }

    public int getAlpha() {
        return Alpha;
    }

    public void setAlpha(int alpha) {
        Alpha = alpha;
    }

    public ImString getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public String getFormattedComment() {
        if (getComment().get().length() > 0) {
            return "\"" + getComment().get() + "\"\n";
        }
        return "";
    }

    public abstract String getTooltip();
}
