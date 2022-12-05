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
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImLong;
import imgui.type.ImString;
import ovs.GlfwWindow;
import ovs.ImGuiWindow;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.TextField;
import ovs.graph.UI.UiComponent;
import ovs.graph.node.*;
import ovs.graph.pin.Pin;
import ovs.graph.save.GraphSaver;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GraphWindow {
    private ImGuiWindow imGuiWindow;
    private GlfwWindow glfwWindow;
    private final ImBoolean closable = new ImBoolean(true);

    public String id;
    private String fileName = "";

    private NodeEditorContext context;
    private GraphSaver graphSaver;
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
    private boolean isLoading = false;

    protected final ArrayList<Class<? extends Node>> nodeList = new ArrayList<>();
    private final ArrayList<Node> nodeInstanceCache = new ArrayList<>();
    private final ArrayList<TextField> tfGlobalVars = new ArrayList<>();
    private final ArrayList<TextField> tfPlayerVars = new ArrayList<>();


    private ImString nodeSearch = new ImString();

    private Settings settings = new Settings();

    private Node editingNodeTitle = null;

    private float canvasXPos = 0;
    private float canvasYPos = 0;

    public GraphWindow(ImGuiWindow imGuiWindow, GlfwWindow window, String loadFile){
        this.imGuiWindow = imGuiWindow;
        this.glfwWindow = window;
        graphSaver = new GraphSaver();
        if(loadFile != null) {
            graph = graphSaver.load(loadFile, settings);
            fileName = loadFile;
            isLoading = true;
        }else {
            graph = new Graph();
        }

        EDITOR = new TextEditor();

        NodeEditorConfig config = new NodeEditorConfig();
        config.setSettingsFile(null);
        context = new NodeEditorContext(config);

        //Add Nodes to list (this will also auto-populate context menu)
        addNodeToList(NodeActionList.class);
        addNodeToList(NodeBoolean.class);
        addNodeToList(NodeCreateHudText.class);
        addNodeToList(NodeCustomString.class);
        addNodeToList(NodeEventPlayer.class);
        addNodeToList(NodeGetVariable.class);
        addNodeToList(NodeIf.class);
        addNodeToList(NodeIsButtonDown.class);
        addNodeToList(NodeSetVariable.class);
        addNodeToList(NodeKill.class);
        addNodeToList(NodeLoop.class);
        addNodeToList(NodeRule.class);
        addNodeToList(NodeWait.class);
        addNodeToList(NodeWhile.class);
        addNodeToList(NodeRespawn.class);
        addNodeToList(NodeTeleport.class);
        addNodeToList(NodeVector.class);
        addNodeToList(NodeCurrentMap.class);
        addNodeToList(NodeMap.class);
        addNodeToList(NodeHostPlayer.class);
        addNodeToList(NodeSetMaxHealth.class);
        addNodeToList(NodeSetMatchTime.class);
        addNodeToList(NodeButton.class);
        addNodeToList(NodeFloat.class);
        addNodeToList(NodeDistanceBetween.class);
        addNodeToList(NodeSetMoveSpeed.class);
        addNodeToList(NodeStartScalingPlayer.class);
        addNodeToList(NodeAdd.class);
        addNodeToList(NodeRestart.class);
        addNodeToList(NodeHero.class);
        addNodeToList(NodeCustomColor.class);
        addNodeToList(NodeSetGravity.class);
        addNodeToList(NodeRandomReal.class);
        addNodeToList(NodeSetSlowMotion.class);

        graph.globalVariables.addListChangedListener(new ListChangedListener() {
            @Override
            public void onChanged() {
                tfGlobalVars.clear();
                for (int i = 0; i < graph.globalVariables.size(); i++) {
                    TextField tf = new TextField();
                    int j = i;
                    tf.addChangedListener(new ChangeListener() {
                        @Override
                        public void onChanged(String oldValue, String newValue) {
                            System.out.println(oldValue);
                            if(newValue.contains(" ")){
                                System.out.println("Error: Variables won't accept spaces" );
                            }
                            graph.globalVariables.get(j).name = newValue;
                        }
                    });
                    tf.setText(graph.globalVariables.get(i).name);
                    tfGlobalVars.add(tf);
                }
            }
        });

        graph.playerVariables.addListChangedListener(new ListChangedListener() {
            @Override
            public void onChanged() {
                tfPlayerVars.clear();
                for (int i = 0; i < graph.playerVariables.size(); i++) {
                    TextField tf = new TextField();
                    int j = i;
                    tf.addChangedListener(new ChangeListener() {
                        @Override
                        public void onChanged(String oldValue, String newValue) {
                            graph.playerVariables.get(j).name = newValue;
                        }
                    });
                    tf.setText(graph.playerVariables.get(i).name);
                    tfPlayerVars.add(tf);
                }
            }
        });

        graph.playerVariables.triggerOnChanged();
        graph.globalVariables.triggerOnChanged();
    }

    public void setFileName(String name){
        this.fileName = name;
    }

    public void show(float menuBarHeight, float taskbarHeight){
        cursorPos = ImGui.getMousePos();
        graph.update();
        ImGui.setNextWindowSize(glfwWindow.getWidth(), glfwWindow.getHeight() - menuBarHeight - taskbarHeight, ImGuiCond.Once);
        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY() + menuBarHeight, ImGuiCond.Once);


        if(ImGui.begin(fileName, closable)) {

            if(!closable.get())
            {
                //TODO request user to save before closing

                imGuiWindow.removeGraphWindow(this);
            }

            NodeEditor.setCurrentEditor(context);
            NodeEditor.getStyle().setNodeRounding(2.0f);

            if(ImGui.button("Compile")){
                String compiledText = Compiler.compile(graph, settings);
                EDITOR.setText(compiledText);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(compiledText), null);
            }

            ImGui.sameLine();

            if(ImGui.button("Save"))
            {
                for(Node node : graph.getNodes().values()){
                    node.onSaved();
                }
                graphSaver.save(fileName, settings, graph);
            }

            if(ImGui.beginTabBar("TabBar")) {
                if(ImGui.beginTabItem("Graph")) {
                    {
                        {
                            ImGui.beginGroup();
                            {
                                ImGui.dummy(400, 0);
                                //SETTINGS
                                settings.show();

                                //List of Rules on graph
                                {
                                    ImGui.separator();
                                    ImGui.text("Rules");

                                    ImInt currentItem = new ImInt();

                                    //TODO STOP CREATING NEW EVERY FRAME!!!!
                                    ArrayList<NodeRule> ruleNodes = new ArrayList<>();

                                    for (Node node : graph.getNodes().values()) {
                                        if (node instanceof NodeRule) {
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

                                ImGui.separator();

                                //Variables
                                ImGui.text("Variables");
                                if (ImGui.button("Add Global Variable")) {
                                    graph.addGlobalVariable("VarName");
                                }

                                ImGui.sameLine();

                                if (ImGui.button("Add Player Variable")) {
                                    graph.addPlayerVariable("varName");
                                }

                                ImGui.pushItemWidth(250);
                                ImGui.text("Global Variable");
                                for (int i = 0; i < tfGlobalVars.size(); i++) {
                                    ImGui.text(i + ":");
                                    ImGui.sameLine();
                                    tfGlobalVars.get(i).show();
                                    ImGui.sameLine();
                                    if(ImGui.button("X##globalVars" + i)){
                                        graph.globalVariables.remove(i);
                                    }
                                }

                                ImGui.text("Player Variable");

                                for (int i = 0; i < tfPlayerVars.size(); i++) {
                                    ImGui.text(i + ":");
                                    ImGui.sameLine();
                                    tfPlayerVars.get(i).show();
                                    ImGui.sameLine();
                                    if(ImGui.button("X##playerVars" + i)){
                                        graph.playerVariables.remove(i);
                                    }
                                }

                                ImGui.popItemWidth();

                            }
                            ImGui.endGroup();
                        }

                        ImGui.sameLine();

                        NodeEditor.begin("Editor");
                        {
                            for (Node node : graph.getNodes().values()) {
                                if (isLoading) {
                                    NodeEditor.setNodePosition(node.getID(), node.posX, node.posY);
                                }
                            }

                            if(isLoading){
                                isLoading = false;
                            }

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
                                    ImGui.newLine();
                                    for (int i = 0; i < max; i++) {
                                        ImGui.newLine();
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
                                                ImGui.sameLine(node.width);
                                            }
                                        }

                                        if (node.outputPins.size() > i) {
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
                                            ImGui.sameLine();
                                        }
                                    }
                                }
                                NodeEditor.endNode();

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
                            for (Pin pin : node.outputPins) {
                                if (pin.connectedTo != -1) {
//                                        float[] pincolor = getPinColor(pin);
                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowMarkerDistance, 50);
                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowDuration, 1000);
                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowSpeed, 25);

                                    NodeEditor.link(uniqueLinkId++, pin.getID(), pin.connectedTo, pin.getColor().x, pin.getColor().y, pin.getColor().z, pin.getColor().w, 2);

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

                        final long pinWithContextMenu = NodeEditor.getPinWithContextMenu();
                        if (pinWithContextMenu != -1) {
                            ImGui.openPopup("pin_menu" + id);
                            ImGui.getStateStorage().setInt(ImGui.getID("node_pin_id"), (int) pinWithContextMenu);
                        }

                        if(ImGui.isPopupOpen("pin_menu" + id)){
                            final int targetPin = ImGui.getStateStorage().getInt(ImGui.getID("node_pin_id"));
                            Pin pin = graph.findPinById(targetPin);
                            if(pin.isCanDelete()){
                                if(ImGui.beginPopup("pin_menu" + id)){
                                    if(ImGui.menuItem("Delete Pin")){
                                        pin.getNode().removePinById(targetPin);
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
                                            e.printStackTrace();
                                        }

                                        if(instance != null) {
                                            createContextMenuItem(instance, 0);
                                        }
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
//                float x = NodeEditor.toCanvasX(ImGui.getCursorScreenPosX());
//                float y = NodeEditor.toCanvasY(ImGui.getCursorScreenPosY());
                newInstance.posX = canvasXPos;
                newInstance.posY = canvasYPos;
                NodeEditor.setNodePosition(newInstance.getID(), canvasXPos, canvasYPos);
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
        Collections.sort(nodeList, new Comparator<Class<? extends Node>>() {
            @Override
            public int compare(Class<? extends Node> o1, Class<? extends Node> o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
    }

    public String getFileName() {
        return fileName;
    }
}
