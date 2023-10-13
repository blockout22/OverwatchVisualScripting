package ovs;

import imgui.*;
import imgui.extension.imguifiledialog.ImGuiFileDialog;
import imgui.extension.imnodes.ImNodes;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiInputTextFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImString;
import org.lwjgl.glfw.GLFW;
import ovs.chat.Chat;
import ovs.chat.ScriptInfo;
import ovs.chat.packet.FilePacket;
import ovs.chat.packet.JSonPacket;
import ovs.graph.GraphWindow;
import ovs.graph.GroupNodeWindow;

import java.io.*;
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

    private ArrayList<GroupNodeWindow> groupNodeWindows = new ArrayList<>();
    private ArrayList<GroupNodeWindow> queueRemoveGroupNodeWindows = new ArrayList<>();

    private ImString inputFileName = new ImString();

    private String lastMenuAction = null;

    private ImVec2 textSize = new ImVec2();

    private ArrayList<String> alreadyExistingScripts = new ArrayList<>();
    private ArrayList<String> alreadyExistingNodeGroups = new ArrayList<>();
//    private ImFont font;

    private Chat chat = new Chat();
    private ImString inputChat = new ImString();
    private ImString userNameText = new ImString(30);

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

//        io.getFonts().addFontDefault();
//        io.getFonts().addFontFromFileTTF("src/main/resources/OpenSans-Regular.ttf", 18);
        InputStream fontStream = getClass().getClassLoader().getResourceAsStream("OpenSans-Regular.ttf");
        if(fontStream != null){
            try{
                byte[] fontBytes = fontStream.readAllBytes();
                ImFontAtlas atlas = io.getFonts();
                ImFontConfig config = new ImFontConfig();
                atlas.addFontFromMemoryTTF(fontBytes, 18, config);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try{
                    fontStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        final ImFontGlyphRangesBuilder rangesBuilder = new ImFontGlyphRangesBuilder();
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesDefault());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesCyrillic());
        rangesBuilder.addRanges(io.getFonts().getGlyphRangesJapanese());

//        final ImFontConfig fontConfig = new ImFontConfig();
//        fontConfig.setMergeMode(true);

//        final short[] glyphRanges = rangesBuilder.buildRanges();
//        font = io.getFonts().addFontFromMemoryTTF(loadFromResources("OpenSans-Regular.ttf"), 72, fontConfig, glyphRanges);
//
//        //this no work???
//        io.getFonts().build();
//        io.setFontDefault(font);

//        fontConfig.destroy();

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
//        ImGui.pushFont(font);
        {
            createMainMenuBar();
            float menuBarHeight = 25f;
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

//            ImGui.setNextWindowPos(-200, -200, ImGuiCond.Always);
//            chatWindow();

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

            if(lastMenuAction == "CreateNodeGroup"){
                inputFileName.set("");
                File file = new File(Global.NODE_GROUP_DIR);
                alreadyExistingNodeGroups.clear();
                for(File group : file.listFiles()){
                    alreadyExistingNodeGroups.add(group.getName());
                }
                ImGui.openPopup("create_node_group");
                lastMenuAction = null;
            }

            if(lastMenuAction == "SendScriptFile"){
                ImGui.openPopup("SendScriptFile");
                System.out.println("Open Popup");
                lastMenuAction = null;
            }

            if(lastMenuAction == "OpenNodeGroup") {
                ImGui.openPopup("open_node_group");
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

            if(ImGui.beginPopupModal("create_node_group", NoTitleBar | NoResize | AlwaysAutoResize | NoMove | NoSavedSettings))
            {
                float width = ImGui.getWindowSize().x;
                ImGui.calcTextSize(textSize, "Node Group Name");
                ImGui.setCursorPosX((width - textSize.x) * 0.5f);
                ImGui.text("Node Group Name");
                if(ImGui.inputText("##", inputFileName)){
                }

                boolean alreadyExists = false;

                //TODO check if Node Group Name Already Exists
                for(String group : alreadyExistingNodeGroups){
                    if(inputFileName.get().equals(group)){
                        alreadyExists = true;
                        break;
                    }
                }

                if(inputFileName.get().length() > 0 && !alreadyExists){
                    if(ImGui.button("Create")){
                        GroupNodeWindow groupNodeWindow = new GroupNodeWindow(this, glfwWindow, null);
                        groupNodeWindows.add(groupNodeWindow);
                        groupNodeWindow.setFileName(inputFileName.get());
//                        GraphWindow window = new GraphWindow(this, glfwWindow, null);
//                        graphWindows.add(window);
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

            if(ImGui.beginPopupModal("open_node_group", NoTitleBar | NoResize | AlwaysAutoResize | NoMove | NoSavedSettings))
            {
                float width = ImGui.getWindowSize().x;
                ImGui.calcTextSize(textSize, "Open Group");
                ImGui.setCursorPosX((width - textSize.x) * 0.5f);
                ImGui.text("Open Group");
                ImGui.separator();
                ImGui.dummy(5, 5);

                File groups = new File(Global.NODE_GROUP_DIR);
                if(groups.exists()){
                    for(File file : groups.listFiles()){
                        boolean alreadyOpen = false;

                        for(GroupNodeWindow window : groupNodeWindows){
                            if(window.getFileName().equals(file.getName())){
                                alreadyOpen = true;
                                break;
                            }
                        }

                        if(alreadyOpen){
                            ImGui.alignTextToFramePadding();
                            ImGui.text(file.getName());
                        }else{
                            if(ImGui.button(file.getName())){
                                GroupNodeWindow window = new GroupNodeWindow(this, glfwWindow, file.getName());
                                groupNodeWindows.add(window);
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

            if(ImGui.beginPopupModal("SendScriptFile", NoTitleBar | NoResize | AlwaysAutoResize | NoMove | NoSavedSettings))
            {
                File scriptDir = new File(Global.SCRIPTS_DIR);

               for (File script : scriptDir.listFiles()) {
                   if (ImGui.button(script.getName())) {
                       String scriptFileName = script.getName();
                       File file = new File(Global.SCRIPTS_DIR + File.separator + scriptFileName + File.separator + "script.json");

                       try {
                           BufferedReader br = new BufferedReader(new FileReader(file));
                           String line;
                           String content = "";
                           while ((line = br.readLine()) != null) {
                               content += line + "\n";
                           }
//                            Packet packet = new PacketFile(content);
                           FilePacket jsonPacket = new FilePacket();
                           jsonPacket.type = "FILE";
                           jsonPacket.data = content;
                           jsonPacket.fileName = scriptFileName;
                           chat.sendJSon(jsonPacket);
                       } catch (Exception e) {
                           e.printStackTrace();
                       }
                       ImGui.closeCurrentPopup();
                   }
               }

               ImGui.separator();
               if(ImGui.button("Close")){
                   ImGui.closeCurrentPopup();
               }

                ImGui.endPopup();
            }

            for(GraphWindow graphWindow : graphWindows){
                graphWindow.show(menuBarHeight, taskbarHeight);
            }

            for(GroupNodeWindow groupNodeWindow : groupNodeWindows){
                groupNodeWindow.show(menuBarHeight, taskbarHeight);
            }

            ImGui.setNextWindowPos(ImGui.getMainViewport().getPosX(), ImGui.getMainViewport().getPosY() + glfwWindow.getHeight() - taskbarHeight, ImGuiCond.Always);
            ImGui.setNextWindowSize(glfwWindow.getWidth(), taskbarHeight, ImGuiCond.Always);
            ImGui.pushStyleColor(ImGuiCol.WindowBg, 25, 25, 25, 255);
            ImGui.begin("Taskbar",  NoDocking | NoScrollbar | NoTitleBar | NoResize);
            {
                for (GraphWindow graphWindow : graphWindows){
                    if(ImGui.button(graphWindow.getFileName(), 0, taskbarHeight - 15))
                    {
                        for (GraphWindow gw : graphWindows){
                            gw.setFocus(false);
                        }
                        ImGui.setWindowFocus(graphWindow.getFileName());
                    }
                    ImGui.sameLine();
                }

                for(GroupNodeWindow groupNodeWindow : groupNodeWindows){
                    if(ImGui.button(groupNodeWindow.getFileName(), 0, taskbarHeight- 15)){
                        for(GroupNodeWindow gnw : groupNodeWindows){
                            gnw.setFocus(false);
                        }
                        ImGui.setWindowFocus(groupNodeWindow.getFileName());
                    }
                    ImGui.sameLine();
                }
            }
            ImGui.end();
            ImGui.popStyleColor();

            for (int i = 0; i < queueRemoveGraphWindow.size(); i++) {
                graphWindows.remove(queueRemoveGraphWindow.get(i));
            }

            queueRemoveGraphWindow.clear();

            for (int i = 0; i < queueRemoveGroupNodeWindows.size(); i++) {
                groupNodeWindows.remove(queueRemoveGroupNodeWindows.get(i));
            }

            queueRemoveGroupNodeWindows.clear();

            Notification.show();
        }

//        ImGui.popFont();
        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if(ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)){
            final long backupWindowPtr = GLFW.glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            GLFW.glfwMakeContextCurrent(backupWindowPtr);
        }
    }

    private void chatWindow(){
        ImGui.setNextWindowSize(1000, 800, ImGuiCond.Once);
        if(ImGui.begin("ChatWindow", NoBringToFrontOnFocus | NoDocking | NoNavFocus | NoFocusOnAppearing)){
            if(!chat.isConnected()){
                ImGui.text("UserName");
                if(ImGui.inputText("##UserName", userNameText)){
                    chat.userName = userNameText.get();
                }

                if(chat.isConnecting() || chat.userName.length() <= 0)
                {
                    ImGui.beginDisabled();
                }
                if(ImGui.button((chat.isConnecting() ? "Attempting to connect..." : "Connect To Chat"))){
                    chat.connect();
                }

                if(chat.isConnecting() || chat.userName.length() <= 0)
                {
                    ImGui.endDisabled();
                }
            }else{

                if(ImGui.button("Disconnect")){
                    chat.disconnect();
                }

                ImGui.text("Connected Users: " + chat.getUserCount());

                ImGui.beginDisabled();
                ImGui.inputTextMultiline("##ChatHistory", chat.chatHistory);
                ImGui.endDisabled();

                if(ImGui.inputText("##ChatInput", inputChat, ImGuiInputTextFlags.EnterReturnsTrue)){
//                        chat.sendMessage(inputChat.get());
//                        chat.sendPacket(new PacketMessage(inputChat.get()));
                    JSonPacket jsonPacket = new JSonPacket();
                    jsonPacket.type = "MESSAGE";
                    jsonPacket.data = inputChat.get();
                    chat.sendJSon(jsonPacket);
                    inputChat.set("");
                };

                if(ImGui.button("Send Script")){
                    lastMenuAction = "SendScriptFile";
                }

                ImGui.separator();
                ImGui.text("Scripts");

                if(ImGui.beginChild("Chat-Scripts", 500, 500)){
                    for (int i = 0; i < chat.scripts.size(); i++) {
                        ScriptInfo filePacket = chat.scripts.get(i);

                        if(ImGui.button(filePacket.fileName)){
                            GraphWindow window = new GraphWindow(this, glfwWindow, null);
                            window.setFileName(filePacket.fileName);
                            window.loadFromString(filePacket.data);
                            graphWindows.add(window);
                        }
                    }
                }
                ImGui.endChild();
            }
        }
        ImGui.end();
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

                if(ImGui.menuItem("Create Node Group")){
                    lastMenuAction = "CreateNodeGroup";
                }

                if(ImGui.menuItem("Open Node Group")){
                    lastMenuAction = "OpenNodeGroup";
                }

                ImGui.endMenu();
            }
        }
        ImGui.endMainMenuBar();
    }

    public void removeGraphWindow(GraphWindow graphWindow){
        queueRemoveGraphWindow.add(graphWindow);
    }

    public void removeGroupNodeWindow(GroupNodeWindow groupNodeWindow){
        queueRemoveGroupNodeWindows.add(groupNodeWindow);
    }

    public void close(){
//        texture.cleanup();
        chat.disconnect();
        ImNodes.destroyContext();
        ImGui.destroyContext();
    }
}
