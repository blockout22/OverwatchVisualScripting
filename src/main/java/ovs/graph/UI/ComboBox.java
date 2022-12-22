package ovs.graph.UI;

import imgui.ImGui;
import imgui.type.ImString;
import ovs.graph.Popup;
import ovs.graph.PopupHandler;
import ovs.graph.UI.Listeners.ChangeListener;
import ovs.graph.UI.Listeners.OnOpenedListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ComboBox extends UiComponent{

    private ArrayList<ChangeListener> changeListeners = new ArrayList<>();
    private ArrayList<OnOpenedListener> onOpenedListeners = new ArrayList<OnOpenedListener>();

    private boolean justOpened = false;
    private boolean requestPopup = false;
    private boolean isSearchable = false;

    private ImString itemSearch = new ImString();

    private int currentSelectedIndex = -1;
    private String[] items = {
    };

    Popup popup = new Popup() {
        @Override
        public boolean show() {

            if(isSearchable){
                if(justOpened){
                    ImGui.setKeyboardFocusHere(0);
                    justOpened = false;
                }

                ImGui.inputTextWithHint("##", "Search Item", itemSearch);
            }

            for (int i = 0; i < items.length; i++) {
                if(itemSearch.get().toLowerCase().length() > 0){
                    if(!items[i].toLowerCase().contains(itemSearch.get())){
                        continue;
                    }
                }
                if(ImGui.menuItem(items[i])){
                    String oldValue = currentSelectedIndex == -1 ? "" : items[currentSelectedIndex];
                    currentSelectedIndex = i;
                    String newValue = items[currentSelectedIndex];
                    for (int j = 0; j < changeListeners.size(); j++) {
                        changeListeners.get(j).onChanged(oldValue, newValue);
                    }
                    return true;
                }
            }
//            currentSelectedIndex = -1;
            return false;
        }
    };

    public ComboBox() {
    }

    public ComboBox(String... options){
        for(String option : options){
            addOption(option);
        }
    }

    public void select(int index){
        if(index < 0){
            currentSelectedIndex = -1;
            return;
        }
        String oldValue = currentSelectedIndex == -1 ? "" : items[currentSelectedIndex];
        currentSelectedIndex = index;
        String newValue = items[currentSelectedIndex];
        for (int j = 0; j < changeListeners.size(); j++) {
            changeListeners.get(j).onChanged(oldValue, newValue);
        }
    }

    public int size(){
        return items.length;
    }

    public void addOption(String option){
        String[] temp = new String[items.length + 1];

        for (int i = 0; i < items.length; i++) {
            temp[i] = items[i];
        }

        temp[temp.length - 1] = option;

        items = temp;
    }

    public int getSelectedIndex(){
        return currentSelectedIndex;
    }

    public String getSelectedValue(){
        if(currentSelectedIndex != -1){
            return items[currentSelectedIndex];
        }
        return "";
    }

    @Override
    public void show() {
        if(requestPopup){
            itemSearch.set("");
            PopupHandler.open(popup);
            justOpened = true;
            for (int i = 0; i < onOpenedListeners.size(); i++) {
                onOpenedListeners.get(i).onOpen();
            }
            requestPopup = false;
        }
//
//        if(ImGui.isPopupOpen("popup" + uniqueID)){
//            if(ImGui.beginPopup("popup" + uniqueID)){
//                ImGui.listBox("list", currentSelection, items);
//                System.out.println("Popup");
////                    ImGui.closeCurrentPopup();
//
//            }
//            ImGui.endPopup();
//        }
//
        String buttonName = "                            ";
        if(currentSelectedIndex != -1 && items.length > 0){
            buttonName = items[currentSelectedIndex];
        }
        if(ImGui.button(buttonName + "##button" + uniqueID)){
            requestPopup = true;
        }
    }

    public String[] getOptions(){
        return items;
    }

    public void clear(){
        items = new String[0];
    }

    public void addChangeListener(ChangeListener changeListener){ changeListeners.add(changeListener); }

    public void addOnOpenedListener(OnOpenedListener onOpenedListener){
        this.onOpenedListeners.add(onOpenedListener);
    }

    public void selectValue(String value) {
        for (int i = 0; i < items.length; i++) {
            if(items[i].equals(value))
            {
                select(i);
                return;
            }
        }

        currentSelectedIndex = -1;
    }

    public void setSearchable(boolean searchable){
        this.isSearchable = searchable;
    }

    public void sort() {
        Arrays.sort(items);
    }
}
