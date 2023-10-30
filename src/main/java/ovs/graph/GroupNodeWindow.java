package ovs.graph;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditor;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.type.ImBoolean;
import imgui.type.ImString;
import ovs.GlfwWindow;
import ovs.Global;
import ovs.ImGuiWindow;
import ovs.TaskSchedule;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.TextField;
import ovs.graph.node.Node;
import ovs.graph.node.NodeGroupInput;
import ovs.graph.node.interfaces.NodeDisabled;
import ovs.graph.node.interfaces.NodeGroupOnly;
import ovs.graph.pin.Pin;
import ovs.graph.save.GroupSaver;

import java.util.ArrayList;

public class GroupNodeWindow {

    private ImGuiWindow imGuiWindow;
    private GlfwWindow glfwWindow;

    private GroupSaver groupSaver;
    protected Graph graph;

    private boolean promptSave = false;
    private boolean isFocused = false;
    private boolean animationCompleted = false;
    private boolean isLoading = false;
    private boolean showSavedText = false;

    private double animation_time = 1.0f;
    private double animation_start_time = ImGui.getTime();

    private final ImBoolean closable = new ImBoolean(true);

    private ImVec2 cursorPos;

    private ConfirmSaveDialog saveDialog = new ConfirmSaveDialog();

    private NodeEditorRenderer nodeEditorRenderer;

    private String fileName = "untitled";
    public String id;

    private int totalInputs = 0;

    private ArrayList<TextField> tfInputPins = new ArrayList<>();

    public GroupNodeWindow(ImGuiWindow imGuiWindow, GlfwWindow window, String loadFile) {
        this.imGuiWindow = imGuiWindow;
        this.glfwWindow = window;
        groupSaver = new GroupSaver();

        if(loadFile != null){
            load(loadFile);
        }else{
            graph = new Graph();
            NodeGroupInput inputNode = new NodeGroupInput(graph);
            graph.addNode(inputNode);
        }


        nodeEditorRenderer = new NodeEditorRenderer(id, graph, NodeDisabled.class);
        nodeEditorRenderer.addChangeListener(new GraphChangeListener() {
            @Override
            public void changed() {
                promptSave = true;
            }
        });
    }

    public void load(String loadFile){
        graph = groupSaver.load(loadFile);
        fileName = loadFile;
        id = fileName;
        isLoading = true;
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
        ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY(), ImGuiCond.Once);
//
        if(ImGui.begin(id, closable)){
            focusAnimation(menuBarHeight, taskbarHeight);
//
            if(!closable.get())
            {
                //TODO request user to save before closing
                if(promptSave){
                    int state = saveDialog.show();
                    if(state == 0){
                        promptSave = false;
                        imGuiWindow.removeGroupNodeWindow(this);
                    }else if(state == 1){
//                        graphSaver.save(fileName, settings, graph);
                        nodeEditorRenderer.setAsCurrentEditor();
                        boolean success = groupSaver.save(fileName, graph);
                        imGuiWindow.removeGroupNodeWindow(this);
                    }
                }else{
                    imGuiWindow.removeGroupNodeWindow(this);
                }
            }

            if(ImGui.button("Save")){

                for(Node node : graph.getNodes().getList()){
                    node.onSaved();
                }

                nodeEditorRenderer.setAsCurrentEditor();
                boolean success = groupSaver.save(fileName, graph);

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

            if (ImGui.beginChild("GroupSettingsChild", 400, 0)) {
                ImGui.beginGroup();
                {
                    if (ImGui.collapsingHeader("Properties", ImGuiTreeNodeFlags.DefaultOpen)) {
                        int outputSize = 0;
                        for(Node node : graph.getNodes().getList()){
                            if(node instanceof NodeGroupInput){
                                outputSize += node.outputPins.size();
                            }
                        }

                        if(outputSize != tfInputPins.size()){
                            tfInputPins.clear();

                            for(Node node : graph.getNodes().getList()){
                                if(node instanceof NodeGroupInput){
                                    for (int i = 0; i < node.outputPins.size(); i++) {
                                        Pin pin = node.outputPins.get(i);

                                        TextField tf = new TextField(true);
                                        tf.addChangedListener((oldValue, newValue) -> {
                                            pin.setName(newValue);
                                        });
                                        tf.setText(pin.getName());
                                        tfInputPins.add(tf);
                                    }
                                }
                            }
                        }

                        for (int i = 0; i < tfInputPins.size(); i++) {
                            tfInputPins.get(i).show();

                            if(i != 0){
                                ImGui.sameLine();
                                if(ImGui.button("^##ip" + i)){
//                                    boolean swapped = graph.nodes.swap(ruleNodes.get(i), ruleNodes.get(i - 1));
                                }
                            }

                            if(i != tfInputPins.size() - 1){
                                ImGui.sameLine();
                                if(ImGui.button("v##ip" + i)){
//                                    boolean swapped = graph.nodes.swap(ruleNodes.get(i), ruleNodes.get(i + 1));
                                }
                            }
                        }
                    }
                }
                ImGui.endGroup();
                ImGui.endChild();
            }

            ImGui.sameLine();

            nodeEditorRenderer.show(cursorPos, isLoading);
        }
        ImGui.end();

        if(isLoading){
            isLoading = false;
        }
    }

    private void focusAnimation(float menuBarHeight, float taskbarHeight){
        if(ImGui.isWindowFocused()){
            if(!isFocused) {
                ImGui.setWindowPos(id, ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY() + taskbarHeight);
                animation_time = 1.0f;
                animation_start_time = ImGui.getTime();
                isFocused = true;
                animationCompleted = false;
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
            ImGui.setWindowPos(id, pos.x, pos.y);
        }else{
            if(!animationCompleted){
                ImGui.setWindowPos(fileName, target_pos.x, target_pos.y);
                animationCompleted = true;
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFocus(boolean focus){
        this.isFocused = focus;
    }
}
