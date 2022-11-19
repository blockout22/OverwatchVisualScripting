package ovs.graph.UI;

import imgui.ImGui;
import imgui.type.ImString;

public class TextField extends UiComponent{

    private ImString string = new ImString();

    public TextField(){
    }

    @Override
    public void show() {
        ImGui.inputText("##" + uniqueID, string);
    }

    public String getText(){
        return string.get();
    }

    public void setText(String text){
        string.set(text);
    }
}
