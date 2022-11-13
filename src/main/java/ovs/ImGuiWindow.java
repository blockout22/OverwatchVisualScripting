package ovs;

import imgui.ImFontConfig;
import imgui.ImFontGlyphRangesBuilder;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.extension.imnodes.ImNodes;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.glfw.GLFW;
import ovs.graph.GraphWindow;

import java.io.File;
import java.io.IOException;
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

    public ImGuiWindow(GlfwWindow glfwWindow){
        this.glfwWindow = glfwWindow;
        ImNodes.createContext();
        ImGui.createContext();

        ImGuiIO io = ImGui.getIO();
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
        io.getFonts().addFontFromMemoryTTF(loadFromResources("OpenSans-Regular.ttf"), 14, fontConfig, glyphRanges);

        fontConfig.destroy();

        imGuiGLFW.init(glfwWindow.getWindowID(), true);
        imGuiGl3.init("#version 150");
    }

    private byte[] loadFromResources(String name){
        try{
            return Files.readAllBytes(Paths.get(new File(name).toURI()));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void update(){
        imGuiGLFW.newFrame();
        ImGui.newFrame();
        {

            createMainMenuBar();
            float menuBarHeight = 20f;
            ImGui.setNextWindowSize(glfwWindow.getWidth(), glfwWindow.getHeight(), ImGuiCond.Always);
            ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY(), ImGuiCond.Always);
            ImGui.setNextWindowViewport(ImGui.getMainViewport().getID());


            if(ImGui.begin("New Window", NoBringToFrontOnFocus | NoBackground | NoTitleBar | NoDocking | NoScrollbar)){
                ImGui.setCursorScreenPos(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY() + menuBarHeight);
                //fill screen widget here to enable snapping on viewport it's self
                ImGui.dockSpace(1, glfwWindow.getWidth(), glfwWindow.getHeight() - menuBarHeight, NoResize | NoScrollbar);
            }
            ImGui.end();

            for(GraphWindow graphWindow : graphWindows){
                graphWindow.show(menuBarHeight);
            }

            for (int i = 0; i < queueRemoveGraphWindow.size(); i++) {
                graphWindows.remove(queueRemoveGraphWindow.get(i));
            }
        }

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)){
            final long backupWindowPtr = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }
    }

    public void createMainMenuBar(){
        ImGui.beginMainMenuBar();
        {
            if(ImGui.beginMenu("File", true)){
                if (ImGui.menuItem("New Graph")) {
                    //lastMenuAction == "File";
                    graphWindows.add(new GraphWindow(glfwWindow));
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
        ImNodes.destroyContext();
        ImGui.destroyContext();
    }
}
