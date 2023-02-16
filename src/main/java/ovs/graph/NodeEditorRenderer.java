package ovs.graph;

import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditor;
import imgui.extension.nodeditor.NodeEditorConfig;
import imgui.extension.nodeditor.NodeEditorContext;
import imgui.extension.nodeditor.flag.NodeEditorPinKind;
import imgui.flag.ImDrawFlags;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.type.ImLong;
import imgui.type.ImString;
import ovs.Global;
import ovs.graph.UI.UiComponent;
import ovs.graph.node.Node;
import ovs.graph.pin.Pin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class NodeEditorRenderer {

    private NodeEditorContext context;
    private Graph graph;

    private ArrayList<GraphChangeListener> changeListeners = new ArrayList<>();
    private final ArrayList<Node> nodeInstanceCache = new ArrayList<>();
    protected final ArrayList<Class<? extends Node>> nodeList = new ArrayList<>();

    private final ImLong LINKA = new ImLong();
    private final ImLong LINKB = new ImLong();

    private ImVec2 headerMin = null;
    private ImVec2 headerMax = null;
    private ImVec2 headerSeparatorMin = new ImVec2();
    private ImVec2 headerSeparatorMax = new ImVec2();

    private ImString nodeSearch = new ImString();

    private Node editingNodeTitle = null;

    private Pin curSelectedPinDataType = null;

    private boolean justOpenedContextMenu = false;
    private boolean contextMenuAutoSize = true;
    private boolean contextMenuOpen = false;
//    private boolean promptSave = true;

    private long holdingPinID = -1;
    private long lastHoldingPinID = -1;
    private long lastActivePin = -1;

    private float contextMenuSize = 250;
    private float canvasXPos = 0;
    private float canvasYPos = 0;

    private String id;


    public NodeEditorRenderer(String id, Graph graph) {
        this.id = id;
        this.graph = graph;
        NodeEditorConfig config = new NodeEditorConfig();
        config.setSettingsFile(null);
        context = new NodeEditorContext(config);

        try {
            ArrayList<Class> nodeList = Global.findAllNodes();
            for (int i = 0; i < nodeList.size(); i++) {
                addNodeToList(nodeList.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Finding Nodes");
        }
    }

    public void setId(String id){
        this.id = id;
    }

    public void addChangeListener(GraphChangeListener listener){
        changeListeners.add(listener);
    }

    public void removeChangeListener(GraphChangeListener listener){
        changeListeners.remove(listener);
    }

    public void show(ImVec2 cursorPos, boolean isLoading) {
        NodeEditor.setCurrentEditor(context);
        NodeEditor.getStyle().setNodeRounding(2.0f);
        float headerMaxY = 0;
        NodeEditor.begin(id);
        {
            for (Node node : graph.getNodes().values()) {
                if (isLoading) {
                    NodeEditor.setNodePosition(node.getID(), node.posX, node.posY);
                }
            }

            for (Node node : graph.getNodes().values()) {
                float maxWidth = 0;
                NodeEditor.beginNode(node.getID());
                {

                    if(!ImGui.isMouseDown(0)) {
                        if (node.posX != NodeEditor.getNodePositionX(node.getID()) || node.posY != NodeEditor.getNodePositionY(node.getID())) {
                            node.posX = NodeEditor.getNodePositionX(node.getID());
                            node.posY = NodeEditor.getNodePositionY(node.getID());
                            for (GraphChangeListener listener : changeListeners){
                                listener.changed();
                            }
//                            promptSave = true;
                        }
                    }

                    //Node Title
                    if(node.isEditingTitle && editingNodeTitle == node) {
                        ImString string = new ImString();
                        string.set(node.getName());
                        ImGui.pushItemWidth(150);
                        if(ImGui.inputText("##", string, ImGuiInputTextFlags.EnterReturnsTrue)){
                            node.setName(string.get());
                            node.isEditingTitle = false;
                        }
                        ImGui.popItemWidth();
                    }else{
                        ImGui.text(node.getName());
                        if(ImGui.isItemHovered() && ImGui.isMouseDoubleClicked(0) && node.canEditTitle){
                            node.isEditingTitle = true;
                            editingNodeTitle = node;
                        }
                    }

                    if(node.hasTitleBar()){
                        headerMin = ImGui.getItemRectMin();
                        headerMaxY = ImGui.getItemRectMaxY();
                    }

                    //Node Custom UI
                    for(UiComponent uiComponent : node.uiComponents)
                    {
                        uiComponent.show();
                    }
                    node.UI();

                    int max = Math.max(node.outputPins.size(), node.inputPins.size());
                    ImGui.newLine();
                    for (int i = 0; i < max; i++) {
                        ImGui.newLine();
                        if (node.inputPins.size() > i) {
                            Pin inPin = node.inputPins.get(i);

                            if(inPin.isVisible()){
                                NodeEditor.beginPin(inPin.getID(), NodeEditorPinKind.Input);
                                drawPinShapeAndColor(inPin);
//                                            ImGui.dummy(10, 10);
                                inPin.pinSize = 12;
                                ImGui.dummy(inPin.pinSize, inPin.pinSize);

                                if (inPin.getName().length() > 0) {
                                    ImGui.sameLine();
                                    ImGui.text(inPin.getName());
                                }

                                NodeEditor.pinPivotAlignment(0f, .5f);
                                NodeEditor.endPin();

                                if (ImGui.isItemClicked() && holdingPinID == -1) {
                                    lastActivePin = inPin.getID();
                                }

                                ImGui.sameLine();
                                ImGui.beginGroup();
                                ImGui.pushItemWidth(150);
                                inPin.UI();
                                ImGui.popItemWidth();
                                ImGui.endGroup();
                            }
                        }

                        if (node.width != -1) {
                            if (!node.hasTitleBar()) {
                                ImGui.sameLine(1);
                            } else {
                                ImGui.sameLine(node.width);
                            }
                        }

                        if (node.outputPins.size() > i) {
                            Pin outPin = node.outputPins.get(i);

                            NodeEditor.beginPin(outPin.getID(), NodeEditorPinKind.Output);
                            drawPinShapeAndColor(outPin);
//                                            ImGui.dummy(10, 10);
                            ImGui.dummy(outPin.pinSize, outPin.pinSize);

                            if (outPin.getName().length() > 0) {
                                ImGui.sameLine();
                                ImGui.text(outPin.getName());
                            }

                            NodeEditor.pinPivotAlignment(0, 0.5f);
                            NodeEditor.endPin();

                            if (ImGui.isItemClicked() && holdingPinID == -1) {
                                lastActivePin = outPin.getID();
                            }
                            ImGui.sameLine();
                        }
                        if(maxWidth < NodeEditor.getNodePositionX(node.getID()) + NodeEditor.getNodeSizeX(node.getID()) - NodeEditor.getStyle().getNodePadding().x){
                            maxWidth = NodeEditor.getNodePositionX(node.getID()) + NodeEditor.getNodeSizeX(node.getID()) - NodeEditor.getStyle().getNodePadding().x;
                        }
                    }

//                                    if(maxWidth < node.width){
//                                        maxWidth = node.width;
//                                    }
                    if(headerMax == null){
                        headerMax = new ImVec2();
                    }
                    headerMax.set(NodeEditor.getNodePositionX(node.getID()) < 0 ? (NodeEditor.getNodeSizeX(node.getID()) + NodeEditor.getNodePositionX(node.getID())) - NodeEditor.getStyle().getNodePadding().x : maxWidth, headerMaxY);
//                                    headerMax = new ImVec2(maxWidth, headerMaxY);
                }
                NodeEditor.endNode();
                float x = ImGui.getMousePosX();
                float y = ImGui.getMousePosY();

//                                ImGui.getWindowDrawList().addRect(x, y,x + 4, y + 4, ImColor.floatToColor(1, 0, 0, 1));

                //draw color ontop of titlebar
                if(ImGui.isItemVisible() && node.hasTitleBar()){
                    int alpha = (int) (ImGui.getStyle().getAlpha() * 255);

                    ImDrawList drawList = NodeEditor.getNodeBackgroundDrawList(node.getID());
                    float halfBorderWidth = NodeEditor.getStyle().getNodeBorderWidth() * 0.5f;

                    float uvX = (headerMax.x - headerMin.x) / (4.0f * 100);
                    float uvY = (headerMax.y - headerMin.y) / (4.0f * 100);

                    if ((headerMax.x > headerMin.x) && (headerMax.y > headerMin.y)) {
                        drawList.addRectFilled(headerMin.x - (8 - halfBorderWidth), headerMin.y - (8 - halfBorderWidth), headerMax.x + (8 - halfBorderWidth), headerMax.y + (0), ImColor.intToColor(node.getRed(), node.getGreen(), node.getBlue(), node.getAlpha()), NodeEditor.getStyle().getNodeRounding(), ImDrawFlags.RoundCornersTop);
                    }

                    headerSeparatorMin.set(headerMin.x, headerMin.y);
                    headerSeparatorMax.set(headerMax.x, headerMax.y);

                    if ((headerSeparatorMax.x > headerSeparatorMin.x) && (headerSeparatorMax.y > headerSeparatorMin.y)) {
                        drawList.addLine(headerMin.x - 8 - halfBorderWidth, headerMax.y, headerMax.x + (8 - halfBorderWidth), headerMax.y, ImColor.intToColor(node.getRed(), node.getGreen(), node.getBlue(), 96 * alpha / (3 * 255)), 1);
                    }
                }

                if (node.width == -1)
                {
                    node.width = NodeEditor.getNodeSizeX(node.getID());
                }

                //TODO Throttle execute as this doesn't need to run a million times a second
                node.execute();
            }
        }



        int uniqueLinkId = 1;
        for (Node node : graph.getNodes().values()) {
            for (Pin pin : node.inputPins) {
//                                if (pin.connectedTo != -1) {
////                                        float[] pincolor = getPinColor(pin);
//                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowMarkerDistance, 50);
//                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowDuration, 1000);
//                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowSpeed, 25);
//
//                                    NodeEditor.link(uniqueLinkId++, pin.getID(), pin.connectedTo, pin.getColor().x, pin.getColor().y, pin.getColor().z, pin.getColor().w, 2);
//
//                                    NodeEditor.popStyleVar(3);
//                                }

                for (int i = 0; i < pin.connectedToList.size(); i++) {
                    NodeEditor.link(uniqueLinkId++, pin.getID(), pin.connectedToList.get(i), pin.getColor().r, pin.getColor().g, pin.getColor().b, pin.getColor().a, 2);
                }
            }
        }

        if (NodeEditor.beginCreate()) {
            holdingPinID = lastActivePin;
//                            curSelectedPinDataType = graph.findPinById((int) lastActivePin).getDataType();
            curSelectedPinDataType = graph.findPinById((int) lastActivePin);
            checkPinConnections();
        } else {
            //Open context menu if pin link dropped without connecting to another pin
            if (holdingPinID != -1 && !(LINKA.get() != 0 || LINKB.get() != 0)) {
                lastHoldingPinID = holdingPinID;
                holdingPinID = -1;
                curSelectedPinDataType = null;

//                            System.out.println(LINKA.get() + " : " + LINKB.get());
//                                setNextWindowPos(cursorPos.x, cursorPos.y, ImGuiCond.Always);
                ImGui.setNextWindowPos(cursorPos.x, cursorPos.y, ImGuiCond.Appearing);
                ImGui.openPopup("context_menu" + id);
                justOpenedContextMenu = true;
            }
            LINKA.set(0);
            LINKB.set(0);
        }
        NodeEditor.endCreate();

        if (NodeEditor.beginDelete()) {
            int size = NodeEditor.getSelectedObjectCount();
            long[] list = new long[size];
            NodeEditor.getSelectedNodes(list, size);
            ImLong link1 = new ImLong();
            ImLong link2 = new ImLong();
            ImLong link3 = new ImLong();
            if (NodeEditor.queryDeletedLink(link1, link2, link3)) {
                Pin pin1 = graph.findPinById((int) link2.get());
                Pin pin2 = graph.findPinById((int) link3.get());

                pin1.remove(pin2.getID());
                pin2.remove(pin1.getID());
                for (GraphChangeListener listener : changeListeners){
                    listener.changed();
                }
//                promptSave = true;
//                                pin1.connectedTo = -1;
//                                pin2.connectedTo = -1;
            }

            for (int i = 0; i < list.length; i++) {
                ImLong nodeID = new ImLong();
                if (NodeEditor.queryDeletedNode(nodeID)) {
                    graph.removeNode((int) nodeID.get());
                    NodeEditor.deselectNode(nodeID.get());
                    for (GraphChangeListener listener : changeListeners){
                        listener.changed();
                    }
//                    promptSave = true;
                }

            }

        }
        NodeEditor.endDelete();
        NodeEditor.suspend();

        //Context Menu
        final long pinWithContextMenu = NodeEditor.getPinWithContextMenu();
        if (pinWithContextMenu != -1) {
            ImGui.openPopup("pin_menu" + id);
            ImGui.getStateStorage().setInt(ImGui.getID("node_pin_id"), (int) pinWithContextMenu);
        }

        if(ImGui.isPopupOpen("pin_menu" + id)){
            final int targetPin = ImGui.getStateStorage().getInt(ImGui.getID("node_pin_id"));
            Pin pin = graph.findPinById(targetPin);
            if(ImGui.beginPopup("pin_menu" + id)){
                pin.contextMenu();
                if(pin.isCanDelete()){

                    if(ImGui.menuItem("Delete Pin")){
                        pin.getNode().removePinById(targetPin);
                        ImGui.closeCurrentPopup();
                    }

                }
            }
            ImGui.endPopup();
        }

        final long nodeWithContextMenu = NodeEditor.getNodeWithContextMenu();
        if(nodeWithContextMenu != -1){
            NodeEditor.selectNode(nodeWithContextMenu, true);
            ImGui.openPopup("node_menu" + id);
            ImGui.getStateStorage().setInt(ImGui.getID("node_id"), (int)nodeWithContextMenu);
        }

        if(ImGui.isPopupOpen("node_menu" + id))
        {
            final int targetNode = ImGui.getStateStorage().getInt(ImGui.getID("node_id"));

            if(ImGui.beginPopup("node_menu" + id)){
                //TODO Duplicate all info attatched to this node
                if(ImGui.menuItem("Duplicate Node " + graph.getNodes().get(targetNode).getName()))
                {
                    Node newInstance = null;
                    try{
                        Node target = graph.getNodes().get(targetNode);
                        newInstance = target.getClass().getDeclaredConstructor(Graph.class).newInstance(graph);
                        graph.addNode(newInstance);
                        NodeEditor.setNodePosition(newInstance.getID(), NodeEditor.toCanvasX(ImGui.getCursorScreenPosX()), NodeEditor.toCanvasY(ImGui.getCursorScreenPosY()));

                        int defaultTotal = newInstance.inputPins.size();
                        for (int i = 0; i < target.inputPins.size(); i++) {
                            if(i > defaultTotal - 1){
                                Pin newPin = target.inputPins.get(i).getClass().getDeclaredConstructor().newInstance();
                                newPin.setNode(newInstance);
                                newPin.setName(target.inputPins.get(i).getName());
                                newPin.setCanDelete(true);
                                newInstance.addCustomInput(newPin);
                            }
                        }

                        newInstance.copy(target);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    ImGui.closeCurrentPopup();
                }

                ImGui.separator();

                if(ImGui.menuItem("Delete " + graph.getNodes().get(targetNode).getName())){
                    NodeEditor.deleteNode(targetNode);
                    ImGui.closeCurrentPopup();
                }

                if(ImGui.menuItem("Preview Source")){
                    Global.setStorage("preview_source", targetNode);
                    Global.setStorage("preview_open_request", 1);
//                                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }
        }

        final long linkWithContextMenu = NodeEditor.getLinkWithContextMenu();
        if(linkWithContextMenu != -1){
            ImGui.openPopup("link_menu" + id);
            ImGui.getStateStorage().setInt(ImGui.getID("link_id"), (int)linkWithContextMenu);
        }

        if(ImGui.isPopupOpen("link_menu" + id)){
            final int targetLink = ImGui.getStateStorage().getInt(ImGui.getID("link_id"));
            if(ImGui.beginPopup("link_menu" + id)){
                if(ImGui.menuItem("Delete Link")){
                    if(NodeEditor.deleteLink(targetLink))
                    {

                    }
                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }
        }

        if((int)Global.getStorage("preview_open_request") == 1){
            ImGui.openPopup("PreviewSource" + id);
            Global.setStorage("preview_open_request", 0);
        }

        if(ImGui.isPopupOpen("PreviewSource" + id))
        {
//                            ImGui.setNextWindowPos(glfwWindow.getWidth() * 0.5f, glfwWindow.getHeight() * 0.5f, ImGuiCond.Always);
            if(ImGui.beginPopupModal("PreviewSource" + id, ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoSavedSettings))
            {
                int targetID = (int)Global.getStorage("preview_source");
                ImGui.text(graph.getNodes().get(targetID).getOutput());

                ImGui.separator();
                if(ImGui.button("Close")){
                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }
        }

        if (NodeEditor.showBackgroundContextMenu()) {
            ImGui.openPopup("context_menu" + id);
            lastHoldingPinID = -1;
            justOpenedContextMenu = true;
        }

        //Copy Paste
//                        System.out.println(ImGui.getWindowPosX());
//                        System.out.println(ImGui.getMousePosX() - ImGui.getWindowPosX() + " : " + (ImGui.getMousePosX() - (ImGui.getWindowPosX() + ImGui.getWindowSizeX())));

//                        System.out.println(NodeEditor.toScreenX(0) + " : " + NodeEditor.toCanvasX(0));

        if(NodeEditor.beginShortcut()) {
            if (NodeEditor.acceptCopy()) {
                int size = NodeEditor.getSelectedObjectCount();
                long[] list = new long[size];
                NodeEditor.getSelectedNodes(list, size);

                ArrayList<Node> nodeList = new ArrayList<>();
                for (int i = 0; i < list.length; i++) {
                    Node node = graph.getNodes().get((int)list[i]);
                    nodeList.add(node);
                }
                NodeCopyPasteHandler.copy(nodeList);
            }

            if(NodeEditor.acceptPaste()){
                System.out.println("Paste");
                NodeCopyPasteHandler.paste(graph);
            }
        }
        NodeEditor.endShortcut();

        if(ImGui.isPopupOpen("context_menu" + id))
        {
            if(ImGui.beginPopup("context_menu" + id)){
                canvasXPos = NodeEditor.toCanvasX(ImGui.getCursorScreenPosX());
                canvasYPos = NodeEditor.toCanvasY(ImGui.getCursorScreenPosY());

                if(nodeInstanceCache.isEmpty())
                {
                    for (Class<? extends Node> node : nodeList) {
                        Constructor<? extends Node> nodeClass = null;
                        Node instance = null;
                        try {
                            nodeClass = node.getDeclaredConstructor(Graph.class);
                            instance = nodeClass.newInstance(graph);
                            nodeInstanceCache.add(instance);
                        } catch (Exception e) {
                            System.out.println(nodeClass.getName());
                            e.printStackTrace();
                        }

//                                        if(instance != null) {
//                                            createContextMenuItem(instance, 0);
//                                        }
                    }
                }else {
                    if (justOpenedContextMenu) {
                        nodeSearch.set("");
                        ImGui.setKeyboardFocusHere(0);
                        justOpenedContextMenu = false;
                    }

                    ImGui.pushItemWidth(350);
                    ImGui.inputTextWithHint("##", "Search Node", nodeSearch);
                    ImGui.popItemWidth();
//                                    ImGui.setNextWindowSize(250, 0, ImGuiCond.Once);
                    ImGui.beginChild("ScrollArea", 350, (contextMenuAutoSize ? 500 : contextMenuSize));
                    for (Node instance : nodeInstanceCache) {
                        createContextMenuItem(instance, 0);
                    }

                    float avail = ImGui.getContentRegionAvailY();
                    if (!contextMenuOpen && avail != -1)
                    {
                        if (avail <= 0) {
                            contextMenuAutoSize = true;
                        } else {
                            contextMenuAutoSize = false;
                            contextMenuSize = 500 - avail;
                        }
                        contextMenuOpen = true;
                    }
                    ImGui.endChild();
                }
                ImGui.endPopup();
            }
        }else{
            contextMenuOpen = false;
            contextMenuAutoSize = true;
        }

        //Handle in node popups
        Popup popup = PopupHandler.currentPopup;
        if(popup != null){
            ImGui.setNextWindowPos(cursorPos.x, cursorPos.y + 5, ImGuiCond.Appearing);
            if(ImGui.isPopupOpen(popup.id.toString())){
                if(ImGui.beginPopup(popup.id.toString())){
                    if(popup.show()){
                        PopupHandler.remove(popup);
                        ImGui.closeCurrentPopup();
                    }
                }
                ImGui.endPopup();
            }
        }

        NodeEditor.resume();
        NodeEditor.end();
    }

    private void drawPinShapeAndColor(Pin pin){
        float size = 10f;
        float posX = ImGui.getCursorPosX();
        float posY = ImGui.getCursorPosY();
        boolean pinDragSame = true;
//        if(curSelectedPinDataType != null){
//            pinDragSame = pin.getClass() == curSelectedPinDataType.getClass();
//        }
        pin.draw(ImGui.getWindowDrawList(), posX, posY, pin.isConnected(), pinDragSame);
    }

    private void checkPinConnections()
    {
        if(NodeEditor.queryNewLink(LINKA, LINKB)){
            Pin sourcePin = graph.findPinById((int)LINKA.get());
            Pin targetPin = graph.findPinById((int)LINKB.get());

            if(sourcePin.getNode() == targetPin.getNode()){
                return;
            }

            if(!(sourcePin.getClass() == targetPin.getClass()) || !(sourcePin.getPinType() != targetPin.getPinType())){
                NodeEditor.rejectNewItem(1, 0, 0, 1, 1);
                holdingPinID = -1;
                return;
            }

            if(NodeEditor.acceptNewItem(0, 1, 0, 1, 1)){
                for (GraphChangeListener listener : changeListeners){
                    listener.changed();
                }
//                promptSave = true;
                if(sourcePin.connect(targetPin)){
                    //sets the holder pin to -1 otherwise context menu will popup
                    holdingPinID = -1;
                }
//                //disconnect old connections
//                if(sourcePin.connectedTo != -1){
//                    Pin oldPin = graph.findPinById(sourcePin.connectedTo);
//                    if(oldPin != null) {
//                        oldPin.connectedTo = -1;
//                    }
//                }
//
//                if(targetPin.connectedTo != -1){
//                    Pin oldPin = graph.findPinById(targetPin.connectedTo);
//                    if(oldPin != null) {
//                        oldPin.connectedTo = -1;
//                    }
//                }
//
//                //create a new link connection
//                if(sourcePin != null && targetPin != null){
//                    if(sourcePin.connectedTo != targetPin.connectedTo || (targetPin.connectedTo == -1 || sourcePin.connectedTo == -1)){
//                        sourcePin.connectedTo = targetPin.getID();
//                        targetPin.connectedTo = sourcePin.getID();
//                        holdingPinID = -1;
//                    }
//                }
            }
        }
    }

    public void addNodeToList(Class<? extends Node> node){
        nodeList.add(node);
        Collections.sort(nodeList, new Comparator<Class<? extends Node>>() {
            @Override
            public int compare(Class<? extends Node> o1, Class<? extends Node> o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
    }

    private void createContextMenuItem(Node instance, int depth) {
        if(nodeSearch.get().toLowerCase().length() > 0){
            if(!instance.getName().toLowerCase().contains(nodeSearch.get())){
                return;
            }
        }
        boolean canCreate = true;
        //TODO filter out pins that can't connect to each other
        Pin lastPin = graph.findPinById((int)lastHoldingPinID);

        if(lastPin != null){
            canCreate = false;
            if(lastPin.getPinType() == Pin.PinType.Input){
                for (int i = 0; i < instance.outputPins.size(); i++) {
                    Pin outPin = instance.outputPins.get(i);

                    if(lastPin.getClass() == outPin.getClass()){
                        canCreate = true;
                        break;
                    }
                }
            }else if(lastPin.getPinType() == Pin.PinType.Output){
                for (int i = 0; i < instance.inputPins.size(); i++) {
                    Pin inPin = instance.inputPins.get(i);

                    if(lastPin.getClass() == inPin.getClass()){
                        canCreate = true;
                        break;
                    }
                }
            }
        }


        if(canCreate) {
            if (ImGui.menuItem(instance.getName() + "##" + instance.getID())) {

            }
            if(ImGui.isItemHovered() && ImGui.isItemClicked()){
                try {
                    Node newInstance = instance.getClass().getDeclaredConstructor(Graph.class).newInstance(graph);
                    graph.addNode(newInstance);
                    newInstance.posX = canvasXPos;
                    newInstance.posY = canvasYPos;
                    NodeEditor.setNodePosition(newInstance.getID(), canvasXPos, canvasYPos);
                    for (GraphChangeListener listener : changeListeners){
                        listener.changed();
                    }
//                    promptSave = true;
                    autoConnectLink(newInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                ImGui.closeCurrentPopup();
            }
        }
    }

    private void autoConnectLink(Node newInstance){
        //check if context menu opened by dragging a pin
        if(lastHoldingPinID != -1){
            Pin pin = graph.findPinById((int)lastActivePin);
//            System.out.println(pin.getPinType());
            switch (pin.getPinType()){
                case Input:
                    for (int i = 0; i < newInstance.outputPins.size(); i++) {
                        Pin instancePin = newInstance.outputPins.get(i);
//                        if(instancePin.getDataType() == pin.getDataType())
                        if(instancePin.getClass() == pin.getClass())
                        {
                            //if a successful connection is made then return/break
                            if(pin.connect(instancePin)){
                                break;
                            }
                        }
                    }
                    break;
                case Output:
                    for (int i = 0; i < newInstance.inputPins.size(); i++) {
                        Pin instancePin = newInstance.inputPins.get(i);
//                        if(instancePin.getDataType() == pin.getDataType())
                        if(instancePin.getClass() == pin.getClass())
                        {
                            //if a successful connection is made then return/break
                            if(pin.connect(instancePin)){
                                break;
                            }
                        }
                    }
                    break;
            }
            lastHoldingPinID = -1;
        }
    }
}
