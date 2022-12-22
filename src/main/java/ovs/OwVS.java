package ovs;

import org.lwjgl.opengl.GL11;

import java.io.File;

public class OwVS {

    public GlfwWindow window;
    public ImGuiWindow imGuiWindow;

    public OwVS(){
        File scripts = new File(Global.SCRIPTS_DIR);
        if(!scripts.exists()){
            scripts.mkdir();
        }
        window = new GlfwWindow(1920, 1080, "Overwatch Visual Scripting");
        imGuiWindow = new ImGuiWindow(window);

        while(!window.isCloseRequested())
        {
            TaskSchedule.update();
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            imGuiWindow.update();
            window.update();
        }

        imGuiWindow.close();
        window.close();
    }

    public static void main(String[] args) {
        new OwVS();
    }
}
