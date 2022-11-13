import org.lwjgl.opengl.GL11;

public class OwVS {

    public GlfwWindow window;
    public ImGuiWindow imGuiWindow;

    public OwVS(){
        window = new GlfwWindow(800, 600, "Overwatch Visual Scripting");
        imGuiWindow = new ImGuiWindow(window.getWindowID());

        while(!window.isCloseRequested())
        {
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
