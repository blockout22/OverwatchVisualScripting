import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class GlfwWindow {

    private long windowID;

    public GlfwWindow(int width, int height, String title) {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!GLFW.glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        windowID = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(windowID == MemoryUtil.NULL){
            throw new RuntimeException("Failed to create GLFW window");
        }


        GLFW.glfwMakeContextCurrent(windowID);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(windowID);
        GL.createCapabilities();
    }

    public boolean isCloseRequested(){
        return GLFW.glfwWindowShouldClose(windowID);
    }

    public void update(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        GLFW.glfwSwapBuffers(windowID);
        GLFW.glfwPollEvents();
    }

    public void close()
    {
        GLFW.glfwDestroyWindow(windowID);
        GLFW.glfwTerminate();
    }
}
