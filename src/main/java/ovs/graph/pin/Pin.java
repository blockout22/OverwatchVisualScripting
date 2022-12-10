package ovs.graph.pin;

import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImVec4;
import ovs.graph.PinData;
import ovs.graph.node.Node;

public abstract class Pin {

    public enum PinType{
        Output,
        Input
    }

    private Node node;

    public ImVec4 color = new ImVec4(1f, 1f, 1f, 1f);
    public int connectedTo = -1;

    private int ID;
    private String name = "";
    private PinType pinType;
    private PinData data;

    private boolean canDelete = false;

    public Pin()
    {

    }

    public boolean connect(Pin targetPin){
        //remove old connections
        if (connectedTo != -1) {
            Pin oldPin = getNode().getGraph().findPinById(connectedTo);
            if(oldPin != null) {
                oldPin.connectedTo = -1;
            }
        }

        if (targetPin.connectedTo != -1) {
            Pin oldPin = getNode().getGraph().findPinById(targetPin.connectedTo);
            if(oldPin != null) {
                oldPin.connectedTo = -1;
            }
        }
        if (connectedTo != targetPin.connectedTo || (targetPin.connectedTo == -1 || connectedTo == -1)) {
            connectedTo = targetPin.getID();
            targetPin.connectedTo = getID();
            return true;
        }
        return false;
    }

    public void draw(ImDrawList windowDrawList, float posX, float posY, boolean isConnected, boolean pinDragSame){
        drawDefaultCircle(windowDrawList,posX,posY,isConnected,pinDragSame);
    }

    public void drawDefaultCircle(ImDrawList windowDrawList, float posX, float posY, boolean isConnected, boolean pinDragSame){
        float size = 10f;
        int doubleGrey = pinDragSame ? rgbToInt(color.x, color.y, color.z, color.w) : rgbToInt(50, 50, 50, 255);
        if(isConnected) {
            windowDrawList.addCircleFilled(posX + (size / 2), posY + (size / 2), size / 2, doubleGrey);
        }else{
            windowDrawList.addCircle(posX + (size / 2), posY + (size / 2), size / 2, doubleGrey);
        }
    }

    public void loadValue(String value){

    }

    public abstract boolean UI();

    public void setPinType(PinType pinType){
        this.pinType = pinType;
    }

    public int rgbToInt(float r, float g, float b, float a){
        return ImColor.floatToColor(r, g, b, a);
    }

    public void setNode(Node node){
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
        getNode().getGraph().setHighestPinID(++id);
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public ImVec4 getColor() {
        return color;
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    public PinType getPinType() {
        return pinType;
    }

    public PinData getData() {
        return data;
    }

    public void setData(PinData data) {
        this.data = data;
    }

    public boolean isConnected(){
        return connectedTo != -1;// && node.getGraph().findPinById(connectedTo) != null;
    }

    public Pin getConnectedPin(){
        if(!isConnected()){
            return null;
        }

        return node.getGraph().findPinById(connectedTo);
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }
}
