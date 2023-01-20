package ovs;

import org.lwjgl.opengl.GL11;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class OwVS {

    public GlfwWindow window;
    public ImGuiWindow imGuiWindow;

    public OwVS(){
        File scripts = new File(Global.SCRIPTS_DIR);
        if(!scripts.exists()){
            scripts.mkdir();
        }
        window = new GlfwWindow(1920, 1080, "Overwatch Visual Scripting | [Build: " + Global.BUILD + "]");
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
        System.out.println("Starting Overwatch Visual Scripting...");
        for(String arg : args){
            if(arg.equals("dev")){
                Global.devMode = true;
                File file = new File("src/main/resources/build");
                System.out.println(file.getAbsolutePath());
                try {

                    BufferedReader br = new BufferedReader(new FileReader(file));

                    int build = 1;
                    String line;
                    while((line = br.readLine()) != null){
                        try{
                            build = Integer.valueOf(line);
                            break;
                        }catch (NumberFormatException e){

                        }
                    }

                    br.close();

                    if(build == 0){
                        System.out.println(")");
                        return;
                    }

                    build++;

                    PrintWriter pw = new PrintWriter(file);
                    pw.write("" + build);
                    pw.flush();
                    pw.close();

                    Global.BUILD = build;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if(!Global.devMode){
            try {
                InputStream stream = OwVS.class.getClassLoader().getResourceAsStream("build");

                BufferedReader br = new BufferedReader(new InputStreamReader(stream));

                int build = 1;
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        build = Integer.valueOf(line);
                        break;
                    } catch (NumberFormatException e) {

                    }
                }

                br.close();

                Global.BUILD = build;
            }catch (Exception e){}
        }

        new OwVS();
    }
}
