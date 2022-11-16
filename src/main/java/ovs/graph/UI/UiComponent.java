package ovs.graph.UI;

import imgui.ImGui;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.HoverListener;
import ovs.graph.UI.Listeners.LeftClickListener;
import ovs.graph.UI.Listeners.RightClickListener;

import java.util.ArrayList;
import java.util.UUID;

public abstract class UiComponent {

    private boolean isHovered;

    protected String uniqueID = UUID.randomUUID().toString();
    protected ArrayList<HoverListener> hoverListeners = new ArrayList<>();
    protected ArrayList<LeftClickListener> leftClickListeners = new ArrayList<>();
    protected ArrayList<RightClickListener> rightClickListeners = new ArrayList<>();

    public abstract void show();

    public void addHoverListener(HoverListener hoverListener){
        hoverListeners.add(hoverListener);
    }

    public void addLeftClickListener(LeftClickListener clickListener){
        leftClickListeners.add(clickListener);
    }

    public void addRightClickListener(RightClickListener clickListener){
        rightClickListeners.add(clickListener);
    }

    protected void pollEvents(){
        if(ImGui.isItemClicked()){
            for (int i = 0; i < leftClickListeners.size(); i++) {
                if(leftClickListeners.get(i) != null){
                    leftClickListeners.get(i).onClicked();
                }
            }
        }
        isHovered = ImGui.isItemHovered();
        if(isHovered){

            if(hoverListeners.size() > 0){
                for (int i = 0; i < hoverListeners.size(); i++) {
                    if(hoverListeners.get(i) != null){
                        hoverListeners.get(i).onHovered();
                    }
                }
            }
        }

        if(ImGui.isItemClicked(1)){
            for (int i = 0; i < rightClickListeners.size(); i++) {
                if(rightClickListeners.get(i) != null){
                    rightClickListeners.get(i).onClicked();
                }
            }
        }
    }

    public boolean isHovered()
    {
        return isHovered;
    }
}
