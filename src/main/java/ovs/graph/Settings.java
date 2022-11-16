package ovs.graph;

import imgui.ImGui;

public class Settings {

    public String modeName = "Cooking";
    public String description = "Description";

    public int team1MaxPlayers = 5;
    public int team2MaxPlayers = 5;

    public void show()
    {
        ImGui.text("Hello Settings");
    }

    public String getOutput() {
        String output = "";
        output += "settings\n";
        output += "{\n";

        {
            output += "\tmain\n";
            output += "\t{\n";
            output += "\t\tMode Name: \"" + modeName + "\"\n";
            output += "\t\tDescription: \"" + description + "\"\n";
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
