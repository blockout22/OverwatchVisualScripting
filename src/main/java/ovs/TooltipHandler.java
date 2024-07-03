package ovs;

public class TooltipHandler {

    private static String tooltip = "";

    public static void set(String text){
        TooltipHandler.tooltip = text;
    }

    public static String getTooltip(){
        return tooltip;
    }
}
