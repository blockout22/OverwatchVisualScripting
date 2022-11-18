package ovs.graph.node;

import ovs.graph.Graph;
import ovs.graph.UI.UiComponent;
import ovs.graph.pin.Pin;

import java.util.ArrayList;

public abstract class Node {

    public final Node self;
    private final Graph graph;

    public ArrayList<Pin> outputPins = new ArrayList<>();
    public ArrayList<Pin> inputPins = new ArrayList<>();
    public ArrayList<UiComponent> uiComponents = new ArrayList<>();

    private int ID;
    private String name = "";
    private boolean hasTitleBar = true;
    public boolean isEditingTitle = false;
    public boolean canEditTitle = false;

    public float width = -1;

    public Node(Graph graph){
        this.self = this;
        this.graph = graph;
    }

    public void addCustomInput(Pin pin){
        int id = Graph.getNextAvailablePinID();
        System.out.println("New ID: " + id);
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



    public abstract void execute();
    public abstract String getOutput();
    public abstract void UI();
}
