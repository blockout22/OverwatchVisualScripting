package ovs.chat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.type.ImString;
import ovs.Notification;
import ovs.chat.packet.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Chat {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader input;
    private boolean isConnected = false;
    private boolean isConnecting = false;

    private int count = 0;

    private Thread listenThread;

    private String history = "";
    public ImString chatHistory = new ImString();
    public String userName = "";

    public ArrayList<ScriptInfo> scripts = new ArrayList<>();


    public void connect(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    isConnecting = true;
                    String ip = ipScramble("90.5.198.224", 4, -3, 5, 3);
                    socket = new Socket(ip, 4000);
                    socket.setSoTimeout(2000);
                    if(socket.isConnected()){
                        isConnecting = false;
                        isConnected = true;
                        System.out.println("Connected To Chat");
                    }else{
                        System.out.println("No Connection");
                        isConnecting = false;
                        return;
                    }
                    out = new PrintWriter(socket.getOutputStream());

//                    Packet packet = new PacketLogin(userName);
                    JSonPacket packet = new JSonPacket();
                    packet.type = "LOGIN";
                    packet.data = userName;
                    sendJSon(packet);

                    listen();
                } catch (IOException e) {
                    e.printStackTrace();
                    isConnecting = false;
                }
            }
        }).start();

    }

    private String ipScramble(String ip, int iOff, int jOff, int kOff, int lOff){
        String[] i = ip.split("\\.");
        if(i.length != 4){
            return "NULL";
        }
        int first = (Integer.valueOf(i[0]) + iOff);
        int second = (Integer.valueOf(i[1]) + jOff);
        int third  = (Integer.valueOf(i[2]) + kOff);
        int forth = (Integer.valueOf(i[3]) + lOff);
        return (first + "." + second + "." + third + "." + forth);
    }

    private void listen(){
        listenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                    isConnected = false;
                    return;
                }
                while(isConnected){
                    try {
                        String outputString = input.readLine();
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        JSonPacket packet = gson.fromJson(outputString, JSonPacket.class);
//                        Packet packet = Packet.fromString(outputString);

                        if(packet != null) {
                            if(packet.type.equals("MESSAGE")){
                                appendHistory(packet.data);
                            }else if (packet.type.equals("USERCOUNT")){
                                count = Integer.valueOf(packet.data.trim());
                            }else if(packet.type.equals("FILE")){
                                FilePacket filePacket = gson.fromJson(outputString, FilePacket.class);
                                scripts.add(new ScriptInfo(filePacket.fileName,  filePacket.data));
                            }
                        }else {
                            System.err.println("Null Packet: " + outputString);
                        }
                    } catch (Exception e) {
//                        System.out.println(isConnected);
//                        e.printStackTrace();
                    }
                }
            }
        });
        listenThread.start();
    }

    public void sendJSon(JSonPacket packet){
        Gson gson = new GsonBuilder().create();
        String output = gson.toJson(packet);
        out.println(output);
        out.flush();
    }

    public int getUserCount(){
        return count;
    }

    public void disconnect(){
        if(isConnected){
            try {
                history = "";
                isConnected = false;
                input.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnecting()
    {
        return isConnecting;
    }

    public boolean isConnected(){
        return isConnected;
    }

//    public void sendPacket(JSonPacket packet){
//        if(out != null) {
//            out.println(packet);
//            out.flush();
//        }
//    }

    private void appendHistory(String text){
        history += text + "\n";
        if(chatHistory.getData().length < history.length()) {
            chatHistory.resize(history.length());
        }
        chatHistory.set(history);
        Notification.add(text);
    }

    public String getChatHistory(){
        return history;
    }
}
