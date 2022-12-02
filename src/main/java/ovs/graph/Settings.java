package ovs.graph;

import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImString;

public class Settings {

    private ImString modeName = new ImString();
    private ImString description = new ImString();

    private int[] maxT1Players = {
            5
    };

    private int[] maxT2Players = {
            5
    };

    private int[] maxFFAPlayers = {
            0
    };

    private ImBoolean assaultOnOff = new ImBoolean(false);
    private ImBoolean controlOnOff = new ImBoolean(true);
    private ImBoolean escortOnOff = new ImBoolean(true);
    private ImBoolean hybridOnOff = new ImBoolean(true);
    private ImBoolean pushOnOff = new ImBoolean(true);
    private ImBoolean bountyHunterOnOff = new ImBoolean(false);
    private ImBoolean ctfOnOff = new ImBoolean(false);
    private ImBoolean deathmatchOnOff = new ImBoolean(false);
    private ImBoolean eliminationOnOff = new ImBoolean(false);
    private ImBoolean teamDeathmatchOnOff = new ImBoolean(false);
    private ImBoolean practiceRangeOnOff = new ImBoolean(false);
    private ImBoolean skirmishOnOff = new ImBoolean(false);

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

        //Lobby Settings
        ImGui.separator();
        ImGui.text("Lobby Settings");
        ImGui.text("Max Team 1 Players");
        ImGui.sameLine();
        ImGui.sliderInt("##Max Team 1 Players", maxT1Players, 0, 12);

        ImGui.text("Max Team 2 Players");
        ImGui.sameLine();
        ImGui.sliderInt("##Max Team 2 Players", maxT2Players, 0, 12);

        ImGui.text("Max FFA Players");
        ImGui.sameLine();
        ImGui.sliderInt("##Max FFA Players", maxFFAPlayers, 0, 12);

        ImGui.separator();
        ImGui.text("Modes");
        ImGui.text("Assault");
        ImGui.sameLine();
        ImGui.checkbox("##Assault", assaultOnOff);
        ImGui.text("Control");
        ImGui.sameLine();
        ImGui.checkbox("##Control", controlOnOff);
        ImGui.text("Escort");
        ImGui.sameLine();
        ImGui.checkbox("##Escort", escortOnOff);
        ImGui.text("Hybrid");
        ImGui.sameLine();
        ImGui.checkbox("##Hybrid", hybridOnOff);
        ImGui.text("Push");
        ImGui.sameLine();
        ImGui.checkbox("##Push", pushOnOff);
        ImGui.text("Bouty Hunter");
        ImGui.sameLine();
        ImGui.checkbox("##BountyHunter", bountyHunterOnOff);
        ImGui.text("CTF");
        ImGui.sameLine();
        ImGui.checkbox("##CTF", ctfOnOff);
        ImGui.text("DeathMatch");
        ImGui.sameLine();
        ImGui.checkbox("##DeathMatch", deathmatchOnOff);
        ImGui.text("Elimination");
        ImGui.sameLine();
        ImGui.checkbox("##Elimination", eliminationOnOff);
        ImGui.text("Team DeathMatch");
        ImGui.sameLine();
        ImGui.checkbox("##TeamDeathMatch", teamDeathmatchOnOff);
        ImGui.text("Practice Range");
        ImGui.sameLine();
        ImGui.checkbox("##PracticeRange", practiceRangeOnOff);
        ImGui.text("Skirmish");
        ImGui.sameLine();
        ImGui.checkbox("##Skirmish", skirmishOnOff);

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
            output += "\t\tMax Team 1 Players: " + maxT1Players[0] + "\n";
            //Can't set Team 2 player limit when using practise range
            if(!practiceRangeOnOff.get()) {
                output += "\t\tMax Team 2 Players: " + maxT2Players[0] + "\n";
            }
            output += "\t\tMax FFA Players: " + maxFFAPlayers[0] + "\n";
            output += "\t}\n";
        }

        {
            output += "\n";
            output += "\tmodes\n";
            output += "\t{\n";

            if(assaultOnOff.get()){
                output += "\t\tAssault\n";
            }

            if(controlOnOff.get()){
                output += "\t\tControl\n";
            }

            if(escortOnOff.get()){
                output += "\t\tEscort\n";
            }

            if(hybridOnOff.get()){
                output += "\t\tHybrid\n";
            }

            if(pushOnOff.get()){
                output += "\t\tPush\n";
            }

            if(bountyHunterOnOff.get()){
                output += "\t\tBounty Hunter\n";
            }

            if(ctfOnOff.get()){
                output += "\t\tCapture the Flag\n";
            }

            if(deathmatchOnOff.get()){
                output += "\t\tDeathmatch\n";
            }

            if(eliminationOnOff.get()) {
                output += "\t\tElimination\n";
            }

            if(teamDeathmatchOnOff.get()){
                output += "\t\tTeam Deathmatch\n";
            }

            if(practiceRangeOnOff.get()){
                output += "\t\tPractice Range\n";
            }

            if(skirmishOnOff.get()){
                output += "\t\tSkirmish\n";
            }


            output += "\t}\n";
        }

        output += "}\n";

        return output;
    }

    public void setModeName(String name){
        modeName.set(name);
    }

    public void setDescription(String des){
        description.set(des);
    }

    public String getModeName(){
        return modeName.get();
    }

    public String getDescription(){
        return description.get();
    }

    public int getMaxT1Players() {
        return maxT1Players[0];
    }

    public void setMaxT1Players(int maxT1Players) {
        this.maxT1Players[0] = maxT1Players;
    }

    public int getMaxT2Players() {
        return maxT2Players[0];
    }

    public void setMaxT2Players(int maxT2Players) {
        this.maxT2Players[0] = maxT2Players;
    }

    public int getMaxFFAPlayers() {
        return maxFFAPlayers[0];
    }

    public void setMaxFFAPlayers(int maxFFAPlayers) {
        this.maxFFAPlayers[0] = maxFFAPlayers;
    }

    public boolean getAssaultOnOff() {
        return assaultOnOff.get();
    }

    public void setAssaultOnOff(boolean assaultOnOff) {
        this.assaultOnOff.set(assaultOnOff);
    }

    public boolean getControlOnOff() {
        return controlOnOff.get();
    }

    public void setControlOnOff(boolean controlOnOff) {
        this.controlOnOff.set(controlOnOff);
    }

    public boolean getEscortOnOff() {
        return escortOnOff.get();
    }

    public void setEscortOnOff(boolean escortOnOff) {
        this.escortOnOff.set(escortOnOff);
    }

    public boolean getHybridOnOff() {
        return hybridOnOff.get();
    }

    public void setHybridOnOff(boolean hybridOnOff) {
        this.hybridOnOff.set(hybridOnOff);
    }

    public boolean getPushOnOff() {
        return pushOnOff.get();
    }

    public void setPushOnOff(boolean pushOnOff) {
        this.pushOnOff.set(pushOnOff);
    }

    public boolean getBountyHunterOnOff() {
        return bountyHunterOnOff.get();
    }

    public void setBountyHunterOnOff(boolean bountyHunterOnOff) {
        this.bountyHunterOnOff.set(bountyHunterOnOff);
    }

    public boolean getCtfOnOff() {
        return ctfOnOff.get();
    }

    public void setCtfOnOff(boolean ctfOnOff) {
        this.ctfOnOff.set(ctfOnOff);
    }

    public boolean getDeathmatchOnOff() {
        return deathmatchOnOff.get();
    }

    public void setDeathmatchOnOff(boolean deathmatchOnOff) {
        this.deathmatchOnOff.set(deathmatchOnOff);
    }

    public boolean getEliminationOnOff() {
        return eliminationOnOff.get();
    }

    public void setEliminationOnOff(boolean eliminationOnOff) {
        this.eliminationOnOff.set(eliminationOnOff);
    }

    public boolean getTeamDeathmatchOnOff() {
        return teamDeathmatchOnOff.get();
    }

    public void setTeamDeathmatchOnOff(boolean teamDeathmatchOnOff) {
        this.teamDeathmatchOnOff.set(teamDeathmatchOnOff);
    }

    public boolean getPracticeRangeOnOff() {
        return practiceRangeOnOff.get();
    }

    public void setPracticeRangeOnOff(boolean practiceRangeOnOff) {
        this.practiceRangeOnOff.set(practiceRangeOnOff);
    }

    public boolean getSkirmishOnOff() {
        return skirmishOnOff.get();
    }

    public void setSkirmishOnOff(boolean skirmishOnOff) {
        this.skirmishOnOff.set(skirmishOnOff);
    }
}
