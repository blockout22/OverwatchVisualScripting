package ovs.graph;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditor;
import imgui.extension.nodeditor.NodeEditorConfig;
import imgui.extension.nodeditor.NodeEditorContext;
import imgui.extension.nodeditor.flag.NodeEditorPinKind;
import imgui.extension.nodeditor.flag.NodeEditorStyleVar;
import imgui.extension.texteditor.TextEditor;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiInputTextFlags;
import imgui.type.ImInt;
import imgui.type.ImLong;
import imgui.type.ImString;
import ovs.GlfwWindow;
import ovs.graph.UI.UiComponent;
import ovs.graph.node.Node;
import ovs.graph.node.NodeCreateHudText;
import ovs.graph.node.NodeRule;
import ovs.graph.pin.Pin;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class GraphWindow {
    private GlfwWindow window;

    private String id;

    private NodeEditorContext context;
    protected Graph graph;

    private TextEditor EDITOR;

    private ImVec2 cursorPos;

    private final ImLong LINKA = new ImLong();
    private final ImLong LINKB = new ImLong();

    private Pin curSelectedPinDataType = null;

    private long holdingPinID = -1;
    private long lastHoldingPinID = -1;
    private long lastActivePin = -1;

    private boolean justOpenedContextMenu = false;

    protected final ArrayList<Class<? extends Node>> nodeList = new ArrayList<>();
    private final ArrayList<Node> nodeInstanceCache = new ArrayList<>();
    private ImString nodeSearch = new ImString();

    private Settings settings = new Settings();

    Node editingNodeTitle = null;

    public GraphWindow(GlfwWindow window){
        this.window = window;
        graph = new Graph();

        EDITOR = new TextEditor();

        NodeEditorConfig config = new NodeEditorConfig();
        config.setSettingsFile("Graph.json");
        context = new NodeEditorContext(config);

        addNodeToList(NodeRule.class);
        addNodeToList(NodeCreateHudText.class);
    }

    public void show(float menuBarHeight){
        cursorPos = ImGui.getMousePos();
        ImGui.setNextWindowSize(window.getWidth(), window.getHeight() - menuBarHeight, ImGuiCond.Once);
        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY() + menuBarHeight, ImGuiCond.Once);

        if(ImGui.begin("Graph window")) {
            NodeEditor.setCurrentEditor(context);
            NodeEditor.getStyle().setNodeRounding(2.0f);

            ImGui.sameLine();
            if(ImGui.button("Compile")){
                String compiledText = compile();
                EDITOR.setText(compiledText);
            }

            if(ImGui.beginTabBar("TabBar")) {
                if(ImGui.beginTabItem("Graph")) {
                    {

                        //SETTINGS
                        ImGui.beginGroup();
                        {
                            ImGui.dummy(400, 0);
                            settings.show();

                            ImGui.text("Rules");

                            ImInt currentItem = new ImInt();

                            ArrayList<NodeRule> ruleNodes = new ArrayList<>();

                            for (Node node : graph.getNodes().values()){
                                if(node instanceof NodeRule)
                                {
                                    ruleNodes.add((NodeRule) node);
                                }
                            }

                            String[] items = new String[ruleNodes.size()];
                            for (int i = 0; i < ruleNodes.size(); i++) {
                                items[i] = ruleNodes.get(i).getName();
                            }

                            ImGui.pushItemWidth(400);
                            ImGui.listBox("##Rules", currentItem, items);
                            ImGui.popItemWidth();
                        }
                        ImGui.endGroup();

                        ImGui.sameLine();

                        NodeEditor.begin("Editor");
                        {
                            for (Node node : graph.getNodes().values()) {
                                NodeEditor.beginNode(node.getID());
                                {
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

                                    //Node Custom UI
                                    for(UiComponent uiComponent : node.uiComponents)
                                    {
                                        uiComponent.show();
                                    }
                                    node.UI();

                                    int max = Math.max(node.outputPins.size(), node.inputPins.size());
                                    for (int i = 0; i < max; i++) {
                                        if (node.inputPins.size() > i) {
                                            Pin inPin = node.inputPins.get(i);

                                            NodeEditor.beginPin(inPin.getID(), NodeEditorPinKind.Input);
                                            drawPinShapeAndColor(inPin);
                                            ImGui.dummy(10, 10);

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

                                        if (node.width != -1) {
                                            if (!node.hasTitleBar()) {
                                                ImGui.sameLine(1);
                                            } else {
                                                ImGui.sameLine(node.width - 10);
                                            }
                                        }

                                        if (node.outputPins.size() > i) {
                                            ImGui.sameLine();
                                            Pin outPin = node.outputPins.get(i);

                                            NodeEditor.beginPin(outPin.getID(), NodeEditorPinKind.Output);
                                            drawPinShapeAndColor(outPin);
                                            ImGui.dummy(10, 10);

                                            if (outPin.getName().length() > 0) {
                                                ImGui.sameLine();
                                                ImGui.text(outPin.getName());
                                            }

                                            NodeEditor.pinPivotAlignment(0, 0.5f);
                                            NodeEditor.endPin();

                                            if (ImGui.isItemClicked() && holdingPinID == -1) {
                                                lastActivePin = outPin.getID();
                                            }
                                        }
                                    }
                                }
                                NodeEditor.endNode();

                                node.execute();
                            }
                        }

                        int uniqueLinkId = 1;
                        for (Node node : graph.getNodes().values()) {
                            for (Pin pin : node.outputPins) {
                                if (pin.connectedTo != -1) {
//                                        float[] pincolor = getPinColor(pin);
                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowMarkerDistance, 50);
                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowDuration, 1000);
                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowSpeed, 25);

                                    NodeEditor.link(uniqueLinkId++, pin.getID(), pin.connectedTo, pin.getColor().x, pin.getColor().y, pin.getColor().z, pin.getColor().w, 1);

                                    NodeEditor.popStyleVar(3);
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
                            int size = 10;
                            long[] list = new long[size];
                            NodeEditor.getSelectedNodes(list, size);
                            ImLong link1 = new ImLong();
                            ImLong link2 = new ImLong();
                            ImLong link3 = new ImLong();
                            if (NodeEditor.queryDeletedLink(link1, link2, link3)) {
                                Pin pin1 = graph.findPinById((int) link2.get());
                                Pin pin2 = graph.findPinById((int) link3.get());

                                pin1.connectedTo = -1;
                                pin2.connectedTo = -1;
                            }

                            ImLong nodeID = new ImLong();
                            if (NodeEditor.queryDeletedNode(nodeID)) {
                                graph.removeNode((int) nodeID.get());
                            }
                        }
                        NodeEditor.endDelete();


                        NodeEditor.suspend();

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

                        if (NodeEditor.showBackgroundContextMenu()) {
                            ImGui.openPopup("context_menu" + id);
                            justOpenedContextMenu = true;
                        }

                        if(ImGui.isPopupOpen("context_menu" + id))
                        {
                            if(ImGui.beginPopup("context_menu" + id)){

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
                                            e.printStackTrace();
                                        }

                                        createContextMenuItem(instance, 0);
                                    }
                                }else{
                                    if(justOpenedContextMenu){
                                        ImGui.setKeyboardFocusHere(0);
                                        justOpenedContextMenu = false;
                                    }

                                    ImGui.inputTextWithHint("##", "Search Node", nodeSearch);
                                    for(Node instance : nodeInstanceCache){
                                        createContextMenuItem(instance, 0);
                                    }
                                }

                                ImGui.endPopup();
                            }
                        }

                        NodeEditor.resume();
                        NodeEditor.end();

                    }
                    ImGui.endTabItem();
                }

                if(ImGui.beginTabItem("Output")){
                    EDITOR.render("TextOutput");
                    ImGui.endTabItem();
                }
            }
            ImGui.endTabBar();

            ImGui.sameLine();
        }
        ImGui.end();
    }

    private void createContextMenuItem(Node instance, int depth) {
        if(nodeSearch.get().toLowerCase().length() > 0){
            if(!instance.getName().toLowerCase().contains(nodeSearch.get())){
                return;
            }
        }
        if (ImGui.menuItem(instance.getName() + "##" + instance.getID())) {
            try {
                Node newInstance = instance.getClass().getDeclaredConstructor(Graph.class).newInstance(graph);
                graph.addNode(newInstance);
                //newInstance.init();
                //nodeQPos.put(newInstance.getID(), new ImVec2());
                NodeEditor.setNodePosition(newInstance.getID(), NodeEditor.toCanvasX(ImGui.getCursorScreenPosX()), NodeEditor.toCanvasY(ImGui.getCursorScreenPosY()));
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
            ImGui.closeCurrentPopup();
        }
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

                //disconnect old connections
                if(sourcePin.connectedTo != -1){
                    Pin oldPin = graph.findPinById(sourcePin.connectedTo);
                    oldPin.connectedTo = -1;
                }

                if(targetPin.connectedTo != -1){
                    Pin oldPin = graph.findPinById(targetPin.connectedTo);
                    oldPin.connectedTo = -1;
                }

                //create a new link connection
                if(sourcePin != null && targetPin != null){
                    if(sourcePin.connectedTo != targetPin.connectedTo || (targetPin.connectedTo == -1 || sourcePin.connectedTo == -1)){
                        sourcePin.connectedTo = targetPin.getID();
                        targetPin.connectedTo = sourcePin.getID();
                        holdingPinID = -1;
                    }
                }
            }
        }
    }

    private void drawPinShapeAndColor(Pin pin){
        float size = 10f;
        float posX = ImGui.getCursorPosX();
        float posY = ImGui.getCursorPosY();
        boolean pinDragSame = true;
//        if(curSelectedPinDataType != null){
//            pinDragSame = pin.getClass() == curSelectedPinDataType.getClass();
//        }
        pin.draw(ImGui.getWindowDrawList(), posX, posY, pin.connectedTo != -1, pinDragSame);
    }

    public void addNodeToList(Class<? extends Node> node){
        nodeList.add(node);
    }

    private String compile(){
        StringBuilder output = new StringBuilder();

        output.append(settings.getOutput());
        output.append("\n");

        for(Node node : graph.getNodes().values()){
            if(node instanceof NodeRule){
                output.append(handleNode(node));
                output.append("\n\n");
            }
        }

        return output.toString();
    }

    private String handleNode(Node node){
        return node.getOutput();
    }
}
