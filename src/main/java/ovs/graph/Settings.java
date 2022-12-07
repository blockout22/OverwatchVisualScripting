package ovs.graph;

import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImString;

import java.util.ArrayList;

public class Settings {

    private ImString modeName = new ImString(500);
    private ImString description = new ImString(500);

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

    //DeathMatch settings
    private int[] gameTimeDM = new int[] {10};
    private int[] scoreToWinDM = new int[] {20};
    private ImBoolean initRespawnOnOffDM = new ImBoolean(true);
    //Maps Toggle
    public ArrayList<BoolInfoWithName> dmMapBools = new ArrayList<>();

    public Settings(){
        dmMapBools.add(new BoolInfoWithName("Black Forest", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Black Forest Winter", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Blizzard World", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Blizzard World Winter", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Castillo", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Château", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Château Guillard", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Château Guillard Halloween", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Dorado", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Ecopoint: Antarctica", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Eichenwalde", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Eichenwalde Halloween", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Hanamura", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Hanamura Winter", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Havana", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Hollywood", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Horizon Lunar Colony", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Ilios Lighthouse", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Ilios Ruins", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Ilois Well", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Kanezaka", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("King's Row", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("King's Row Winter", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Lijiang Control Center", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Lijiang Control Center Lunar New Year", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Lijiang Garden", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Lijiang Garden Lunar New Year", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Lijiang Night Market", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Lijiang Night Market Lunar New Year", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Malevento", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Necropolis", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Nepal Sanctum", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Nepal Shrine", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Nepal Village", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Oasis City Center", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Oasis Gardens", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Oasis University", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Paris", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Petra", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Route 66", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Temple Of Anubis", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Volskaya Industries", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Workshop Chamber", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Workshop Expanse", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Workshop Expanse Night", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Workshop Green Screen", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Workshop Island", new ImBoolean(true)));
        dmMapBools.add(new BoolInfoWithName("Workshop Island Night", new ImBoolean(true)));
    }

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

        ImGui.newLine();
        //Lobby Settings
        ImGui.text("---------------------------------------------------------");
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

        ImGui.newLine();
        ImGui.text("Modes");
        ImGui.text("---------------------------------------------------------");
        ImGui.checkbox("##Assault", assaultOnOff);
        ImGui.sameLine();
        ImGui.text("Assault");
        ImGui.checkbox("##Control", controlOnOff);
        ImGui.sameLine();
        ImGui.text("Control");
        ImGui.checkbox("##Escort", escortOnOff);
        ImGui.sameLine();
        ImGui.text("Escort");
        ImGui.checkbox("##Hybrid", hybridOnOff);
        ImGui.sameLine();
        ImGui.text("Hybrid");
        ImGui.checkbox("##Push", pushOnOff);
        ImGui.sameLine();
        ImGui.text("Push");
        ImGui.checkbox("##BountyHunter", bountyHunterOnOff);
        ImGui.sameLine();
        ImGui.text("Bouty Hunter");
        ImGui.checkbox("##CTF", ctfOnOff);
        ImGui.sameLine();
        ImGui.text("CTF");
        ImGui.checkbox("##DeathMatch", deathmatchOnOff);
        ImGui.sameLine();
        ImGui.text("DeathMatch");
        ImGui.sameLine();
        if(ImGui.button("Deathmatch Options")){
            ImGui.openPopup("Deathmatch_options");
        }
        ImGui.checkbox("##Elimination", eliminationOnOff);
        ImGui.sameLine();
        ImGui.text("Elimination");
        ImGui.checkbox("##TeamDeathMatch", teamDeathmatchOnOff);
        ImGui.sameLine();
        ImGui.text("Team DeathMatch");
        ImGui.checkbox("##PracticeRange", practiceRangeOnOff);
        ImGui.sameLine();
        ImGui.text("Practice Range");
        ImGui.checkbox("##Skirmish", skirmishOnOff);
        ImGui.sameLine();
        ImGui.text("Skirmish");

        ImGui.popItemWidth();


        //Deatch Match options popup
        if(ImGui.isPopupOpen("Deathmatch_options")){
            if(ImGui.beginPopup("Deathmatch_options")){
                ImGui.text("Game Time");
                ImGui.sameLine();
                ImGui.sliderInt("##time", gameTimeDM, 5, 15);
                ImGui.text("Score To Win");
                ImGui.sameLine();
                ImGui.sliderInt("##scoreToWin", scoreToWinDM, 1, 5000);
                ImGui.checkbox("##initRespawn", initRespawnOnOffDM);
                ImGui.sameLine();
                ImGui.text("Self Initialize Respawn");

                if(ImGui.button("All")) {
                    for (int i = 0; i < dmMapBools.size(); i++) {
                        BoolInfoWithName bool = dmMapBools.get(i);
                        bool.bool.set(true);
                    }
                }
                ImGui.sameLine();
                if(ImGui.button("None")){
                    for (int i = 0; i < dmMapBools.size(); i++) {
                        BoolInfoWithName bool = dmMapBools.get(i);
                        bool.bool.set(false);
                    }
                }
                ImGui.separator();
                //Deathmatch maps
                for(BoolInfoWithName biwn : dmMapBools) {
                    ImGui.checkbox("##" + biwn.name, biwn.bool);
                    ImGui.sameLine();
                    ImGui.text(biwn.name);
                }
                ImGui.endPopup();
            }
        }
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
                output += "\t\t{\n";

                output += "\t\t\tGame Length In Minutes: " + gameTimeDM[0] + "\n";
                output += "\t\t\tScore To Win: " + scoreToWinDM[0] + "\n";
                output += "\t\t\tSelf Initiated Respawn: " + (initRespawnOnOffDM.get() ? "On" : "Off") + "\n";

                int[] res = getDeathmatchMaps();

                if(!(res[0] == 0 || res[1] == 0)){
                    output += "\n";
                    if(res[0] >= res[1]){
                        //Disabled Maps
                        output += "\t\t\tdisabled maps\n";
                        output += "\t\t\t{\n";
                        for (int i = 0; i < dmMapBools.size(); i++) {
                            BoolInfoWithName info = dmMapBools.get(i);
                            if(!info.bool.get()){
                                output += "\t\t\t\t" + info.name + " 0\n";
                            }
                        }
                    }else if(res[1] > res[0])
                    {
                        //Enabled Maps
                        output += "\t\t\tenabled maps\n";
                        output += "\t\t\t{\n";
                        for (int i = 0; i < dmMapBools.size(); i++) {
                            BoolInfoWithName info = dmMapBools.get(i);
                            if(info.bool.get()){
                                output += "\t\t\t\t" + info.name + " 0\n";
                            }
                        }
                    }

                    output += "\t\t\t}\n";
                }
                output += "\t\t}\n";
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

    /**
     *gets the amount of enabled and disabled maps on deatchmatch mode and return [number of maps on, number of maps off]
     */
    private int[] getDeathmatchMaps(){
        int[] res = new int[2];
        for (int i = 0; i < dmMapBools.size(); i++) {
            BoolInfoWithName info = dmMapBools.get(i);
            if(info.bool.get()){
                res[0] = res[0] + 1;
            }else{
                res[1] = res[1] + 1;
            }
        }
        return res;
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

    public int getGameTimeDM() {
        return gameTimeDM[0];
    }

    public void setGameTimeDM(int gameTimeDM) {
        this.gameTimeDM[0] = gameTimeDM;
    }

    public int getScoreToWinDM() {
        return scoreToWinDM[0];
    }

    public void setScoreToWinDM(int scoreToWinDM) {
        this.scoreToWinDM[0] = scoreToWinDM;
    }

    public boolean getInitRespawnOnOffDM() {
        return initRespawnOnOffDM.get();
    }

    public void setInitRespawnOnOffDM(boolean initRespawnOnOffDM) {
        this.initRespawnOnOffDM.set(initRespawnOnOffDM);
    }

    public static class BoolInfoWithName{
        public String name;
        public ImBoolean bool;

        public BoolInfoWithName(String name, ImBoolean bool){
            this.name = name;
            this.bool = bool;
        }
    }
}
