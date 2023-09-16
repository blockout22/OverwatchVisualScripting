package ovs.graph;

import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.type.ImBoolean;
import ovs.GlfwWindow;
import ovs.Global;
import ovs.ImGuiWindow;
import ovs.TaskSchedule;
import ovs.graph.node.Node;
import ovs.graph.node.interfaces.NodeDisabled;
import ovs.graph.node.interfaces.NodeGroupOnly;
import ovs.graph.save.GroupSaver;

public class GroupNodeWindow {

    private ImGuiWindow imGuiWindow;
    private GlfwWindow glfwWindow;

    private GroupSaver groupSaver;
    protected Graph graph;

    private boolean promptSave = false;
    private boolean isFocused = false;
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

    public GroupNodeWindow(ImGuiWindow imGuiWindow, GlfwWindow window, String loadFile) {
        this.imGuiWindow = imGuiWindow;
        this.glfwWindow = window;
        groupSaver = new GroupSaver();

        if(loadFile != null){
            load(loadFile);
        }else{
            graph = new Graph();
        }


        nodeEditorRenderer = new NodeEditorRenderer(id, graph, NodeDisabled.class);
        nodeEditorRenderer.addChangeListener(new GraphChangeListener() {
            @Override
            public void changed() {
                promptSave = true;
            }
        });

        //TODO check if loading from file else create blank graph;
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
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFocus(boolean focus){
        this.isFocused = focus;
    }
}
