package ovs;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static org.lwjgl.system.MemoryUtil.memSlice;

public class ImageParser {

    private ByteBuffer image;
    private int width, height;

    ImageParser(int width, int height, ByteBuffer image) {
        this.image = image;
        this.height = height;
        this.width = width;
    }
    public static ImageParser load_image(String path) throws IOException {
        ByteBuffer image;
        ByteBuffer imageBuffer = readFromClassPath(path);
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            image = stbi_load_from_memory(imageBuffer, w, h, comp, 4);
            if (image == null) {
                // throw new resource_error("Could not load image resources.");
            }
            width = w.get();
            height = h.get();
        }
        return new ImageParser(width, height, image);
    }

    private static ByteBuffer readFromClassPath(String resource) throws IOException {
        ByteBuffer buffer;

        Path path = Paths.get(resource);
        if(Files.isReadable(path)){
            try(SeekableByteChannel fc = Files.newByteChannel(path)){
                buffer = BufferUtils.createByteBuffer((int)fc.size() + 1);
                while(fc.read(buffer) != -1){
                    ;
                }
            }
        }else{
            try(
                InputStream source = ImageParser.class.getClassLoader().getResourceAsStream(resource);
                ReadableByteChannel rbc = Channels.newChannel(source);
            ){
                buffer = ByteBuffer.allocateDirect( 8 * 1024).order(ByteOrder.nativeOrder());

                while(true){
                    int bytes = rbc.read(buffer);
                    if(bytes == -1){
                        break;
                    }

                    if(buffer.remaining() == 0){
                        ByteBuffer newBuffer = BufferUtils.createByteBuffer(buffer.capacity() * 3 / 2);
                        newBuffer.flip();
                        newBuffer.put(buffer);
                        buffer = newBuffer;
                    }
                }
            }
        }
        buffer.flip();
        return memSlice(buffer);
    }

    public ByteBuffer getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
