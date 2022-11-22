package ovs.graph.UI;

import imgui.ImGui;
import imgui.ImGuiTextFilter;
import imgui.type.ImString;
import ovs.graph.UI.Listeners.ChangeListener;

import java.util.ArrayList;

public class TextField extends UiComponent{

    private ImString string = new ImString();
    private String lastText = "";

    private ArrayList<ChangeListener> changeListeners = new ArrayList<>();

    public TextField(){
    }

    public void addChangedListener(ChangeListener changeListener){
        changeListeners.add(changeListener);
    }

    @Override
    public void show() {
        if(ImGui.inputText("##" + uniqueID, string)){

        }

        if(lastText != string.get()){
            for (int i = 0; i < changeListeners.size(); i++) {
                changeListeners.get(i).onChanged(lastText, string.get());
            }
            lastText = string.get();
        }
    }

    public String getText(){
        return string.get();
    }

    public void setText(String text){
        string.set(text);
    }
}
