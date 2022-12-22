package ovs;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
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

    public void setIcon(){
        try {
            File file = new File("E:\\Github\\OverwatchVisualScripting\\src\\main\\resources\\owvs_icon128.png");
//            File file = new File("src\\main\\resources\\owIcon.ico");
            System.out.println(file.getAbsolutePath());
            BufferedImage image = ImageIO.read(file);

            int width = image.getWidth();
            int height = image.getHeight();
            int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

            ByteBuffer imageBuffer = ByteBuffer.allocate(width * height * 4);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = pixels[y * width + x];
                    imageBuffer.put((byte) ((pixel >> 16) & 0xff)); // red
                    imageBuffer.put((byte) ((pixel >> 8) & 0xff)); // green
                    imageBuffer.put((byte) (pixel & 0xff)); // blue
                    imageBuffer.put((byte) ((pixel >> 24) & 0xff)); // alpha
                }
            }
            imageBuffer.flip();


            GLFWImage glfwImage = GLFWImage.malloc();
            glfwImage.set(width, height, imageBuffer);

            GLFWImage.Buffer images = GLFWImage.malloc(1);
            images.put(0, glfwImage);
            images.flip();

            GLFW.glfwSetWindowIcon(windowID, images);

            glfwImage.free();
            images.free();
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
