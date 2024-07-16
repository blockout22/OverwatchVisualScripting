package ovs.graph;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditor;
import imgui.extension.texteditor.TextEditor;
import imgui.flag.*;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImString;
import org.lwjgl.glfw.GLFW;
import ovs.*;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.TextField;
import ovs.graph.importer.ScriptImporter;
import ovs.graph.node.*;
import ovs.graph.node.interfaces.NodeDisabled;
import ovs.graph.node.interfaces.NodeGroupOnly;
import ovs.graph.pin.Pin;
import ovs.graph.pin.PinCombo;
import ovs.graph.pin.PinString;
import ovs.graph.save.GraphSaver;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;

public class GraphWindow {
    private ImGuiWindow imGuiWindow;
    private GlfwWindow glfwWindow;

    private final ImBoolean closable = new ImBoolean(true);
    private ConfirmSaveDialog saveDialog = new ConfirmSaveDialog();
    private ImportDialog importDialog = new ImportDialog();

    public String id;
    private String fileName = "untitled";

    private GraphSaver graphSaver;
    protected Graph graph;
    private NodeEditorRenderer nodeEditorRenderer;

    private TextEditor EDITOR;

    private ImString searchText = new ImString();
    private ImVec2 cursorPos;


    private Pin curSelectedPinDataType = null;

    private boolean isFocused = false;
    private boolean animationCompleted = false;
    private boolean promptSave = false;
    private boolean showSavedText = false;
    private boolean isLoading = false;
    private boolean outputInFocus = false;
    private boolean searching = false;

    private final ArrayList<TextField> tfRulesList = new ArrayList<>();
    private final ArrayList<TextField> tfGlobalVars = new ArrayList<>();
    private final ArrayList<TextField> tfPlayerVars = new ArrayList<>();
    private final ArrayList<TextField> tfSubroutines = new ArrayList<>();
    private ArrayList<Node> searchResults = new ArrayList<>();

    private Settings settings = new Settings();


    private ImInt ruleListSelected = new ImInt();

    private double animation_time = 1.0f; // animation duration in seconds
    private double animation_start_time = ImGui.getTime();

    private UndoHandler undoHandler = new UndoHandler();

    private ArrayList<Node> ruleNodes = new ArrayList<>();

    private GraphChangeListener graphChangeListener;

    private long lastSaveTime = System.currentTimeMillis();

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

        //auto save thread

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(closable.get() && Global.isRunning){
                    try {
                        Thread.sleep(5000);
                        if(!Global.isRunning){
                            break;
                        }
                        if(lastSaveTime + Duration.ofMinutes(15).toMillis() < System.currentTimeMillis()){
                            nodeEditorRenderer.setAsCurrentEditor();
                            boolean success = graphSaver.save(fileName, settings, graph);
                            if(success){
                                showSavedText = true;
                                lastSaveTime = System.currentTimeMillis();
                                TaskSchedule.addTask(new Task() {
                                    @Override
                                    public void onFinished() {
                                        showSavedText = false;
                                    }
                                }, 5000);
                            }
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();

        EDITOR = new TextEditor();

        nodeEditorRenderer = new NodeEditorRenderer(id, graph, NodeDisabled.class, NodeGroupOnly.class);
        graphChangeListener = new GraphChangeListener() {
            @Override
            public void changed() {
                promptSave = true;
            }
        };
        nodeEditorRenderer.addChangeListener(graphChangeListener);

        graph.getNodes().addListChangedListener(new ListChangedListener() {
            public void onChanged() {
                tfRulesList.clear();
                ruleNodes.clear();
                for(Node node : graph.getNodes().getList()){
                    if (node instanceof NodeRule) {
                        TextField tf = new TextField(true);
                        ruleNodes.add(node);
                        tf.addChangedListener(new ChangeListener() {
                            @Override
                            public void onChanged(String oldValue, String newValue) {
                                node.setName(newValue);
                            }
                        });
                        tf.setText(node.getName());
                        tfRulesList.add(tf);
                    }
                }
            }
        });

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

                            //TODO store the previous value while still allowing user to change the current TextField
                            //Check for naming conflict
//                            for (int k = 0; k < graph.globalVariables.size(); k++) {
//                                if(graph.globalVariables.get(k).name.equals(newValue)){
//                                    tf.setText(oldValue);
//                                    break;
//                                }
//                            }

                            for(Node node : graph.getNodes().getList()){
                                //if variable names are changed then update Get and Set nodes that use the changed variables to the new name
                                if(node instanceof NodeGetVariable){
                                    NodeGetVariable ngv = (NodeGetVariable) node;

                                    if(ngv.variableBox.getSelectedValue().equals("Global." + oldValue)){
                                        //this will refresh the options list inside the combobox
                                        ngv.populateCombobox();
                                        ngv.variableBox.selectValue("Global." + newValue);
                                        ngv.width = -1;
                                    }
                                }

                                if(node instanceof NodeSetVariable)
                                {
                                    NodeSetVariable nsr = (NodeSetVariable) node;
                                    if(nsr.variableBox.getSelectedValue().equals("Global." + oldValue)){
                                        //this will refresh the options list inside the combobox
                                        nsr.populateCombobox();
                                        nsr.variableBox.selectValue("Global." + newValue);
                                        nsr.width = -1;
                                    }
                                }
                            }
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

                            for(Node node : graph.getNodes().getList()){
                                //if variable names are changed then update Get and Set nodes that use the changed variables to the new name
                                if(node instanceof NodeGetVariable){
                                    NodeGetVariable ngv = (NodeGetVariable) node;

                                    if(ngv.variableBox.getSelectedValue().equals("Event Player." + oldValue)){
                                        //this will refresh the options list inside the combobox
                                        ngv.populateCombobox();
                                        ngv.variableBox.selectValue("Event Player." + newValue);
                                        ngv.width = -1;
                                    }
                                }

                                if(node instanceof NodeSetVariable)
                                {
                                    NodeSetVariable nsr = (NodeSetVariable) node;
                                    if(nsr.variableBox.getSelectedValue().equals("Event Player." + oldValue)){
                                        //this will refresh the options list inside the combobox
                                        nsr.populateCombobox();
                                        nsr.variableBox.selectValue("Event Player." + newValue);
                                        nsr.width = -1;
                                    }
                                }
                            }
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

        graph.getNodes().triggerOnChanged();
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
        nodeEditorRenderer.setGraph(graph);
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

    private void handleImportActions(String[] actions, Node parentNode, ClassLoader loader, ImVec2 offset)
    {
        String packageName = "ovs.graph.node.";
        for (int i = 0; i < actions.length; i++) {

            String name = ScriptImporter.getActionName(actions[i]);

            Node node = null;
            if(name.startsWith("Global.")){
                String varName = name.split("=")[0].split("\\.")[1].trim();
                graph.addGlobalVariable(varName);
                node = new NodeSetGlobalVariable(graph);
            }else if(name.contains("=")){
                String varName = name.substring(0, name.lastIndexOf("=")).split("\\.")[1].trim();
                graph.addPlayerVariable(varName);
                node = new NodeSetPlayerVariable(graph);
            }else{
                String className = "Node" + name.replace(" ", "").replace("-", "");
                Class nodeClass = null;
                try {
                    nodeClass = Class.forName(packageName + className, true, loader);
                } catch (ClassNotFoundException e) {
                    System.err.println("ClassName: " + className);
                    e.printStackTrace();
                    continue;
                }

                try {
                    node = (Node) nodeClass.getDeclaredConstructor(Graph.class).newInstance(graph);
                } catch (InstantiationException | InvocationTargetException | NoSuchMethodException |
                         IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
            }

            node.posX = parentNode.posX - offset.x;
            node.posY = parentNode.posY - offset.y;

            offset.y -= 100;
            graph.addNode(node);
            NodeEditor.setNodePosition(node.getID(), NodeEditor.toCanvasX(node.posX), NodeEditor.toCanvasY(node.posY));
            parentNode.inputPins.get(i).connect(node.outputPins.get(0));

            if(actions[i].startsWith("Global.")){
                String variable = actions[i].split("=")[0].split("\\.")[1].trim();
                String value = actions[i].split("=")[1].trim();
                handleImportArguments(new String[]{variable, value}, node, loader, offset);
            }else if(name.contains("=")){
                String player = name.split("=")[0].split("\\.")[0].trim();;
                String variable = name.split("=")[0].split("\\.")[1].trim();
                String value = name.split("=")[1].trim();
                handleImportArguments(new String[]{player, variable, value}, node, loader, offset);
            }

            String[] args = ScriptImporter.getArguments(actions[i]);
            for (int j = 0; j < args.length; j++) {
//                System.out.println("Arg: " + args[j]);
            }
            if (args.length > 0) {
                try {
                    handleImportArguments(args, node, loader, offset);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isNumber(String name){
        try{
            float value = Float.parseFloat(name);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    private void handleImportArguments(String[] arguments, Node parentNode, ClassLoader loader, ImVec2 offset) {
        String packageName = "ovs.graph.node.";
        for (int i = 0; i < arguments.length; i++) {
            String name = ScriptImporter.getActionName(arguments[i]);
            Node node = null;

            if(name.startsWith("\"") && name.endsWith("\""))
            {
                if(parentNode.inputPins.get(i) instanceof PinString){
                    PinString pinString = (PinString)parentNode.inputPins.get(i);
                    pinString.setValue(name.substring(1, name.length() - 1));
                    continue;
                }
                continue;
            }

            if(parentNode.inputPins.get(i) instanceof PinCombo){
                PinCombo comboPin = (PinCombo) parentNode.inputPins.get(i);
                comboPin.selectValue(name);
                continue;
            }


            if(isNumber(name)){
                float value = Float.valueOf(name);
                node = new NodeNumber(graph);
                System.out.println("Created Number...");
                ((NodeNumber) node).setValue(value);
            }
            else if(name.startsWith("Global.")){
//                String varName = name.substring(0, name.lastIndexOf(" ")).split("\\.")[1].trim();
                String varName = name.split("=")[0].split("\\.")[1].trim();
                graph.addGlobalVariable(varName);
                node = new NodeGetGlobalVariable(graph);

            }else if(name.contains("=")){
                String varName = name.substring(0, name.lastIndexOf("=")).split("\\.")[1].trim();
                graph.addPlayerVariable(varName);
                node = new NodeGetPlayerVariable(graph);
            }else{

                String className = "Node" + name.replace(" ", "").replace("-", "");
                Class nodeClass = null;
                try {
                    nodeClass = Class.forName(packageName + className, true, loader);
                } catch (ClassNotFoundException e) {
                    System.err.println("ClassName: " + className);
                    e.printStackTrace();
                    continue;
                }

                try {
                    node = (Node) nodeClass.getDeclaredConstructor(Graph.class).newInstance(graph);
                } catch (InstantiationException | InvocationTargetException | NoSuchMethodException |
                         IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
            }
            node.posX = parentNode.posX - offset.x;
            node.posY = parentNode.posY - offset.y;

            offset.y -= 100;
            graph.addNode(node);
            NodeEditor.setNodePosition(node.getID(), NodeEditor.toCanvasX(node.posX), NodeEditor.toCanvasY(node.posY));
            parentNode.inputPins.get(i).connect(node.outputPins.get(0));

            System.out.println("Connected node: " + node.getClass().getSimpleName() + " to parent node " + parentNode.getClass().getSimpleName());

//            //if it contains these symbols it's probably using a compare
//            if(arguments[i].contains("=") || arguments[i].contains(">") || arguments[i].contains("<"))
//            {
//
//            }

            if(arguments[i].startsWith("Global.")){
                String variable = arguments[i].split("=")[0].split("\\.")[1].trim();
//                String value = arguments[i].split("=")[1].trim();
                handleImportArguments(new String[]{variable}, node, loader, offset);
                continue;
            }

            String[] args = ScriptImporter.getArguments(arguments[i]);
            System.out.println("Arguments for node: " + node.getClass().getSimpleName() + " are [" + args.length + "] " + String.join(", ", args));
//            if(node instanceof NodeArray){
//                for (int j = 0; j < args.length; j++) {
//                    ((NodeArray) node).addInputPin();
//                }
//            }
            if (args.length > 0) {
                try {
                    handleImportArguments(args, node, loader, offset);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
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
                    animation_start_time = 0;
                    isFocused = true;
                    animationCompleted = false;
                }
            }

            if(ImGui.isPopupOpen("Find")) {
                float maxHeight = 400.0f; // Set this to whatever maximum height you want
                ImGui.setNextWindowSizeConstraints(400, 400, 1000, maxHeight);
                if(ImGui.beginPopup("Find")) {
                    ImGui.text("Search");
                    if (ImGui.inputText("##Search", searchText)) {
                        searchResults.clear();
                        if (!searchText.isEmpty()) {
                            for (int i = 0; i < graph.getNodes().getList().size(); i++) {
                                Node node = graph.getNodes().getList().get(i);

                                if (node.getName().toLowerCase().contains(searchText.get().toLowerCase())) {
                                    searchResults.add(node);
                                }
                            }
                        }
                    }

                    int index = 0;
                    for(Node node : graph.getNodes().getList()){
                        NodeEditor.deselectNode(node.getID());
                    }
                    for(Node node : searchResults){
                        index++;
                        if(ImGui.button(node.getName() + "##" + index)){
                            NodeEditor.selectNode(node.getID(), true);
                            NodeEditor.navigateToSelection(false, 0.5f);
                        }
                    }

                    ImGui.endPopup();
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
            }else{
                if(!animationCompleted){
                    ImGui.setWindowPos(fileName, target_pos.x, target_pos.y);
                    animationCompleted = true;
                }
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
                        nodeEditorRenderer.setAsCurrentEditor();
                        graphSaver.save(fileName, settings, graph);
                        imGuiWindow.removeGraphWindow(this);
                    }
                }else{
                    imGuiWindow.removeGraphWindow(this);
                }

                nodeEditorRenderer.removeChangeListener(graphChangeListener);
            }

            if(importDialog.isOpen()){
                int state = importDialog.show();
                if(state == 1){
                    String[] rules = ScriptImporter.splitRules(importDialog.getContent());

                    String packageName = "ovs.graph.node.";
                    ClassLoader loader = NodeCopyPasteHandler.class.getClassLoader();
                    float canvasXPos = NodeEditor.toCanvasX(ImGui.getMousePosX());
                    float canvasYPos = NodeEditor.toCanvasY(ImGui.getMousePosY());
                    ImVec2 offset = new ImVec2();
                    for(String rule : rules){
                        String ruleName = ScriptImporter.getRuleName(rule);
                        offset.x = 0;
                        try {
                            String[] event = ScriptImporter.getActionLines(ScriptImporter.LineType.event, rule);
                            String[] conditions = ScriptImporter.getActionLines(ScriptImporter.LineType.conditions, rule);
                            String[] actions = ScriptImporter.getActionLines(ScriptImporter.LineType.actions, rule);

                            Class ruleClass = Class.forName(packageName + "NodeRule", true, loader);
                            NodeRule ruleNode = (NodeRule) ruleClass.getDeclaredConstructor(Graph.class).newInstance(graph);
                            ruleNode.setName(ruleName);
                            ruleNode.comboEventOnGoing.selectValue(event[0]);
                            if(event.length == 2){
                                ruleNode.comboSub.selectValue(event[1]);
                            }else if(event.length == 3){
                                ruleNode.comboTeam.selectValue(event[1]);
                                ruleNode.comboPlayers.selectValue(event[2]);
                            }
//                            ruleNode.setComment();
                            ruleNode.posX = canvasXPos - offset.x;
                            ruleNode.posY = canvasYPos - offset.y;
                            offset.x += 100;
                            graph.addNode(ruleNode);
                            NodeEditor.setNodePosition(ruleNode.getID(), NodeEditor.toCanvasX(ruleNode.posX), NodeEditor.toCanvasY(ruleNode.posY));

                            Node parentNode;

                            NodeActionList actionList = new NodeActionList(graph);
                            for (int i = 0; i < actions.length - 1; i++) {
                                actionList.addInputPin();
                            }
                            actionList.posX = canvasXPos - offset.x;
                            actionList.posY = canvasYPos - offset.y;
                            offset.x += 200;
                            graph.addNode(actionList);
                            parentNode = actionList;
                            ruleNode.inputPins.get(1).connect(actionList.outputPins.get(0));
                            NodeEditor.setNodePosition(actionList.getID(), NodeEditor.toCanvasX(actionList.posX), NodeEditor.toCanvasY(actionList.posY));

                            try {
                                handleImportActions(actions, parentNode, loader, offset);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
//                    String content = importDialog.getContent();

//                    List<ScriptImporter.Rule> rules = ScriptImporter.importFromString(importDialog.getContent());
//
//                    for(ScriptImporter.Rule rule : rules){
//                        NodeRule nodeRule = new NodeRule(graph);
//                        nodeRule.comboEventOnGoing.selectValue(rule.eventType);
//
//                        if(rule.playerType != null){
//                            nodeRule.comboPlayers.selectValue(rule.playerType);
//                        }
//
//                        if(rule.teamType != null){
//                            nodeRule.comboTeam.selectValue(rule.teamType);
//                        }
//
//                        //TODO set positions
//                        nodeRule.posX = 0;
//                        nodeRule.posY = 0;
//                        graph.addNode(nodeRule);
//
//                        for (int i = 0; i < rule.actions.size(); i++) {
//                            String action = rule.actions.get(i);
//
//                            for (Node node : nodeEditorRenderer.nodeInstanceCache) {
//                                if(node.getName().equals(action.trim().split("\\(")[0])){
//                                    try {
//                                        Node newInstance = node.getClass().getDeclaredConstructor(Graph.class).newInstance(graph);
//                                        graph.addNode(newInstance);
//                                    } catch (Exception e) {
//                                        throw new RuntimeException(e);
//                                    }
//
//                                    break;
//                                }
//                            }
//                        }
//                    }
                }
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
                for(Node node : graph.getNodes().getList()){
                    node.onSaved();
                }

                nodeEditorRenderer.setAsCurrentEditor();
                boolean success = graphSaver.save(fileName, settings, graph);

                if(success){
                    promptSave = false;
                    showSavedText = true;
                    lastSaveTime = System.currentTimeMillis();
                    TaskSchedule.addTask(new Task() {
                        @Override
                        public void onFinished() {
                            showSavedText = false;
                        }
                    }, 5000);
                }
            }

            ImGui.sameLine();

            if(ImGui.button("Import")){
                importDialog.open();
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
//                                    ImGui.pushItemWidth(400);
//                                    ImGui.listBox("##Rules", ruleListSelected, items);
                                    for (int i = 0; i < tfRulesList.size(); i++) {
                                        tfRulesList.get(i).show();
                                        ImGui.sameLine();

                                        if(ImGui.button("->##rn" + i))
                                        {
                                            int id = ruleNodes.get(i).getID();
                                            NodeEditor.selectNode(id, false);
                                            NodeEditor.navigateToSelection(false, 0.5f);
                                        }

                                        if(i != 0){
                                            ImGui.sameLine();
                                            if(ImGui.button("^##rn" + i)){
                                                boolean swapped = graph.nodes.swap(ruleNodes.get(i), ruleNodes.get(i - 1));
                                            }
                                        }

                                        if(i != tfRulesList.size() - 1){
                                            ImGui.sameLine();
                                            if(ImGui.button("v##rn" + i)){
                                                boolean swapped = graph.nodes.swap(ruleNodes.get(i), ruleNodes.get(i + 1));
                                            }
                                        }
                                    }

//                                    ImGui.popItemWidth();
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

                                    float buttonWidth = 25.0f;
                                    float spaceWidth = 5.0f;

//                                    ImGui.pushItemWidth(250);
                                    ImGui.text("Global Variable");
                                    for (int i = 0; i < tfGlobalVars.size(); i++) {
                                        ImGui.text(i + ":");
                                        ImGui.sameLine();
                                        tfGlobalVars.get(i).show();
                                        ImGui.sameLine(400 - (3 * buttonWidth + 2 * spaceWidth));

                                        //check if this is not the first element
                                        if(i != 0){
                                            if(ImGui.button("^" + "##g" + i)){
                                                graph.globalVariables.swap(i, i - 1);

                                            };
                                            ImGui.sameLine();
                                        }else{
                                            ImGui.dummy(buttonWidth / 2 + (ImGui.getStyle().getItemSpacingX() / 2) - 1.0f, 0);
                                            ImGui.sameLine();
                                        }

                                        //check if this is not the last element
                                        if(i != tfGlobalVars.size() - 1){
                                            if(ImGui.button("v" + "##g" + i)){
                                                graph.globalVariables.swap(i, i + 1);
                                            }
                                            ImGui.sameLine();
                                        }else{
                                            ImGui.dummy(buttonWidth / 2 + (ImGui.getStyle().getItemSpacingX() / 2) - 1.0f, 0);
                                            ImGui.sameLine();
                                        }

                                        if (ImGui.button("X##globalVars" + i)) {
                                            graph.globalVariables.remove(i);
                                        }
                                    }

                                    ImGui.text("Player Variable");

                                    for (int i = 0; i < tfPlayerVars.size(); i++) {
                                        ImGui.text(i + ":");
                                        ImGui.sameLine();
                                        tfPlayerVars.get(i).show();
                                        ImGui.sameLine(400 - (3 * buttonWidth + 2 * spaceWidth));

                                        //check if this is not the first element
                                        if(i != 0){
                                            if(ImGui.button("^" + "##p" + i)){
                                                graph.playerVariables.swap(i, i - 1);

                                            };
                                            ImGui.sameLine();
                                        }else{
                                            ImGui.dummy(buttonWidth / 2 + (ImGui.getStyle().getItemSpacingX() / 2) - 1.0f, 0);
                                            ImGui.sameLine();
                                        }

                                        //check if this is not the last element
                                        if(i != tfPlayerVars.size() - 1){
                                            if(ImGui.button("v" + "##p" + i)){
                                                graph.playerVariables.swap(i, i + 1);
                                            }
                                            ImGui.sameLine();
                                        }else{
                                            ImGui.dummy(buttonWidth / 2 + (ImGui.getStyle().getItemSpacingX() / 2) - 1.0f, 0);
                                            ImGui.sameLine();
                                        }

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

            if(ImGui.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL) && ImGui.isKeyPressed(GLFW.GLFW_KEY_F)){
                ImGui.openPopup("Find");
            }

            ImGui.sameLine();
        }
        ImGui.end();

        //if the entire frame is successful then loading is likely to be finished so we set loading to false
        if(isLoading){
            isLoading = false;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFocus(boolean focus){
        this.isFocused = focus;
    }
}
