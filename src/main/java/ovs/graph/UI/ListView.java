package ovs.graph.UI;

import imgui.ImGui;
import imgui.type.ImInt;
import ovs.graph.UI.Listeners.ChangeListener;

import java.util.ArrayList;

public class ListView extends UiComponent{

    protected ArrayList<ChangeListener> changeListeners = new ArrayList<>();

    private String title = "";
    private ImInt currentSelection = new ImInt();
    private String[] list = new String[0];

    private int last = 0;

    @Override
    public void show() {
        ImGui.beginListBox("##" + uniqueID, 300, 200);
        {
            ImGui.listBox("##", currentSelection, list);
//            System.out.println(currentSelection);
            if(last != currentSelection.get()){
                for (int j = 0; j < changeListeners.size(); j++) {
                    changeListeners.get(j).onChanged(String.valueOf(last), String.valueOf(currentSelection));
                }
                last = currentSelection.get();
            }
            pollEvents();
        }

        ImGui.endListBox();
    }

    public void add(String value){
        String[] temp = new String[list.length + 1];
        for (int i = 0; i < list.length; i++) {
            temp[i] = list[i];
        }

        temp[temp.length - 1] = value;

        list = temp;
    }

    public void remove(String value){

    }

    public void remove(int index){
        String[] temp = new String[list.length - 1];
        int in = 0;
        for(int i = 0; i < list.length; i++){
            if(i != index){
                temp[in++] = list[i];
            }
        }

        list = temp;
    }

    public String getSelectedItem(){
        return list[currentSelection.get()];
    }

    public int getSelectedIndex(){
        return currentSelection.get();
    }

    public void addChangeListener(ChangeListener changeListener){ changeListeners.add(changeListener); }
}
