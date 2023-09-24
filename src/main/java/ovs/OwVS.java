package ovs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lwjgl.opengl.GL11;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class OwVS {

    public GlfwWindow window;
    public ImGuiWindow imGuiWindow;

    public OwVS(){
        try {
            checkLatestReleases("blockout22", "OverwatchVisualScripting");
        } catch (Exception e) {
            e.printStackTrace();
        }
        File scripts = new File(Global.SCRIPTS_DIR);
        if(!scripts.exists()){
            scripts.mkdir();
        }

        File groups = new File(Global.NODE_GROUP_DIR);
        if(!groups.exists()){
            groups.mkdir();
        }

        window = new GlfwWindow(1920, 1080, "Overwatch Visual Scripting | [Build: " + Global.BUILD + "]" + (Global.LATEST_BUILD > Global.BUILD ? " - Update Available" : ""));
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

    private void checkLatestReleases(String user, String repo) throws Exception {
        String url = "https://api.github.com/repos/" + user + "/" + repo + "/releases/latest";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        String token = "";
//        con.setRequestProperty("Authorization", "Bearer " + token);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String line;
        StringBuilder response = new StringBuilder();
        while((line = in.readLine()) != null){
            response.append(line);
        }
        in.close();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Repo toJson = gson.fromJson(response.toString(), Repo.class);

        int build = extractNumbers(toJson.tag_name);
//        System.out.println(toJson.assets.get(0).download_count);
        Global.LATEST_BUILD = build;
        System.out.println("Build: " + build);
    }

    private int extractNumbers(String s) {
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                sb.append(c);
            }
        }

        return Integer.valueOf(sb.toString());
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

        File file = new File("src\\main\\resources\\heroes.txt");
        System.out.println(file.getAbsoluteFile());
//        Global.writeArrayToFile(Global.heroes, file);
        new OwVS();
    }
}
