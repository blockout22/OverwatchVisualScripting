package ovs.graph;

import imgui.ImColor;
import imgui.ImDrawList;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditor;
import imgui.extension.nodeditor.NodeEditorConfig;
import imgui.extension.nodeditor.NodeEditorContext;
import imgui.extension.nodeditor.flag.NodeEditorPinKind;
import imgui.extension.nodeditor.flag.NodeEditorStyleVar;
import imgui.extension.texteditor.TextEditor;
import imgui.flag.*;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImLong;
import imgui.type.ImString;
import ovs.*;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.TextField;
import ovs.graph.UI.UiComponent;
import ovs.graph.node.*;
import ovs.graph.node.interfaces.NodeDisabled;
import ovs.graph.node.interfaces.NodeGroupOnly;
import ovs.graph.pin.Pin;
import ovs.graph.save.GraphSaver;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GraphWindow {
    private ImGuiWindow imGuiWindow;
    private GlfwWindow glfwWindow;

    private final ImBoolean closable = new ImBoolean(true);
    private ConfirmSaveDialog saveDialog = new ConfirmSaveDialog();

    public String id;
    private String fileName = "untitled";

    private GraphSaver graphSaver;
    protected Graph graph;
    private NodeEditorRenderer nodeEditorRenderer;

    private TextEditor EDITOR;

    private ImVec2 cursorPos;

    private Pin curSelectedPinDataType = null;

    private boolean isFocused = false;
    private boolean promptSave = false;
    private boolean showSavedText = false;
    private boolean isLoading = false;
    private boolean outputInFocus = false;

    private final ArrayList<TextField> tfGlobalVars = new ArrayList<>();
    private final ArrayList<TextField> tfPlayerVars = new ArrayList<>();
    private final ArrayList<TextField> tfSubroutines = new ArrayList<>();

    private Settings settings = new Settings();


    private ImInt ruleListSelected = new ImInt();

    private double animation_time = 1.0f; // animation duration in seconds
    private double animation_start_time = ImGui.getTime();

    private UndoHandler undoHandler = new UndoHandler();

    private ArrayList<Node> ruleNodes = new ArrayList<>();

    private GraphChangeListener graphChangeListener;

    public GraphWindow(ImGuiWindow imGuiWindow, GlfwWindow window, String loadFile){
        this.imGuiWindow = imGuiWindow;
        this.glfwWindow = window;
        graphSaver = new GraphSaver();
        if(loadFile != null) {
            load(loadFile);
        }else {
            graph = new Graph();
            System.out.println("Created New Graph");
        }

        EDITOR = new TextEditor();

        nodeEditorRenderer = new NodeEditorRenderer(id, graph, NodeDisabled.class, NodeGroupOnly.class);
        graphChangeListener = new GraphChangeListener() {
            @Override
            public void changed() {
                promptSave = true;
            }
        };
        nodeEditorRenderer.addChangeListener(graphChangeListener);

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

        graph.subroutines.addListChangedListener(new ListChangedListener() {
            @Override
            public void onChanged() {
                tfSubroutines.clear();
                for (int i = 0; i < graph.subroutines.size(); i++) {
                    TextField tf = new TextField();
                    int j = i;
                    tf.addChangedListener(new ChangeListener() {
                        @Override
                        public void onChanged(String oldValue, String newValue) {
                            graph.subroutines.set(j, newValue);
                        }
                    });
                    tf.setText(graph.subroutines.get(i));
                    tfSubroutines.add(tf);
                }
            }
        });

        graph.playerVariables.triggerOnChanged();
        graph.globalVariables.triggerOnChanged();
        graph.subroutines.triggerOnChanged();
    }

    public void load(String loadFile){
        graph = graphSaver.load(loadFile, settings);
        fileName = loadFile;
        id = fileName;
        isLoading = true;
    }

    public void loadFromString(String data){
        graph = graphSaver.loadFromString(data, settings);
        isLoading = true;
    }

    public void save(){
        graphSaver.save(fileName, settings, graph);
    }

    public void setFileName(String name){
        this.fileName = name;
        this.id = fileName;
        nodeEditorRenderer.setId(id);
    }

    public void show(float menuBarHeight, float taskbarHeight){
        cursorPos = ImGui.getMousePos();
        graph.update();
        float ySize = glfwWindow.getHeight() - menuBarHeight - taskbarHeight;
        ImGui.setNextWindowSize(glfwWindow.getWidth(), ySize, ImGuiCond.Once);
//        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY() + menuBarHeight, ImGuiCond.Once);
        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY(), ImGuiCond.Once);

        if(ImGui.begin(fileName, closable)) {
            if(ImGui.isWindowFocused()){
                if(!isFocused) {
                    ImGui.setWindowPos(fileName, ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY() + taskbarHeight);
                    animation_time = 1.0f;
                    animation_start_time = ImGui.getTime();
                    isFocused = true;
                }
            }

            ImVec2 target_pos = new ImVec2(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY() + menuBarHeight);
            double current_time = ImGui.getTime();
            if (current_time - animation_start_time < animation_time)
            {
                float t = (float) ((current_time - animation_start_time) / animation_time);
                ImVec2 pos = new ImVec2();
                pos.x = Global.lerpFloat(ImGui.getWindowPosX(), target_pos.x, t);
                pos.y = Global.lerpFloat(ImGui.getWindowPosY(), target_pos.y, t);
                ImGui.setWindowPos(fileName, pos.x, pos.y);
            }

            if(!closable.get())
            {
                //TODO request user to save before closing
                if(promptSave){
                    int state = saveDialog.show();
                    if(state == 0){
                        promptSave = false;
                        imGuiWindow.removeGraphWindow(this);
                    }else if(state == 1){
                        graphSaver.save(fileName, settings, graph);
                        imGuiWindow.removeGraphWindow(this);
                    }
                }else{
                    imGuiWindow.removeGraphWindow(this);
                }

                nodeEditorRenderer.removeChangeListener(graphChangeListener);
            }

//            NodeEditor.setCurrentEditor(context);
//            NodeEditor.getStyle().setNodeRounding(2.0f);
//            nodeEditorRenderer.initFrame();

//            if(ImGui.button("Compile")){
//                String compiledText = Compiler.compile(graph, settings);
//                EDITOR.setText(compiledText);
//                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//                clipboard.setContents(new StringSelection(compiledText), null);
//            }

            ImGui.sameLine();

            if(ImGui.button("Save"))
            {
                for(Node node : graph.getNodes().values()){
                    node.onSaved();
                }
                boolean success = graphSaver.save(fileName, settings, graph);

                if(success){
                    promptSave = false;
                    showSavedText = true;
                    TaskSchedule.addTask(new Task() {
                        @Override
                        public void onFinished() {
                            showSavedText = false;
                        }
                    }, 5000);
                }
            }

            if(showSavedText){
                ImGui.sameLine();
                ImGui.text("Saved!");
            }


            if(ImGui.beginTabBar("TabBar")) {
                if(ImGui.beginTabItem("Graph")) {
                    if(outputInFocus){
                        outputInFocus = false;
//                        hasCompiled = false;
                    }
                    {
                        {
                            ImGui.beginChild("SettingChild", 400, 0);
                            ImGui.beginGroup();
                            {
                                ImGui.dummy(400, 0);
                                //SETTINGS
                                settings.show();

                                //List of Rules on graph
//                                ImGui.newLine();
                                ImGui.separator();
                                if(ImGui.collapsingHeader("Rules"))
                                {
//                                    ImGui.separator();
//                                    ImGui.text("Rules");

                                    ruleNodes.clear();

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
                                    ImGui.listBox("##Rules", ruleListSelected, items);
                                    if(ImGui.isItemHovered() && ImGui.isMouseDoubleClicked(0)){

                                        int id = ruleNodes.get(ruleListSelected.get()).getID();
                                        NodeEditor.selectNode(id, false);
                                        NodeEditor.navigateToSelection(false, 0.5f);
                                    }
                                    ImGui.popItemWidth();
                                }

//                                ImGui.newLine();
                                ImGui.separator();

                                //Variables
                                if(ImGui.collapsingHeader("Variables")) {
//                                    ImGui.text("Variables");
                                    if (ImGui.button("Add Global Variable")) {
                                        graph.addGlobalVariable("VarName" + graph.globalVariables.size());
                                    }

                                    ImGui.sameLine();

                                    if (ImGui.button("Add Player Variable")) {
                                        graph.addPlayerVariable("varName" + graph.playerVariables.size());
                                    }

//                                    ImGui.pushItemWidth(250);
                                    ImGui.text("Global Variable");
                                    for (int i = 0; i < tfGlobalVars.size(); i++) {
                                        ImGui.text(i + ":");
                                        ImGui.sameLine();
                                        tfGlobalVars.get(i).show();
                                        ImGui.sameLine();
                                        if (ImGui.button("X##globalVars" + i)) {
                                            graph.globalVariables.remove(i);
                                        }
                                    }

                                    ImGui.text("Player Variable");

                                    for (int i = 0; i < tfPlayerVars.size(); i++) {
                                        ImGui.text(i + ":");
                                        ImGui.sameLine();
                                        tfPlayerVars.get(i).show();
                                        ImGui.sameLine();
                                        if (ImGui.button("X##playerVars" + i)) {
                                            graph.playerVariables.remove(i);
                                        }
                                    }
                                }

                                ImGui.separator();
                                if(ImGui.collapsingHeader("Subroutines")) {
                                    if (ImGui.button("Add Subroutine")) {
                                        graph.addSubroutine("newSub" + graph.subroutines.size());
                                    }

//                                    ImGui.text("Subroutines");
                                    for (int i = 0; i < tfSubroutines.size(); i++) {
                                        ImGui.text(i + ":");
                                        ImGui.sameLine();
                                        tfSubroutines.get(i).show();
                                        ImGui.sameLine();
                                        if (ImGui.button("X##Subroutines" + i)) {
                                            graph.subroutines.remove(i);
                                        }
                                    }
                                }

//                                ImGui.popItemWidth();

                            }
                            ImGui.endGroup();
                            ImGui.endChild();
                        }

                        ImGui.sameLine();


                        nodeEditorRenderer.show(cursorPos, isLoading);
//                        float headerMaxY = 0;
//                        NodeEditor.begin("Editor");
//                        {
//                            for (Node node : graph.getNodes().values()) {
//                                if (isLoading) {
//                                    NodeEditor.setNodePosition(node.getID(), node.posX, node.posY);
//                                }
//                            }
//
//                            if(isLoading){
//                                isLoading = false;
//                            }
//
//                            for (Node node : graph.getNodes().values()) {
//                                float maxWidth = 0;
//                                NodeEditor.beginNode(node.getID());
//                                {
//
//                                    if(!ImGui.isMouseDown(0)) {
//                                        if (node.posX != NodeEditor.getNodePositionX(node.getID()) || node.posY != NodeEditor.getNodePositionY(node.getID())) {
//                                            node.posX = NodeEditor.getNodePositionX(node.getID());
//                                            node.posY = NodeEditor.getNodePositionY(node.getID());
//                                            promptSave = true;
//                                        }
//                                    }
//
//                                    //Node Title
//                                    if(node.isEditingTitle && editingNodeTitle == node) {
//                                        ImString string = new ImString();
//                                        string.set(node.getName());
//                                        ImGui.pushItemWidth(150);
//                                        if(ImGui.inputText("##", string, ImGuiInputTextFlags.EnterReturnsTrue)){
//                                            node.setName(string.get());
//                                            node.isEditingTitle = false;
//                                        }
//                                        ImGui.popItemWidth();
//                                    }else{
//                                        ImGui.text(node.getName());
//                                        if(ImGui.isItemHovered() && ImGui.isMouseDoubleClicked(0) && node.canEditTitle){
//                                            node.isEditingTitle = true;
//                                            editingNodeTitle = node;
//                                        }
//                                    }
//
//                                    if(node.hasTitleBar()){
//                                        headerMin = ImGui.getItemRectMin();
//                                        headerMaxY = ImGui.getItemRectMaxY();
//                                    }
//
//                                    //Node Custom UI
//                                    for(UiComponent uiComponent : node.uiComponents)
//                                    {
//                                        uiComponent.show();
//                                    }
//                                    node.UI();
//
//                                    int max = Math.max(node.outputPins.size(), node.inputPins.size());
//                                    ImGui.newLine();
//                                    for (int i = 0; i < max; i++) {
//                                        ImGui.newLine();
//                                        if (node.inputPins.size() > i) {
//                                            Pin inPin = node.inputPins.get(i);
//
//                                            if(inPin.isVisible()){
//                                                NodeEditor.beginPin(inPin.getID(), NodeEditorPinKind.Input);
//                                                drawPinShapeAndColor(inPin);
////                                            ImGui.dummy(10, 10);
//                                                inPin.pinSize = 12;
//                                                ImGui.dummy(inPin.pinSize, inPin.pinSize);
//
//                                                if (inPin.getName().length() > 0) {
//                                                    ImGui.sameLine();
//                                                    ImGui.text(inPin.getName());
//                                                }
//
//                                                NodeEditor.pinPivotAlignment(0f, .5f);
//                                                NodeEditor.endPin();
//
//                                                if (ImGui.isItemClicked() && holdingPinID == -1) {
//                                                    lastActivePin = inPin.getID();
//                                                }
//
//                                                ImGui.sameLine();
//                                                ImGui.beginGroup();
//                                                ImGui.pushItemWidth(150);
//                                                inPin.UI();
//                                                ImGui.popItemWidth();
//                                                ImGui.endGroup();
//                                            }
//                                        }
//
//                                        if (node.width != -1) {
//                                            if (!node.hasTitleBar()) {
//                                                ImGui.sameLine(1);
//                                            } else {
//                                                ImGui.sameLine(node.width);
//                                            }
//                                        }
//
//                                        if (node.outputPins.size() > i) {
//                                            Pin outPin = node.outputPins.get(i);
//
//                                            NodeEditor.beginPin(outPin.getID(), NodeEditorPinKind.Output);
//                                            drawPinShapeAndColor(outPin);
////                                            ImGui.dummy(10, 10);
//                                            ImGui.dummy(outPin.pinSize, outPin.pinSize);
//
//                                            if (outPin.getName().length() > 0) {
//                                                ImGui.sameLine();
//                                                ImGui.text(outPin.getName());
//                                            }
//
//                                            NodeEditor.pinPivotAlignment(0, 0.5f);
//                                            NodeEditor.endPin();
//
//                                            if (ImGui.isItemClicked() && holdingPinID == -1) {
//                                                lastActivePin = outPin.getID();
//                                            }
//                                            ImGui.sameLine();
//                                        }
//                                        if(maxWidth < NodeEditor.getNodePositionX(node.getID()) + NodeEditor.getNodeSizeX(node.getID()) - NodeEditor.getStyle().getNodePadding().x){
//                                            maxWidth = NodeEditor.getNodePositionX(node.getID()) + NodeEditor.getNodeSizeX(node.getID()) - NodeEditor.getStyle().getNodePadding().x;
//                                        }
//                                    }
//
////                                    if(maxWidth < node.width){
////                                        maxWidth = node.width;
////                                    }
//                                    if(headerMax == null){
//                                        headerMax = new ImVec2();
//                                    }
//                                    headerMax.set(NodeEditor.getNodePositionX(node.getID()) < 0 ? (NodeEditor.getNodeSizeX(node.getID()) + NodeEditor.getNodePositionX(node.getID())) - NodeEditor.getStyle().getNodePadding().x : maxWidth, headerMaxY);
////                                    headerMax = new ImVec2(maxWidth, headerMaxY);
//                                }
//                                NodeEditor.endNode();
//                                float x = ImGui.getMousePosX();
//                                float y = ImGui.getMousePosY();
//
////                                ImGui.getWindowDrawList().addRect(x, y,x + 4, y + 4, ImColor.floatToColor(1, 0, 0, 1));
//
//                                //draw color ontop of titlebar
//                                if(ImGui.isItemVisible() && node.hasTitleBar()){
//                                    int alpha = (int) (ImGui.getStyle().getAlpha() * 255);
//
//                                    ImDrawList drawList = NodeEditor.getNodeBackgroundDrawList(node.getID());
//                                    float halfBorderWidth = NodeEditor.getStyle().getNodeBorderWidth() * 0.5f;
//
//                                    float uvX = (headerMax.x - headerMin.x) / (4.0f * 100);
//                                    float uvY = (headerMax.y - headerMin.y) / (4.0f * 100);
//
//                                    if ((headerMax.x > headerMin.x) && (headerMax.y > headerMin.y)) {
//                                        drawList.addRectFilled(headerMin.x - (8 - halfBorderWidth), headerMin.y - (8 - halfBorderWidth), headerMax.x + (8 - halfBorderWidth), headerMax.y + (0), ImColor.intToColor(node.getRed(), node.getGreen(), node.getBlue(), node.getAlpha()), NodeEditor.getStyle().getNodeRounding(), ImDrawFlags.RoundCornersTop);
//                                    }
//
//                                    headerSeparatorMin.set(headerMin.x, headerMin.y);
//                                    headerSeparatorMax.set(headerMax.x, headerMax.y);
//
//                                    if ((headerSeparatorMax.x > headerSeparatorMin.x) && (headerSeparatorMax.y > headerSeparatorMin.y)) {
//                                        drawList.addLine(headerMin.x - 8 - halfBorderWidth, headerMax.y, headerMax.x + (8 - halfBorderWidth), headerMax.y, ImColor.intToColor(node.getRed(), node.getGreen(), node.getBlue(), 96 * alpha / (3 * 255)), 1);
//                                    }
//                                }
//
//                                if (node.width == -1)
//                                {
//                                    node.width = NodeEditor.getNodeSizeX(node.getID());
//                                }
//
//                                //TODO Throttle execute as this doesn't need to run a million times a second
//                                node.execute();
//                            }
//                        }



//                        int uniqueLinkId = 1;
//                        for (Node node : graph.getNodes().values()) {
//                            for (Pin pin : node.inputPins) {
////                                if (pin.connectedTo != -1) {
//////                                        float[] pincolor = getPinColor(pin);
////                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowMarkerDistance, 50);
////                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowDuration, 1000);
////                                    NodeEditor.pushStyleVar(NodeEditorStyleVar.FlowSpeed, 25);
////
////                                    NodeEditor.link(uniqueLinkId++, pin.getID(), pin.connectedTo, pin.getColor().x, pin.getColor().y, pin.getColor().z, pin.getColor().w, 2);
////
////                                    NodeEditor.popStyleVar(3);
////                                }
//
//                                for (int i = 0; i < pin.connectedToList.size(); i++) {
//                                    NodeEditor.link(uniqueLinkId++, pin.getID(), pin.connectedToList.get(i), pin.getColor().r, pin.getColor().g, pin.getColor().b, pin.getColor().a, 2);
//                                }
//                            }
//                        }
//
//                        if (NodeEditor.beginCreate()) {
//                            holdingPinID = lastActivePin;
////                            curSelectedPinDataType = graph.findPinById((int) lastActivePin).getDataType();
//                            curSelectedPinDataType = graph.findPinById((int) lastActivePin);
//                            checkPinConnections();
//                        } else {
//                            //Open context menu if pin link dropped without connecting to another pin
//                            if (holdingPinID != -1 && !(LINKA.get() != 0 || LINKB.get() != 0)) {
//                                lastHoldingPinID = holdingPinID;
//                                holdingPinID = -1;
//                                curSelectedPinDataType = null;
//
////                            System.out.println(LINKA.get() + " : " + LINKB.get());
////                                setNextWindowPos(cursorPos.x, cursorPos.y, ImGuiCond.Always);
//                                ImGui.setNextWindowPos(cursorPos.x, cursorPos.y, ImGuiCond.Appearing);
//                                ImGui.openPopup("context_menu" + id);
//                                justOpenedContextMenu = true;
//                            }
//                            LINKA.set(0);
//                            LINKB.set(0);
//                        }
//                        NodeEditor.endCreate();
//
//                        if (NodeEditor.beginDelete()) {
//                            int size = NodeEditor.getSelectedObjectCount();
//                            long[] list = new long[size];
//                            NodeEditor.getSelectedNodes(list, size);
//                            ImLong link1 = new ImLong();
//                            ImLong link2 = new ImLong();
//                            ImLong link3 = new ImLong();
//                            if (NodeEditor.queryDeletedLink(link1, link2, link3)) {
//                                Pin pin1 = graph.findPinById((int) link2.get());
//                                Pin pin2 = graph.findPinById((int) link3.get());
//
//                                pin1.remove(pin2.getID());
//                                pin2.remove(pin1.getID());
//                                promptSave = true;
////                                pin1.connectedTo = -1;
////                                pin2.connectedTo = -1;
//                            }
//
//                            for (int i = 0; i < list.length; i++) {
//                                ImLong nodeID = new ImLong();
//                                if (NodeEditor.queryDeletedNode(nodeID)) {
//                                    graph.removeNode((int) nodeID.get());
//                                    NodeEditor.deselectNode(nodeID.get());
//                                    promptSave = true;
//                                }
//
//                            }
//
//                        }
//                        NodeEditor.endDelete();
//                        NodeEditor.suspend();
//
//                        //Context Menu
//                        final long pinWithContextMenu = NodeEditor.getPinWithContextMenu();
//                        if (pinWithContextMenu != -1) {
//                            ImGui.openPopup("pin_menu" + id);
//                            ImGui.getStateStorage().setInt(ImGui.getID("node_pin_id"), (int) pinWithContextMenu);
//                        }
//
//                        if(ImGui.isPopupOpen("pin_menu" + id)){
//                            final int targetPin = ImGui.getStateStorage().getInt(ImGui.getID("node_pin_id"));
//                            Pin pin = graph.findPinById(targetPin);
//                            if(ImGui.beginPopup("pin_menu" + id)){
//                                pin.contextMenu();
//                                if(pin.isCanDelete()){
//
//                                    if(ImGui.menuItem("Delete Pin")){
//                                        pin.getNode().removePinById(targetPin);
//                                        ImGui.closeCurrentPopup();
//                                    }
//
//                                }
//                            }
//                            ImGui.endPopup();
//                        }
//
//                        final long nodeWithContextMenu = NodeEditor.getNodeWithContextMenu();
//                        if(nodeWithContextMenu != -1){
//                            NodeEditor.selectNode(nodeWithContextMenu, true);
//                            ImGui.openPopup("node_menu" + id);
//                            ImGui.getStateStorage().setInt(ImGui.getID("node_id"), (int)nodeWithContextMenu);
//                        }
//
//                        if(ImGui.isPopupOpen("node_menu" + id))
//                        {
//                            final int targetNode = ImGui.getStateStorage().getInt(ImGui.getID("node_id"));
//
//                            if(ImGui.beginPopup("node_menu" + id)){
//                                //TODO Duplicate all info attatched to this node
//                                if(ImGui.menuItem("Duplicate Node " + graph.getNodes().get(targetNode).getName()))
//                                {
//                                    Node newInstance = null;
//                                    try{
//                                        Node target = graph.getNodes().get(targetNode);
//                                        newInstance = target.getClass().getDeclaredConstructor(Graph.class).newInstance(graph);
//                                        graph.addNode(newInstance);
//                                        NodeEditor.setNodePosition(newInstance.getID(), NodeEditor.toCanvasX(ImGui.getCursorScreenPosX()), NodeEditor.toCanvasY(ImGui.getCursorScreenPosY()));
//
//                                        int defaultTotal = newInstance.inputPins.size();
//                                        for (int i = 0; i < target.inputPins.size(); i++) {
//                                            if(i > defaultTotal - 1){
//                                                Pin newPin = target.inputPins.get(i).getClass().getDeclaredConstructor().newInstance();
//                                                newPin.setNode(newInstance);
//                                                newPin.setName(target.inputPins.get(i).getName());
//                                                newPin.setCanDelete(true);
//                                                newInstance.addCustomInput(newPin);
//                                            }
//                                        }
//
//                                        newInstance.copy(target);
//                                    }catch (Exception e){
//                                        e.printStackTrace();
//                                    }
//                                    ImGui.closeCurrentPopup();
//                                }
//
//                                ImGui.separator();
//
//                                if(ImGui.menuItem("Delete " + graph.getNodes().get(targetNode).getName())){
//                                    NodeEditor.deleteNode(targetNode);
//                                    ImGui.closeCurrentPopup();
//                                }
//
//                                if(ImGui.menuItem("Preview Source")){
//                                    Global.setStorage("preview_source", targetNode);
//                                    Global.setStorage("preview_open_request", 1);
////                                    ImGui.closeCurrentPopup();
//                                }
//                                ImGui.endPopup();
//                            }
//                        }
//
//                        final long linkWithContextMenu = NodeEditor.getLinkWithContextMenu();
//                        if(linkWithContextMenu != -1){
//                            ImGui.openPopup("link_menu" + id);
//                            ImGui.getStateStorage().setInt(ImGui.getID("link_id"), (int)linkWithContextMenu);
//                        }
//
//                        if(ImGui.isPopupOpen("link_menu" + id)){
//                            final int targetLink = ImGui.getStateStorage().getInt(ImGui.getID("link_id"));
//                            if(ImGui.beginPopup("link_menu" + id)){
//                                if(ImGui.menuItem("Delete Link")){
//                                    if(NodeEditor.deleteLink(targetLink))
//                                    {
//
//                                    }
//                                    ImGui.closeCurrentPopup();
//                                }
//                                ImGui.endPopup();
//                            }
//                        }
//
//                        if((int)Global.getStorage("preview_open_request") == 1){
//                            ImGui.openPopup("PreviewSource" + id);
//                            Global.setStorage("preview_open_request", 0);
//                        }
//
//                        if(ImGui.isPopupOpen("PreviewSource" + id))
//                        {
////                            ImGui.setNextWindowPos(glfwWindow.getWidth() * 0.5f, glfwWindow.getHeight() * 0.5f, ImGuiCond.Always);
//                            if(ImGui.beginPopupModal("PreviewSource" + id, ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoResize | ImGuiWindowFlags.NoMove | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoSavedSettings))
//                            {
//                                int targetID = (int)Global.getStorage("preview_source");
//                                ImGui.text(graph.getNodes().get(targetID).getOutput());
//
//                                ImGui.separator();
//                                if(ImGui.button("Close")){
//                                    ImGui.closeCurrentPopup();
//                                }
//                                ImGui.endPopup();
//                            }
//                        }
//
//                        if (NodeEditor.showBackgroundContextMenu()) {
//                            ImGui.openPopup("context_menu" + id);
//                            lastHoldingPinID = -1;
//                            justOpenedContextMenu = true;
//                        }
//
//                        //Copy Paste
////                        System.out.println(ImGui.getWindowPosX());
////                        System.out.println(ImGui.getMousePosX() - ImGui.getWindowPosX() + " : " + (ImGui.getMousePosX() - (ImGui.getWindowPosX() + ImGui.getWindowSizeX())));
//
////                        System.out.println(NodeEditor.toScreenX(0) + " : " + NodeEditor.toCanvasX(0));
//
//                        if(NodeEditor.beginShortcut()) {
//                            if (NodeEditor.acceptCopy()) {
//                                int size = NodeEditor.getSelectedObjectCount();
//                                long[] list = new long[size];
//                                NodeEditor.getSelectedNodes(list, size);
//
//                                ArrayList<Node> nodeList = new ArrayList<>();
//                                for (int i = 0; i < list.length; i++) {
//                                    Node node = graph.getNodes().get((int)list[i]);
//                                    nodeList.add(node);
//                                }
//                                NodeCopyPasteHandler.copy(nodeList);
//                            }
//
//                            if(NodeEditor.acceptPaste()){
//                                System.out.println("Paste");
//                                NodeCopyPasteHandler.paste(graph);
//                            }
//                        }
//                        NodeEditor.endShortcut();
//
//                        if(ImGui.isPopupOpen("context_menu" + id))
//                        {
//                            if(ImGui.beginPopup("context_menu" + id)){
//                                canvasXPos = NodeEditor.toCanvasX(ImGui.getCursorScreenPosX());
//                                canvasYPos = NodeEditor.toCanvasY(ImGui.getCursorScreenPosY());
//
//                                if(nodeInstanceCache.isEmpty())
//                                {
//                                    for (Class<? extends Node> node : nodeList) {
//                                        Constructor<? extends Node> nodeClass = null;
//                                        Node instance = null;
//                                        try {
//                                            nodeClass = node.getDeclaredConstructor(Graph.class);
//                                            instance = nodeClass.newInstance(graph);
//                                            nodeInstanceCache.add(instance);
//                                        } catch (Exception e) {
//                                            System.out.println(nodeClass.getName());
//                                            e.printStackTrace();
//                                        }
//
////                                        if(instance != null) {
////                                            createContextMenuItem(instance, 0);
////                                        }
//                                    }
//                                }else {
//                                    if (justOpenedContextMenu) {
//                                        nodeSearch.set("");
//                                        ImGui.setKeyboardFocusHere(0);
//                                        justOpenedContextMenu = false;
//                                    }
//
//                                    ImGui.pushItemWidth(350);
//                                    ImGui.inputTextWithHint("##", "Search Node", nodeSearch);
//                                    ImGui.popItemWidth();
////                                    ImGui.setNextWindowSize(250, 0, ImGuiCond.Once);
//                                    ImGui.beginChild("ScrollArea", 350, (contextMenuAutoSize ? 500 : contextMenuSize));
//                                    for (Node instance : nodeInstanceCache) {
//                                        createContextMenuItem(instance, 0);
//                                    }
//
//                                    float avail = ImGui.getContentRegionAvailY();
//                                    if (!contextMenuOpen && avail != -1)
//                                    {
//                                        if (avail <= 0) {
//                                            contextMenuAutoSize = true;
//                                        } else {
//                                            contextMenuAutoSize = false;
//                                            contextMenuSize = 500 - avail;
//                                        }
//                                        contextMenuOpen = true;
//                                    }
//                                    ImGui.endChild();
//                                }
//                                ImGui.endPopup();
//                            }
//                        }else{
//                            contextMenuOpen = false;
//                            contextMenuAutoSize = true;
//                        }
//
//                        //Handle in node popups
//                        Popup popup = PopupHandler.currentPopup;
//                        if(popup != null){
//                            ImGui.setNextWindowPos(cursorPos.x, cursorPos.y + 5, ImGuiCond.Appearing);
//                            if(ImGui.isPopupOpen(popup.id.toString())){
//                                if(ImGui.beginPopup(popup.id.toString())){
//                                    if(popup.show()){
//                                        PopupHandler.remove(popup);
//                                        ImGui.closeCurrentPopup();
//                                    }
//                                }
//                                ImGui.endPopup();
//                            }
//                        }
//
//                        NodeEditor.resume();
//                        NodeEditor.end();

                    }
                    ImGui.endTabItem();
                }

                if(ImGui.beginTabItem("Output")){
                    EDITOR.render("TextOutput");
                    ImGui.endTabItem();

                    if(!outputInFocus){
                        outputInFocus = true;
                        String compiledText = Textformatter.prettyPrint(Compiler.compile(graph, settings));
                        EDITOR.setText(compiledText);
                        ClipboardContents.set(compiledText);
                    }
                }
            }
            ImGui.endTabBar();

            ImGui.sameLine();
        }
        ImGui.end();

        //if the entire frame is successful then loading is likely to be finished so we set loading to false
        if(isLoading){
            isLoading = false;
        }
    }

//    private void createContextMenuItem(Node instance, int depth) {
//        if(nodeSearch.get().toLowerCase().length() > 0){
//            if(!instance.getName().toLowerCase().contains(nodeSearch.get())){
//                return;
//            }
//        }
//        boolean canCreate = true;
//        //TODO filter out pins that can't connect to each other
//        Pin lastPin = graph.findPinById((int)lastHoldingPinID);
//
//        if(lastPin != null){
//            canCreate = false;
//            if(lastPin.getPinType() == Pin.PinType.Input){
//                for (int i = 0; i < instance.outputPins.size(); i++) {
//                    Pin outPin = instance.outputPins.get(i);
//
//                    if(lastPin.getClass() == outPin.getClass()){
//                        canCreate = true;
//                        break;
//                    }
//                }
//            }else if(lastPin.getPinType() == Pin.PinType.Output){
//                for (int i = 0; i < instance.inputPins.size(); i++) {
//                    Pin inPin = instance.inputPins.get(i);
//
//                    if(lastPin.getClass() == inPin.getClass()){
//                        canCreate = true;
//                        break;
//                    }
//                }
//            }
//        }
//
//
//        if(canCreate) {
//            if (ImGui.menuItem(instance.getName() + "##" + instance.getID())) {
//
//            }
//            if(ImGui.isItemHovered() && ImGui.isItemClicked()){
//                try {
//                    Node newInstance = instance.getClass().getDeclaredConstructor(Graph.class).newInstance(graph);
//                    graph.addNode(newInstance);
//                    newInstance.posX = canvasXPos;
//                    newInstance.posY = canvasYPos;
//                    NodeEditor.setNodePosition(newInstance.getID(), canvasXPos, canvasYPos);
//                    promptSave = true;
//                    autoConnectLink(newInstance);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return;
//                }
//                ImGui.closeCurrentPopup();
//            }
//        }
//    }

//    private void checkPinConnections()
//    {
//        if(NodeEditor.queryNewLink(LINKA, LINKB)){
//            Pin sourcePin = graph.findPinById((int)LINKA.get());
//            Pin targetPin = graph.findPinById((int)LINKB.get());
//
//            if(sourcePin.getNode() == targetPin.getNode()){
//                return;
//            }
//
//            if(!(sourcePin.getClass() == targetPin.getClass()) || !(sourcePin.getPinType() != targetPin.getPinType())){
//                NodeEditor.rejectNewItem(1, 0, 0, 1, 1);
//                holdingPinID = -1;
//                return;
//            }
//
//            if(NodeEditor.acceptNewItem(0, 1, 0, 1, 1)){
//                promptSave = true;
//                if(sourcePin.connect(targetPin)){
//                    //sets the holder pin to -1 otherwise context menu will popup
//                    holdingPinID = -1;
//                }
////                //disconnect old connections
////                if(sourcePin.connectedTo != -1){
////                    Pin oldPin = graph.findPinById(sourcePin.connectedTo);
////                    if(oldPin != null) {
////                        oldPin.connectedTo = -1;
////                    }
////                }
////
////                if(targetPin.connectedTo != -1){
////                    Pin oldPin = graph.findPinById(targetPin.connectedTo);
////                    if(oldPin != null) {
////                        oldPin.connectedTo = -1;
////                    }
////                }
////
////                //create a new link connection
////                if(sourcePin != null && targetPin != null){
////                    if(sourcePin.connectedTo != targetPin.connectedTo || (targetPin.connectedTo == -1 || sourcePin.connectedTo == -1)){
////                        sourcePin.connectedTo = targetPin.getID();
////                        targetPin.connectedTo = sourcePin.getID();
////                        holdingPinID = -1;
////                    }
////                }
//            }
//        }
//    }

//    private void autoConnectLink(Node newInstance){
//        //check if context menu opened by dragging a pin
//        if(lastHoldingPinID != -1){
//            Pin pin = graph.findPinById((int)lastActivePin);
////            System.out.println(pin.getPinType());
//            switch (pin.getPinType()){
//                case Input:
//                    for (int i = 0; i < newInstance.outputPins.size(); i++) {
//                        Pin instancePin = newInstance.outputPins.get(i);
////                        if(instancePin.getDataType() == pin.getDataType())
//                        if(instancePin.getClass() == pin.getClass())
//                        {
//                            //if a successful connection is made then return/break
//                            if(pin.connect(instancePin)){
//                                break;
//                            }
//                        }
//                    }
//                    break;
//                case Output:
//                    for (int i = 0; i < newInstance.inputPins.size(); i++) {
//                        Pin instancePin = newInstance.inputPins.get(i);
////                        if(instancePin.getDataType() == pin.getDataType())
//                        if(instancePin.getClass() == pin.getClass())
//                        {
//                            //if a successful connection is made then return/break
//                            if(pin.connect(instancePin)){
//                                break;
//                            }
//                        }
//                    }
//                    break;
//            }
//            lastHoldingPinID = -1;
//        }
//    }

//    private void drawPinShapeAndColor(Pin pin){
//        float size = 10f;
//        float posX = ImGui.getCursorPosX();
//        float posY = ImGui.getCursorPosY();
//        boolean pinDragSame = true;
////        if(curSelectedPinDataType != null){
////            pinDragSame = pin.getClass() == curSelectedPinDataType.getClass();
////        }
//        pin.draw(ImGui.getWindowDrawList(), posX, posY, pin.isConnected(), pinDragSame);
//    }

//    public void addNodeToList(Class<? extends Node> node){
//        nodeList.add(node);
//        Collections.sort(nodeList, new Comparator<Class<? extends Node>>() {
//            @Override
//            public int compare(Class<? extends Node> o1, Class<? extends Node> o2) {
//                return o1.getName().compareToIgnoreCase(o2.getName());
//            }
//        });
//    }

    public String getFileName() {
        return fileName;
    }

    public void setFocus(boolean focus){
        this.isFocused = focus;
    }
}
