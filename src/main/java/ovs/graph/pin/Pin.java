package ovs.graph.pin;

import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImVec4;
import ovs.graph.PinData;
import ovs.graph.node.Node;

import java.util.ArrayList;

public abstract class Pin {

    public enum PinType{
        Output,
        Input
    }

    private Node node;

    public ImVec4 color = new ImVec4(1f, 1f, 1f, 1f);
//    public int connectedTo = -1;

    public ArrayList<Integer> connectedToList = new ArrayList<>();

    private int ID;
    private String name = "";
    private PinType pinType;
    private PinData data;

    public float pinSize = 12f;

    private boolean canDelete = false;
    private boolean isVisible = true;

    public Pin()
    {
    }

    public boolean connect(Pin targetPin){
        //remove old connections

        // remove pin connections from inputs as they shouldn't have multiple pins
        if(pinType == PinType.Input){
            if(isConnected()) {
                Pin connectedPin = getNode().getGraph().findPinById(connectedToList.get(0));
                disconnectInput(connectedPin);
                connectedToList.clear();
            }
        }else{
            if(targetPin.isConnected()) {
                Pin connectedPin = getNode().getGraph().findPinById(targetPin.connectedToList.get(0));
                targetPin.disconnectInput(connectedPin);
                targetPin.connectedToList.clear();
            }
        }

        connectedToList.add(targetPin.getID());
        targetPin.connectedToList.add(getID());
//        if (connectedTo != -1) {
//            Pin oldPin = getNode().getGraph().findPinById(connectedTo);
//            if(oldPin != null) {
//                oldPin.connectedTo = -1;
//            }
//        }
//
//        if (targetPin.connectedTo != -1) {
//            Pin oldPin = getNode().getGraph().findPinById(targetPin.connectedTo);
//            if(oldPin != null) {
//                oldPin.connectedTo = -1;
//            }
//        }
//        if (connectedTo != targetPin.connectedTo || (targetPin.connectedTo == -1 || connectedTo == -1)) {
//            connectedTo = targetPin.getID();
//            targetPin.connectedTo = getID();
//            return true;
//        }
        return true;
    }

    /**
     * disconnects input pin
     * @param pin
     */
    private void disconnectInput(Pin pin){
        if(pin == null)
        {

        }else {
            for (int i = 0; i < connectedToList.size(); i++) {
                if (connectedToList.get(i) == pin.getID()) {
                    pin.connectedToList.remove(i);
                    break;
                }
            }
        }
    }

    public void validateAllConnections(){
        for (int i = connectedToList.size() - 1; i >= 0 ; i--) {
            Pin pin = getNode().getGraph().findPinById(connectedToList.get(i));
            if(pin == null){
                //If pin is null then remove invalid pin
                System.out.println("Removed invalid pin");
                connectedToList.remove(i);
            }
        }
    }

    public void remove(int id){
        for (int i = 0; i < connectedToList.size(); i++) {
            if(connectedToList.get(i) == id){
                connectedToList.remove(i);
                break;
            }
        }
    }

    public void draw(ImDrawList windowDrawList, float posX, float posY, boolean isConnected, boolean pinDragSame){
        drawDefaultCircle(windowDrawList,posX,posY,isConnected,pinDragSame);
    }

    public void drawDefaultCircle(ImDrawList windowDrawList, float posX, float posY, boolean isConnected, boolean pinDragSame){
        int doubleGrey = pinDragSame ? rgbToInt(color.x, color.y, color.z, color.w) : rgbToInt(50, 50, 50, 255);
        if(isConnected) {
            windowDrawList.addCircleFilled(posX + (pinSize / 2), posY + (pinSize / 2), pinSize / 2, doubleGrey);
        }else{
            windowDrawList.addCircle(posX + (pinSize / 2), posY + (pinSize / 2), pinSize / 2, doubleGrey);
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
//        return connectedTo != -1;// && node.getGraph().findPinById(connectedTo) != null;
        return connectedToList.size() > 0;
    }

    public Pin getConnectedPin(){
        if(!isConnected()){
            return null;
        }

//        return node.getGraph().findPinById(connectedTo);
        return node.getGraph().findPinById(connectedToList.get(0));
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void contextMenu(){

    }
}
