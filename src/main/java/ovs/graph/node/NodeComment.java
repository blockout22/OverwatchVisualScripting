package ovs.graph.node;

import ovs.graph.Graph;

public class NodeComment extends Node{

    public int sizeX = 200;
    public int sizeY = 200;
    public float[] rgba = new float[4];
    public boolean showBubble = false;

    public boolean showColorPicker = false;

    public NodeComment(Graph graph) {
        super(graph);
        setName("Comment");
        rgba[0] = 0.2f;
        rgba[1] = 0.2f;
        rgba[2] = 0.2f;
        rgba[3] = 1f;
        canEditTitle = true;
    }

    @Override
    public void execute() {

    }

    @Override
    public void onSaved() {
        getExtraSaveData().clear();
        getExtraSaveData().add("SizeX:" + sizeX);
        getExtraSaveData().add("SizeY:" + sizeY);
        getExtraSaveData().add("Red:" + rgba[0]);
        getExtraSaveData().add("Green: " + rgba[1]);
        getExtraSaveData().add("Blue: " + rgba[2]);
        getExtraSaveData().add("Alpha: " + rgba[3]);
        getExtraSaveData().add("ShowBubble: " + showBubble);
    }

    @Override
    public void onLoaded() {
        for(String data : getExtraSaveData()){
            if(data.startsWith("SizeX")){
                sizeX = Integer.parseInt(data.split(":")[1]);
            }else if(data.startsWith("SizeY")){
                sizeY = Integer.parseInt(data.split(":")[1]);
            }else if(data.startsWith("Red")){
                rgba[0] = Float.parseFloat(data.split(":")[1]);
            }else if(data.startsWith("Green")){
                rgba[1] = Float.parseFloat(data.split(":")[1]);
            }else if(data.startsWith("Blue")){
                rgba[2] = Float.parseFloat(data.split(":")[1]);
            }else if(data.startsWith("Alpha")){
                rgba[3] = Float.parseFloat(data.split(":")[1]);
            }else if(data.startsWith("ShowBubble")){
                showBubble = Boolean.parseBoolean(data.split(":")[1]);
            }
        }
    }

    @Override
    public String getOutput() {
        return "";
    }

    @Override
    public void UI() {

    }
}
