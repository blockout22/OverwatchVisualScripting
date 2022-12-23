package ovs;

import static org.lwjgl.opengl.GL11.glDeleteTextures;

public class Texture {

    private int id;
    private int width;
    private int height;

    public Texture(int id, int width, int height){
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void cleanup()
    {
        glDeleteTextures(id);
    }
}
