package ovs;

import imgui.*;
import imgui.extension.imnodes.ImNodes;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImInt;
import imgui.type.ImString;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL13;
import ovs.graph.GraphWindow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static imgui.flag.ImGuiWindowFlags.*;

public class ImGuiWindow {

    private GlfwWindow glfwWindow;

    private final ImGuiImplGlfw imGuiGLFW = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private ArrayList<GraphWindow> graphWindows = new ArrayList<>();
    private ArrayList<GraphWindow> queueRemoveGraphWindow = new ArrayList<>();

    private ImString inputFileName = new ImString();

    private String lastMenuAction = null;

    private ImVec2 textSize = new ImVec2();

    private ArrayList<String> alreadyExistingScripts = new ArrayList<>();
    private ImFont font;

//    Texture texture;

    public ImGuiWindow(GlfwWindow glfwWindow){
        this.glfwWindow = glfwWindow;
        ImNodes.createContext();
        ImGui.createContext();

        ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null);
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable);
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);
        io.setConfigViewportsNoTaskBarIcon(false);

        io.getFonts().addFontDefault();
        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder();
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesDefault());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesCyrillic());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesJapanese());

        final ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setMergeMode(true);

        final short[] glyphRanges = rangesBuilder.buildRanges();
        font = io.getFonts().addFontFromMemoryTTF(loadFromResources("OpenSans-Regular.ttf"), 72, fontConfig, glyphRanges);

        //this no work???
        io.getFonts().build();
        io.setFontDefault(font);

        fontConfig.destroy();

        imGuiGLFW.init(glfwWindow.getWindowID(), true);
        imGuiGl3.init("#version 150");

//        try {
//            texture = TextureLoader.loadTexture("blizzard_world.jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private byte[] loadFromResources(String name){
        try{
            return Files.readAllBytes(Paths.get(new File(name).toURI()));
        }catch (IOException e){
            try {
                InputStream stream = getClass().getResourceAsStream("/" + name);
                return stream.readAllBytes();
            }catch (IOException e1) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update(){
        imGuiGLFW.newFrame();
        ImGui.newFrame();
        ImGui.pushFont(font);
        {

            createMainMenuBar();
            float menuBarHeight = 20f;
            float taskbarHeight = 50f;
            ImGui.setNextWindowSize(glfwWindow.getWidth(), glfwWindow.getHeight(), ImGuiCond.Always);
            ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY(), ImGuiCond.Always);
            ImGui.setNextWindowViewport(ImGui.getMainViewport().getID());

//            ImGui.showDemoWindow();

//            ImGui.image(texture.getId(), texture.getWidth(), texture.getHeight());
//            float xPos = ImGui.getMousePosX() - ImGui.getWindowPosX();
//            float yPos = ImGui.getMousePosY() - ImGui.getWindowPosY();
//            System.out.println(Global.map(xPos, 0, texture.getWidth(), 0, 1));


            if(ImGui.begin("New Window", NoBringToFrontOnFocus | NoBackground | NoTitleBar | NoDocking | NoScrollbar)){
                ImGui.setCursorScreenPos(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY() + menuBarHeight);
                //fill screen widget here to enable snapping on viewport it's self
                ImGui.dockSpace(1, glfwWindow.getWidth(), glfwWindow.getHeight() - menuBarHeight - taskbarHeight, NoResize | NoScrollbar);
            }
            ImGui.end();

            if(lastMenuAction == "New"){

                inputFileName.set("");
                File file = new File(Global.SCRIPTS_DIR);
                alreadyExistingScripts.clear();
                for(File script : file.listFiles())
                {
                    alreadyExistingScripts.add(script.getName());
                }
                ImGui.openPopup("new_file_popup");
                lastMenuAction = null;
            }

            if(lastMenuAction == "Open"){
                ImGui.openPopup("open_file_popup");
                lastMenuAction = null;
            }

//            tipsWindow();

            if(ImGui.beginPopupModal("new_file_popup", NoTitleBar | NoResize | AlwaysAutoResize | NoMove | NoSavedSettings))
            {
                float width = ImGui.getWindowSize().x;
                ImGui.calcTextSize(textSize, "Script Name");
                ImGui.setCursorPosX((width - textSize.x) * 0.5f);
                ImGui.text("Script Name");
                if(ImGui.inputText("##", inputFileName)){
                }

                boolean alreadyExists = false;

                for(String script : alreadyExistingScripts){
                    if(inputFileName.get().equals(script)){
                        alreadyExists = true;
                        break;
                    }
                }

                if(inputFileName.get().length() > 0 && !alreadyExists){
                    if(ImGui.button("Create")){
                        GraphWindow window = new GraphWindow(this, glfwWindow, null);
                        window.setFileName(inputFileName.get());
                        graphWindows.add(window);
                        ImGui.closeCurrentPopup();
                    }
                }else{
                    ImGui.alignTextToFramePadding();
                    ImGui.text("Create");
                }

                ImGui.sameLine();
                if(ImGui.button("Close")){
                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }

            if(ImGui.beginPopupModal("open_file_popup", NoTitleBar | NoResize | AlwaysAutoResize | NoMove | NoSavedSettings)) {
                float width = ImGui.getWindowSize().x;
                ImGui.calcTextSize(textSize, "Open Script");
                ImGui.setCursorPosX((width - textSize.x) * 0.5f);
                ImGui.text("Open Script");
                ImGui.separator();
                ImGui.dummy(5, 5);

                File scripts = new File(Global.SCRIPTS_DIR);
                if (scripts.exists()) {
                    for (File file : scripts.listFiles()) {
                        boolean alreadyOpen = false;

                        for (GraphWindow window : graphWindows){
                            if(window.getFileName().equals(file.getName())){
                                alreadyOpen = true;
                                break;
                            }
                        }

                        if(alreadyOpen){
                            ImGui.alignTextToFramePadding();
                            ImGui.text(file.getName());
                        }else {
                            if (ImGui.button(file.getName())) {
                                GraphWindow window = new GraphWindow(this, glfwWindow, file.getName());
                                graphWindows.add(window);
                                ImGui.closeCurrentPopup();
                            }
                        }
                    }
                }

                ImGui.dummy(5, 5);
                ImGui.separator();

                if(ImGui.button("Close", -1, 0)){
                    ImGui.closeCurrentPopup();
                }

                ImGui.endPopup();
            }

            for(GraphWindow graphWindow : graphWindows){
                graphWindow.show(menuBarHeight, taskbarHeight);
            }

            ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY() + glfwWindow.getHeight() - taskbarHeight, ImGuiCond.Always);
            ImGui.setNextWindowSize(glfwWindow.getWidth(), taskbarHeight, ImGuiCond.Always);
            ImGui.pushStyleColor(ImGuiCol.WindowBg, 25, 25, 25, 255);
            ImGui.begin("Taskbar",  NoDocking | NoScrollbar | NoTitleBar | NoResize);
            {
                for (GraphWindow graphWindow : graphWindows){
                    if(ImGui.button(graphWindow.getFileName(), 0, taskbarHeight - 15))
                    {
                        ImGui.setWindowFocus(graphWindow.getFileName());
                    }
                    ImGui.sameLine();
                }
            }
            ImGui.end();
            ImGui.popStyleColor();

            for (int i = 0; i < queueRemoveGraphWindow.size(); i++) {
                graphWindows.remove(queueRemoveGraphWindow.get(i));
            }
        }

        ImGui.popFont();
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)){
            final long backupWindowPtr = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }
    }

    private void tipsWindow() {
        ImGui.setNextWindowSize(250, 250, ImGuiCond.Once);
        if(ImGui.begin("Tips",   NoBringToFrontOnFocus)){
            ImGui.textWrapped("You change the rule name by double clicking on the node title");
            ImGui.separator();
            ImGui.textWrapped("You can search for a node in the right click menu");
        }
        ImGui.end();
    }

    public void createMainMenuBar(){
        ImGui.beginMainMenuBar();
        {
            if(ImGui.beginMenu("File", true)){
                if (ImGui.menuItem("New")) {
                    lastMenuAction = "New";
//                    GraphWindow window = new GraphWindow(glfwWindow, null);
//                    window.setFileName("SomeNewScript");
//                    graphWindows.add(window);
                }

                if(ImGui.menuItem("Open")){
                    lastMenuAction = "Open";
//                    GraphWindow window = new GraphWindow(glfwWindow, "SomeNewScript");
//                    graphWindows.add(window);
                }
                ImGui.endMenu();
            }
        }
        ImGui.endMainMenuBar();
    }

    public void removeGraphWindow(GraphWindow graphWindow){
        queueRemoveGraphWindow.add(graphWindow);
    }

    public void close(){
//        texture.cleanup();
        ImNodes.destroyContext();
        ImGui.destroyContext();
    }
}
