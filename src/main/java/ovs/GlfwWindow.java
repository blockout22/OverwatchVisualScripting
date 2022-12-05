package ovs;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class GlfwWindow {

    private long windowID;
    private static int curWidth;
    private static int curHeight;

    public GlfwWindow(int width, int height, String title) {
        GLFWErrorCallback.createPrint(System.err).set();

        if(!GLFW.glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        windowID = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if(windowID == MemoryUtil.NULL){
            throw new RuntimeException("Failed to create GLFW window");
        }

        GLFW.glfwSetWindowSizeCallback(windowID, (window, w, h) -> {
            curWidth = w;
            curHeight = h;
        });

        GLFW.glfwMakeContextCurrent(windowID);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(windowID);
        GL.createCapabilities();

        curWidth = width;
        curHeight = height;
    }

    public boolean isCloseRequested(){
        return GLFW.glfwWindowShouldClose(windowID);
    }

    public void update(){
        GLFW.glfwSwapBuffers(windowID);
        GLFW.glfwPollEvents();
    }

    /**
     * Doesn't work
     * @param image
     */
    private void setIcon(String image){
        try {
            ByteBuffer byteBuffer = loadImage(image);

            if(byteBuffer != null) {

                GLFWImage.Buffer images = GLFWImage.create(1);
                GLFWImage icon = GLFWImage.create().set(128, 128, byteBuffer);
                images.put(icon);
                GLFW.glfwSetWindowIcon(windowID, images);
                return;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ByteBuffer loadImage(String image) throws IOException {
        InputStream stream = getClass().getResourceAsStream("/" + image);
        if(stream == null){
            return null;
        }
        byte[] bytesArray = stream.readAllBytes();
        ByteBuffer buffer = ByteBuffer.wrap(bytesArray);
        buffer.flip();

        return buffer;
    }

    public static int getWidth(){
        return curWidth;
    }

    public static int getHeight(){
        return curHeight;
    }

    public void close()
    {
        GLFW.glfwDestroyWindow(windowID);
        GLFW.glfwTerminate();
    }

    public long getWindowID(){
        return windowID;
    }
}
