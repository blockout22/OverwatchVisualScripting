package ovs.graph;

import imgui.ImGui;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImString;

import java.util.ArrayList;

public class Settings {

    public ImString modeName = new ImString(500);
    public ImString description = new ImString(500);

    public int[] maxT1Players = {
            5
    };

    public int[] maxT2Players = {
            5
    };

    public int[] maxFFAPlayers = {
            0
    };

    public ImBoolean assaultOnOff = new ImBoolean(false);
    public ImBoolean controlOnOff = new ImBoolean(true);
    public ImBoolean escortOnOff = new ImBoolean(true);
    public ImBoolean hybridOnOff = new ImBoolean(true);
    public ImBoolean pushOnOff = new ImBoolean(true);
    public ImBoolean bountyHunterOnOff = new ImBoolean(false);
    public ImBoolean ctfOnOff = new ImBoolean(false);
    public ImBoolean deathmatchOnOff = new ImBoolean(false);
    public ImBoolean eliminationOnOff = new ImBoolean(false);
    public ImBoolean teamDeathmatchOnOff = new ImBoolean(false);
    public ImBoolean practiceRangeOnOff = new ImBoolean(false);
    public ImBoolean skirmishOnOff = new ImBoolean(false);


    //Assault Settings
    public int[] assaultSpeedModifier = new int[]{100};
    public ImBoolean assaultCompRulesOnOff = new ImBoolean(false);

    //Control Settings
    public int[] controlSpeedModifier = new int[]{100};
    public ImBoolean controlCompetitiveRules = new ImBoolean(false);
    //Limit Valid Control Points: All First Second Third
    public ImInt controlValidControlPointsSelection = new ImInt();
    public String[] controlValidControlPointsOptions = {"All", "First", "Second", "Third"};
    public int[] controlScoreToWin = new int[]{2};
    public int[] controlScoringSpeedModifier = new int[]{100};

    //Escort Settings
    public ImBoolean escortCompRulesOnOff = new ImBoolean(false);
    public int[] escortSpeedModifier = new int[]{100};

    //Hybrid Settings
    public int[] hybridSpeedModifier = new int[]{100};
    public ImBoolean hybridCompRulesOnOff = new ImBoolean(false);
    public int[] hybridPayloadSpeedModifier = new int[]{100};

    //Push Settings
    public ImBoolean pushCompRulesOnOff = new ImBoolean(false);
    public int[] pushPushSpeedModifier = new int[]{100};
    public int[] pushWalkSpeedModifier = new int[]{100};

    //DeathMatch settings
    public int[] gameTimeDM = new int[] {10};
    public int[] scoreToWinDM = new int[] {20};
    public ImBoolean initRespawnOnOffDM = new ImBoolean(true);
    //Maps Toggle
    public ArrayList<BoolInfoWithName> assaultMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> controlMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> escortMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> hybridMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> pushMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> dmMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> extensionBools = new ArrayList<>();

    public Settings(){

        assaultMap.add(new BoolInfoWithName("Hanamura", new ImBoolean(true)));
        assaultMap.add(new BoolInfoWithName("Hanamura Winter", new ImBoolean(true)));
        assaultMap.add(new BoolInfoWithName("Horizon Lunar Colony", new ImBoolean(true)));
        assaultMap.add(new BoolInfoWithName("Paris", new ImBoolean(true)));
        assaultMap.add(new BoolInfoWithName("Temple Of Anubis", new ImBoolean(true)));
        assaultMap.add(new BoolInfoWithName("Volskaya Industries", new ImBoolean(true)));

        controlMap.add(new BoolInfoWithName("Busan", new ImBoolean(true)));
        controlMap.add(new BoolInfoWithName("Ilios", new ImBoolean(true)));
        controlMap.add(new BoolInfoWithName("Lijiang Tower", new ImBoolean(true)));
        controlMap.add(new BoolInfoWithName("Lijiang Tower Lunar New Year", new ImBoolean(true)));
        controlMap.add(new BoolInfoWithName("Nepal", new ImBoolean(true)));
        controlMap.add(new BoolInfoWithName("Oasis", new ImBoolean(true)));

        escortMap.add(new BoolInfoWithName("Circuit Royal", new ImBoolean(true)));
        escortMap.add(new BoolInfoWithName("Dorado", new ImBoolean(true)));
        escortMap.add(new BoolInfoWithName("Havana", new ImBoolean(true)));
        escortMap.add(new BoolInfoWithName("Junkertown", new ImBoolean(true)));
        escortMap.add(new BoolInfoWithName("Rialto", new ImBoolean(true)));
        escortMap.add(new BoolInfoWithName("Route 66", new ImBoolean(true)));
        escortMap.add(new BoolInfoWithName("Shambali Monastery", new ImBoolean(true)));
        escortMap.add(new BoolInfoWithName("Watchpoint: Gibraltar", new ImBoolean(true)));

        hybridMap.add(new BoolInfoWithName("Blizzard World", new ImBoolean(true)));
        hybridMap.add(new BoolInfoWithName("Blizzard World Winter", new ImBoolean(true)));
        hybridMap.add(new BoolInfoWithName("Eichenwalde", new ImBoolean(true)));
        hybridMap.add(new BoolInfoWithName("Eichenwalde Halloween", new ImBoolean(true)));
        hybridMap.add(new BoolInfoWithName("Hollywood", new ImBoolean(true)));
        hybridMap.add(new BoolInfoWithName("Hollywood Halloween", new ImBoolean(true)));
        hybridMap.add(new BoolInfoWithName("King's Row", new ImBoolean(true)));
        hybridMap.add(new BoolInfoWithName("King's Row Winter", new ImBoolean(true)));
        hybridMap.add(new BoolInfoWithName("Midtown", new ImBoolean(true)));
        hybridMap.add(new BoolInfoWithName("Numbani", new ImBoolean(true)));
        hybridMap.add(new BoolInfoWithName("Paraíso", new ImBoolean(true)));

        pushMap.add(new BoolInfoWithName("Colossea", new ImBoolean(true)));
        pushMap.add(new BoolInfoWithName("Esperança", new ImBoolean(true)));
        pushMap.add(new BoolInfoWithName("new Queen Street", new ImBoolean(true)));

        dmMap.add(new BoolInfoWithName("Black Forest", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Black Forest Winter", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Blizzard World", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Blizzard World Winter", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Castillo", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Château", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Château Guillard", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Château Guillard Halloween", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Dorado", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Ecopoint: Antarctica", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Eichenwalde", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Eichenwalde Halloween", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Hanamura", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Hanamura Winter", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Havana", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Hollywood", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Horizon Lunar Colony", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Ilios Lighthouse", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Ilios Ruins", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Ilois Well", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Kanezaka", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("King's Row", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("King's Row Winter", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Lijiang Control Center", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Lijiang Control Center Lunar New Year", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Lijiang Garden", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Lijiang Garden Lunar New Year", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Lijiang Night Market", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Lijiang Night Market Lunar New Year", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Malevento", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Necropolis", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Nepal Sanctum", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Nepal Shrine", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Nepal Village", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Oasis City Center", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Oasis Gardens", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Oasis University", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Paris", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Petra", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Route 66", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Temple Of Anubis", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Volskaya Industries", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Workshop Chamber", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Workshop Expanse", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Workshop Expanse Night", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Workshop Green Screen", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Workshop Island", new ImBoolean(true)));
        dmMap.add(new BoolInfoWithName("Workshop Island Night", new ImBoolean(true)));

        extensionBools.add(new BoolInfoWithName("Beam Sounds", new ImBoolean(false)));
        extensionBools.add(new BoolInfoWithName("Beam Effects", new ImBoolean(false)));
        extensionBools.add(new BoolInfoWithName("Buff and Debuff Sounds", new ImBoolean(false)));
        extensionBools.add(new BoolInfoWithName("Buff Status Effects", new ImBoolean(false)));
        extensionBools.add(new BoolInfoWithName("Debuff Status Effects", new ImBoolean(false)));
        extensionBools.add(new BoolInfoWithName("Energy Explosion Effects", new ImBoolean(false)));
        extensionBools.add(new BoolInfoWithName("Play More Effects", new ImBoolean(false)));
        extensionBools.add(new BoolInfoWithName("Spawn More Dummy Bots", new ImBoolean(false)));
        extensionBools.add(new BoolInfoWithName("Explosion Sounds", new ImBoolean(false)));
        extensionBools.add(new BoolInfoWithName("Kinetic Explosion Effects", new ImBoolean(false)));
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
        ImGui.text("Lobby Settings");
        ImGui.text("---------------------------------------------------------");
        if(ImGui.button("Extension Options")){
            ImGui.openPopup("Extension_options");
        }
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
        ImGui.sameLine();
        if(ImGui.button("Assault Options")){
            ImGui.openPopup("Assault_options");
        }
        ImGui.checkbox("##Control", controlOnOff);
        ImGui.sameLine();
        ImGui.text("Control");
        ImGui.sameLine();
        if(ImGui.button("Control Options")){
            ImGui.openPopup("Control_options");
        }
        ImGui.checkbox("##Escort", escortOnOff);
        ImGui.sameLine();
        ImGui.text("Escort");
        ImGui.sameLine();
        if(ImGui.button("Escort Options")){
            ImGui.openPopup("Escort_options");
        }
        ImGui.checkbox("##Hybrid", hybridOnOff);
        ImGui.sameLine();
        ImGui.text("Hybrid");
        ImGui.sameLine();
        if(ImGui.button("Hybrid Options")){
            ImGui.openPopup("Hybrid_options");
        }
        ImGui.checkbox("##Push", pushOnOff);
        ImGui.sameLine();
        ImGui.text("Push");
        ImGui.sameLine();
        if(ImGui.button("Push Options")){
            ImGui.openPopup("Push_options");
        }
        ImGui.checkbox("##BountyHunter", bountyHunterOnOff);
        ImGui.sameLine();
        ImGui.text("Bouty Hunter");
//        ImGui.sameLine();
//        if(ImGui.button("Bounty Hunter Options")){
//            ImGui.openPopup("Bountyhunter_options");
//        }
        ImGui.checkbox("##CTF", ctfOnOff);
        ImGui.sameLine();
        ImGui.text("CTF");
//        ImGui.sameLine();
//        if(ImGui.button("CTF Options")){
//            ImGui.openPopup("Ctf_options");
//        }
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
//        ImGui.sameLine();
//        if(ImGui.button("Elimination Options")){
//            ImGui.openPopup("Elimination_options");
//        }
        ImGui.checkbox("##TeamDeathMatch", teamDeathmatchOnOff);
        ImGui.sameLine();
        ImGui.text("Team DeathMatch");
//        ImGui.sameLine();
//        if(ImGui.button("Team DeathMatch Options")){
//            ImGui.openPopup("Teamdeathmatch_options");
//        }
        ImGui.checkbox("##PracticeRange", practiceRangeOnOff);
        ImGui.sameLine();
        ImGui.text("Practice Range");
//        ImGui.sameLine();
//        if(ImGui.button("Practice Range Options")){
//            ImGui.openPopup("Practicerange_options");
//        }
        ImGui.checkbox("##Skirmish", skirmishOnOff);
        ImGui.sameLine();
        ImGui.text("Skirmish");
        ImGui.sameLine();
//        if(ImGui.button("Skirmish Options")){
//            ImGui.openPopup("Skirmish_options");
//        }

        ImGui.popItemWidth();

        //Assault Options popup
        if(ImGui.isPopupOpen("Assault_options")){
            if(ImGui.beginPopup("Assault_options")){
                ImGui.text("Capture Speed Modifier");
                ImGui.sameLine();
                ImGui.sliderInt("##assaultspeed", assaultSpeedModifier, 10, 500);
                ImGui.checkbox("##assaultCompRules", assaultCompRulesOnOff);
                ImGui.sameLine();
                ImGui.text("Competitive Rules");

                if(ImGui.button("All")) {
                    for (int i = 0; i < assaultMap.size(); i++) {
                        BoolInfoWithName bool = assaultMap.get(i);
                        bool.bool.set(true);
                    }
                }
                ImGui.sameLine();
                if(ImGui.button("None")){
                    for (int i = 0; i < assaultMap.size(); i++) {
                        BoolInfoWithName bool = assaultMap.get(i);
                        bool.bool.set(false);
                    }
                }
                ImGui.separator();
                //Assault maps
                for(BoolInfoWithName biwn : assaultMap) {
                    ImGui.checkbox("##" + biwn.name, biwn.bool);
                    ImGui.sameLine();
                    ImGui.text(biwn.name);
                }

                ImGui.endPopup();
            }
        }

        //Control Options popup
        if(ImGui.isPopupOpen("Control_options")) {
            if(ImGui.beginPopup("Control_options")){
                ImGui.text("Capture Speed Modifer");
                ImGui.sameLine();
                ImGui.sliderInt("##controlSpeed", controlSpeedModifier, 10, 500);
                ImGui.checkbox("##controlCompRules", controlCompetitiveRules);
                ImGui.sameLine();
                ImGui.text("Competitive Rules");
                ImGui.combo("Limit Valid Control Points:", controlValidControlPointsSelection, controlValidControlPointsOptions);
                ImGui.sliderInt("##controlScoreToWin", controlScoreToWin, 10, 500);
                ImGui.sameLine();
                ImGui.text("Score To Win");
                ImGui.sliderInt("##controlScoringSpeedMod", controlScoringSpeedModifier, 10, 500);
                ImGui.sameLine();
                ImGui.text("Scoring Speed Modifier");


                if(ImGui.button("All")) {
                    for (int i = 0; i < controlMap.size(); i++) {
                        BoolInfoWithName bool = controlMap.get(i);
                        bool.bool.set(true);
                    }
                }
                ImGui.sameLine();
                if(ImGui.button("None")){
                    for (int i = 0; i < controlMap.size(); i++) {
                        BoolInfoWithName bool = controlMap.get(i);
                        bool.bool.set(false);
                    }
                }
                ImGui.separator();
                //Assault maps
                for(BoolInfoWithName biwn : controlMap) {
                    ImGui.checkbox("##" + biwn.name, biwn.bool);
                    ImGui.sameLine();
                    ImGui.text(biwn.name);
                }

                ImGui.endPopup();
            }
        }

        //Escort Options popup
        if(ImGui.isPopupOpen("Escort_options")){
            if(ImGui.beginPopup("Escort_options")){
                ImGui.text("Capture Speed Modifier");
                ImGui.sameLine();
                ImGui.sliderInt("##escortspeed", escortSpeedModifier, 10, 500);
                ImGui.checkbox("##escortCompRules", escortCompRulesOnOff);
                ImGui.sameLine();
                ImGui.text("Competitive Rules");

                if(ImGui.button("All")) {
                    for (int i = 0; i < escortMap.size(); i++) {
                        BoolInfoWithName bool = escortMap.get(i);
                        bool.bool.set(true);
                    }
                }
                ImGui.sameLine();
                if(ImGui.button("None")){
                    for (int i = 0; i < escortMap.size(); i++) {
                        BoolInfoWithName bool = escortMap.get(i);
                        bool.bool.set(false);
                    }
                }
                ImGui.separator();
                //Assault maps
                for(BoolInfoWithName biwn : escortMap) {
                    ImGui.checkbox("##" + biwn.name, biwn.bool);
                    ImGui.sameLine();
                    ImGui.text(biwn.name);
                }

                ImGui.endPopup();
            }
        }

        //Hybrid options popup
        if(ImGui.isPopupOpen("Hybrid_options")){
            if(ImGui.beginPopup("Hybrid_options")){
                ImGui.text("Capture Speed Modifier");
                ImGui.sameLine();
                ImGui.sliderInt("##hybridspeed", hybridSpeedModifier, 10, 500);
                ImGui.checkbox("##hybridCompRules", hybridCompRulesOnOff);
                ImGui.sameLine();
                ImGui.text("Competitive Rules");
                ImGui.sliderInt("##hybridScoringSpeedMod", hybridSpeedModifier, 10, 500);
                ImGui.sameLine();
                ImGui.text("Scoring Speed Modifier");

                if(ImGui.button("All")) {
                    for (int i = 0; i < hybridMap.size(); i++) {
                        BoolInfoWithName bool = hybridMap.get(i);
                        bool.bool.set(true);
                    }
                }
                ImGui.sameLine();
                if(ImGui.button("None")){
                    for (int i = 0; i < hybridMap.size(); i++) {
                        BoolInfoWithName bool = hybridMap.get(i);
                        bool.bool.set(false);
                    }
                }
                ImGui.separator();
                //Assault maps
                for(BoolInfoWithName biwn : hybridMap) {
                    ImGui.checkbox("##" + biwn.name, biwn.bool);
                    ImGui.sameLine();
                    ImGui.text(biwn.name);
                }

                ImGui.endPopup();
            }
        }

        if(ImGui.isPopupOpen("Push_options")){
            if(ImGui.beginPopup("Push_options")){
                ImGui.checkbox("##pushCompRules", pushCompRulesOnOff);
                ImGui.sameLine();
                ImGui.text("Competitive Rules");
                ImGui.sliderInt("##pushPushSpeed", pushPushSpeedModifier, 10, 500);
                ImGui.sameLine();
                ImGui.text("Push Speed Modifier");
                ImGui.sliderInt("##pushWalkSpeed", pushWalkSpeedModifier, 10, 500);
                ImGui.sameLine();
                ImGui.text("Walk Speed Modifier");

                if(ImGui.button("All")) {
                    for (int i = 0; i < pushMap.size(); i++) {
                        BoolInfoWithName bool = pushMap.get(i);
                        bool.bool.set(true);
                    }
                }
                ImGui.sameLine();
                if(ImGui.button("None")){
                    for (int i = 0; i < pushMap.size(); i++) {
                        BoolInfoWithName bool = pushMap.get(i);
                        bool.bool.set(false);
                    }
                }
                ImGui.separator();
                //Assault maps
                for(BoolInfoWithName biwn : pushMap) {
                    ImGui.checkbox("##" + biwn.name, biwn.bool);
                    ImGui.sameLine();
                    ImGui.text(biwn.name);
                }

                ImGui.endPopup();
            }
        }

        //Deatch Match options popup
        if(ImGui.isPopupOpen("Deathmatch_options")){
            if(ImGui.beginPopup("Deathmatch_options")){
                ImGui.text("Game Time");
                ImGui.sameLine();
                ImGui.sliderInt("##dmtime", gameTimeDM, 5, 15);
                ImGui.text("Score To Win");
                ImGui.sameLine();
                ImGui.sliderInt("##dmscoreToWin", scoreToWinDM, 1, 5000);
                ImGui.checkbox("##dminitRespawn", initRespawnOnOffDM);
                ImGui.sameLine();
                ImGui.text("Self Initialize Respawn");

                if(ImGui.button("All")) {
                    for (int i = 0; i < dmMap.size(); i++) {
                        BoolInfoWithName bool = dmMap.get(i);
                        bool.bool.set(true);
                    }
                }
                ImGui.sameLine();
                if(ImGui.button("None")){
                    for (int i = 0; i < dmMap.size(); i++) {
                        BoolInfoWithName bool = dmMap.get(i);
                        bool.bool.set(false);
                    }
                }
                ImGui.separator();
                //Deathmatch maps
                for(BoolInfoWithName biwn : dmMap) {
                    ImGui.checkbox("##" + biwn.name, biwn.bool);
                    ImGui.sameLine();
                    ImGui.text(biwn.name);
                }
                ImGui.endPopup();
            }
        }

        if(ImGui.isPopupOpen("Extension_options")){
            if(ImGui.beginPopup("Extension_options")){
                for (int i = 0; i < extensionBools.size(); i++) {
                    BoolInfoWithName bool = extensionBools.get(i);
                    ImGui.checkbox("##" + bool.name, bool.bool);
                    ImGui.sameLine();
                    ImGui.text(bool.name);
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
            output += "main\n";
            output += "{\n";
            output += "Mode Name: \"" + modeName.get() + "\"\n";
            output += "Description: \"" + description.get() + "\"\n";
            output += "}\n";
        }

        {
            output += "\n";
            output += "lobby\n";
            output += "{\n";
            output += "Max Team 1 Players: " + maxT1Players[0] + "\n";
            //Can't set Team 2 player limit when using practise range
            if(!practiceRangeOnOff.get()) {
                output += "Max Team 2 Players: " + maxT2Players[0] + "\n";
            }
            output += "Max FFA Players: " + maxFFAPlayers[0] + "\n";
            output += "}\n";
        }

        {
            output += "\n";
            output += "modes\n";
            output += "{\n";

            if(assaultOnOff.get()){
                output += "Assault\n";
                output += "{\n";

                output += "Capture Speed Modifier: " + assaultSpeedModifier[0] + "%\n";
                output += "Competitive Rules: " + (assaultCompRulesOnOff.get() ? "On" : "Off") + "\n";

                int[] res = getMaps(assaultMap);

                if(!(res[0] == 0 || res[1] == 0)){
                    output += "\n";
                    if(res[0] >= res[1]){
                        //Disabled Maps
                        output += "disabled maps\n";
                        output += "{\n";
                        for (int i = 0; i < assaultMap.size(); i++) {
                            BoolInfoWithName info = assaultMap.get(i);
                            if(!info.bool.get()){
                                output += "" + info.name + " 0\n";
                            }
                        }
                    }else if(res[1] > res[0])
                    {
                        //Enabled Maps
                        output += "enabled maps\n";
                        output += "{\n";
                        for (int i = 0; i < assaultMap.size(); i++) {
                            BoolInfoWithName info = assaultMap.get(i);
                            if(info.bool.get()){
                                output += "" + info.name + " 0\n";
                            }
                        }
                    }

                    output += "}\n";
                }
                output += "}\n";
            }

            if(controlOnOff.get()){
                output += "Control\n";
                output += "{\n";

                output += "Capture Speed Modifier: " + controlSpeedModifier[0] + "%\n";
                output += "Competitive Rules: " + (controlCompetitiveRules.get() ? "On" : "Off") + "\n";
                output += "Limit Valid Control Points: " + controlValidControlPointsOptions[controlValidControlPointsSelection.get()] + "\n";
                output += "Scoring Speed Modifier: " + controlScoringSpeedModifier[0] + "%\n";

                int[] res = getMaps(controlMap);

                if(!(res[0] == 0 || res[1] == 0)){
                    output += "\n";
                    if(res[0] >= res[1]){
                        //Disabled Maps
                        output += "disabled maps\n";
                        output += "{\n";
                        for (int i = 0; i < controlMap.size(); i++) {
                            BoolInfoWithName info = controlMap.get(i);
                            if(!info.bool.get()){
                                output += "" + info.name + " 0\n";
                            }
                        }
                    }else if(res[1] > res[0])
                    {
                        //Enabled Maps
                        output += "enabled maps\n";
                        output += "{\n";
                        for (int i = 0; i < controlMap.size(); i++) {
                            BoolInfoWithName info = controlMap.get(i);
                            if(info.bool.get()){
                                output += "" + info.name + " 0\n";
                            }
                        }
                    }

                    output += "}\n";
                }
                output += "}\n";
            }

            if(escortOnOff.get()){
                output += "Escort\n";
                output += "{\n";

                output += "Competitive Rules: " + (escortCompRulesOnOff.get() ? "On" : "Off") + "\n";
                output += "Payload Speed Modifier: " + escortSpeedModifier[0] + "%\n";

                output += getMapOutput(escortMap);

                output += "}\n";
            }

            if(hybridOnOff.get()){
                output += "Hybrid\n";
                output += "{\n";

                output += "Capture Speed Modifier: " + hybridSpeedModifier[0] + "%\n";
                output += "Competitive Rules: " + (hybridCompRulesOnOff.get() ? "On" : "Off") + "\n";
                output += "Payload Speed Modifier: " + hybridPayloadSpeedModifier[0] + "%\n";

                output += getMapOutput(hybridMap);

                output += "}\n";
            }

            if(pushOnOff.get()){
                output += "Push\n";
                output += "{\n";

                output += "Competitive Rules: " + (pushCompRulesOnOff.get() ? "On" : "Off") + "\n";
                output += "TS-1 Push Speed Modifier: " + pushPushSpeedModifier[0] + "%\n";
                output += "TS-1 Walk Speed Modifier: " + pushWalkSpeedModifier[0] + "%\n";

                output += getMapOutput(pushMap);

                output += "}\n";
            }

            if(bountyHunterOnOff.get()){
                output += "Bounty Hunter\n";
            }

            if(ctfOnOff.get()){
                output += "Capture the Flag\n";
            }

            if(deathmatchOnOff.get()){
                output += "Deathmatch\n";
                output += "{\n";

                output += "Game Length In Minutes: " + gameTimeDM[0] + "\n";
                output += "Score To Win: " + scoreToWinDM[0] + "\n";
                output += "Self Initiated Respawn: " + (initRespawnOnOffDM.get() ? "On" : "Off") + "\n";

                int[] res = getMaps(dmMap);

                if(!(res[0] == 0 || res[1] == 0)){
                    output += "\n";
                    if(res[0] >= res[1]){
                        //Disabled Maps
                        output += "disabled maps\n";
                        output += "{\n";
                        for (int i = 0; i < dmMap.size(); i++) {
                            BoolInfoWithName info = dmMap.get(i);
                            if(!info.bool.get()){
                                output += "" + info.name + " 0\n";
                            }
                        }
                    }else if(res[1] > res[0])
                    {
                        //Enabled Maps
                        output += "enabled maps\n";
                        output += "{\n";
                        for (int i = 0; i < dmMap.size(); i++) {
                            BoolInfoWithName info = dmMap.get(i);
                            if(info.bool.get()){
                                output += "" + info.name + " 0\n";
                            }
                        }
                    }

                    output += "}\n";
                }
                output += "}\n";
            }

            if(eliminationOnOff.get()) {
                output += "Elimination\n";
            }

            if(teamDeathmatchOnOff.get()){
                output += "Team Deathmatch\n";
            }

            if(practiceRangeOnOff.get()){
                output += "Practice Range\n";
            }

            if(skirmishOnOff.get()){
                output += "Skirmish\n";
            }


            output += "}\n";
        }

        {
            if(anyExtensionsOn()) {
                output += "\n";
                output += "extensions\n";
                output += "{\n";

                for (int i = 0; i < extensionBools.size(); i++) {
                    BoolInfoWithName ext = extensionBools.get(i);

                    if(ext.bool.get()){
                        output += "" + ext.name + "\n";
                    }
                }

                output += "}\n";
            }

        }

        output += "}\n";

        return output;
    }

    /**
     *gets the amount of enabled and disabled maps on deatchmatch mode and return [number of maps on, number of maps off]
     */
    private int[] getMaps(ArrayList<BoolInfoWithName> maps){
        int[] res = new int[2];
        for (int i = 0; i < maps.size(); i++) {
            BoolInfoWithName info = maps.get(i);
            if(info.bool.get()){
                res[0] = res[0] + 1;
            }else{
                res[1] = res[1] + 1;
            }
        }
        return res;
    }

    private String getMapOutput(ArrayList<BoolInfoWithName> maps){
        String toString = "";
        int[] res = getMaps(maps);

        if(!(res[0] == 0 || res[1] == 0)){
            toString += "\n";
            if(res[0] >= res[1]){
                //Disabled Maps
                toString += "disabled maps\n";
                toString += "{\n";
                for (int i = 0; i < maps.size(); i++) {
                    BoolInfoWithName info = maps.get(i);
                    if(!info.bool.get()){
                        toString += "" + info.name + " 0\n";
                    }
                }
            }else if(res[1] > res[0])
            {
                //Enabled Maps
                toString += "enabled maps\n";
                toString += "{\n";
                for (int i = 0; i < maps.size(); i++) {
                    BoolInfoWithName info = maps.get(i);
                    if(info.bool.get()){
                        toString += "" + info.name + " 0\n";
                    }
                }
            }

            toString += "}\n";
        }

        return toString;
    }

    private boolean anyExtensionsOn(){
        boolean areOn = false;
        for (int i = 0; i < extensionBools.size(); i++) {
            if(extensionBools.get(i).bool.get()){
                areOn = true;
                break;
            }
        }

        return areOn;
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
