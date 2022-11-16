package ovs.graph;

import imgui.ImGui;
import imgui.type.ImString;

public class Settings {

    public int team1MaxPlayers = 5;
    public int team2MaxPlayers = 5;

    private ImString modeName = new ImString();
    private ImString description = new ImString();

    public void show()
    {
        ImGui.text("Settings");

        ImGui.pushItemWidth(150);

        ImGui.text("Mode Name: ");
        ImGui.sameLine();
        ImGui.inputText("##ModeName", modeName);
        ImGui.text("Description: ");
        ImGui.sameLine();
        ImGui.inputText("##Description", description);
        ImGui.popItemWidth();
    }

    public String getOutput() {
        String output = "";
        output += "settings\n";
        output += "{\n";

        {
            output += "\tmain\n";
            output += "\t{\n";
            output += "\t\tMode Name: \"" + modeName.get() + "\"\n";
            output += "\t\tDescription: \"" + description.get() + "\"\n";
            output += "\t}\n";
        }

        {
            output += "\n";
            output += "\tlobby\n";
            output += "\t{\n";
            output += "\t\tMax Team 1 Players: " + team1MaxPlayers + "\n";
            output += "\t\tMax Team 2 Players: " + team2MaxPlayers + "\n";
            output += "\t}\n";
        }

        {
            output += "\n";
            output += "\tmodes\n";
            output += "\t{\n";
            output += "\t\tElimination\n";
            output += "\t}\n";
        }

        output += "}\n";

        return output;
    }
}
