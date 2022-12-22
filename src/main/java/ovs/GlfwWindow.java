package ovs;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.stb.STBImage.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

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
        setIcon();
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

    public void setIcon() {
//            File file = new File("src\\main\\resources\\owIcon.ico");
        GLFWImage image = GLFWImage.malloc(); GLFWImage.Buffer imagebf = GLFWImage.malloc(1);

        ImageParser resource_01 = null;
        try {
            resource_01 = ImageParser.load_image("owvs_icon128.png");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        image.set(resource_01.getWidth(), resource_01.getHeight(), resource_01.getImage());
        imagebf.put(0, image);
        GLFW.glfwSetWindowIcon(windowID, imagebf);
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

    public int getWidth(){
        return curWidth;
    }

    public int getHeight(){
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
