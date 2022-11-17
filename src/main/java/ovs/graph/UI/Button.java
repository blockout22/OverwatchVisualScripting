package ovs.graph.UI;

import imgui.ImGui;

public class Button extends UiComponent{

    private String text = "##";

    public Button(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void show() {
        if(ImGui.button(text + "##" + uniqueID)){

        }

        pollEvents();
    }
}
