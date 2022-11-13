public class OwVS {

    public GlfwWindow window;

    public OwVS(){
        window = new GlfwWindow(800, 600, "Overwatch Visual Scripting");

        while(!window.isCloseRequested())
        {
            window.update();
        }

        window.close();
    }

    public static void main(String[] args) {
        new OwVS();
    }
}
