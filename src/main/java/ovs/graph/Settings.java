package ovs.graph;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
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

    public int[] maxSpectators = {
            2
    };

    public ImInt mapRotCurrent = new ImInt();
    public ImInt returnToLobbyCurrent = new ImInt();

    private String[] mapRotItems = {"After A Mirror Match", "After A Game", "Paused"};
    private String[] returnToLobbyItems = {"Never", "After A Game", "After A Mirror Match"};

    public ImBoolean allowInQueueOnOff = new ImBoolean(true);

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

    //Bounty Hunter Settings
    public int[] baseScoreForKillingABountyTarget = new int[]{300};
    public int[] bountyIncreasePerKillAsBountyTarget = new int[]{0};
    public int[] gameTimeBH = new int[]{10};
    public int[] scorePerKillBH = new int[]{100};
    public int[] scorePerKillAsBountyTarget = new int[]{300};
    public int[] scoreToWinBH = new int[]{20};
    public ImBoolean initRespawnOnOffBH = new ImBoolean(true);

    public ImBoolean blitzFlagLocation = new ImBoolean(false);
    public ImBoolean damageInterruptsFlagInteractions = new ImBoolean(false);
    public ImInt flagCarrierAbilitiesSelection = new ImInt();
    public String[] flagCarrierAbilitiesOptions = {"All", "None", "Restricted"};
    public float[] flagDroppedLockTime = new float[]{5.0f};
    public float[] flagPickupTime = new float[]{0.0f};
    public float[] flagReturnTime = new float[]{4.0f};
    public float[] flagScoreRespawnTime = new float[]{15.0f};
    public int[] gameTimeCTF = new int[] {10};
    public float[] respawnSpeedBuffDuration = new float[]{0.0f};
    public int[] scoreToWinCtf = new int[] {3};
    public ImBoolean teamNeedsFlagAtBaseToScore = new ImBoolean(false);


    //DeathMatch settings
    public int[] gameTimeDM = new int[] {10};
    public int[] scoreToWinDM = new int[] {20};
    public ImBoolean initRespawnOnOffDM = new ImBoolean(true);

    //Elimination Settings
    public int[] heroSelectionTime = new int[]{20};
    public int[] scoreToWinElim = new int[]{3};
    public ImInt restrictPreviouslyUsedHeroElimSelection = new ImInt();
    public String[] restrictPreviouslyUsedHeroElimOptions = {"Off", "After Round Won", "After Round Played"};
    public ImInt heroSelectionElimSelection = new ImInt();
    public String[] heroSelectionElimOptions = {"Any", "Limited", "Random", "Random Mirrored"};
    public ImInt limitedChoicePoolElimSelection = new ImInt();
    public String[] limitedChoicePoolElimOptions = {"Team Size", "Team Size +1", "Team Size +2", "Team Size +3"};
    public ImBoolean captureObjectiveTiebreakerElim = new ImBoolean(true);
    public int[] tiebreakerAfterMatchTimeElapsedElim = new int[]{105};
    public int[] timeToCapture = new int[]{3};
    public int[] drawAfterMatchTimeElapsedWithNoTiebreakerElim = new int[]{135};
    public ImBoolean revealHeroesElim = new ImBoolean(false);
    public int[] revealHeroesAfterMatchTimeElapsedElim = new int[]{75};

    //Team Deathmatch Settings
    public int[] gameTimeTDM = new int[]{10};
    public ImBoolean mercyResCounteractsKillsTDM = new ImBoolean(true);
    public int[] scoreToWinTDM = new int[]{30};
    public ImBoolean initRespawnOnOfTDM = new ImBoolean(true);
    public ImBoolean imbalancedTeamScoreToWinTDM = new ImBoolean(false);
    public int[] team1ScoreToWinTDM = new int[]{30};
    public int[] team2ScoreToWinTDM = new int[]{30};

    //Practise Range Settings
    public ImBoolean spawnTrainingBots = new ImBoolean(true);
    public int[] trainingBotRespawnTimeScaler = new int[]{100};
    public ImBoolean trainingPartner = new ImBoolean(true);

    //Skirmish Settings
    public ImInt skirmishValidControlPointsSelection = new ImInt();
    public String[] skirmishValidControlPointsOptions = {"All", "First", "Second", "Third"};

    //Maps Toggle
    public ArrayList<BoolInfoWithName> assaultMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> controlMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> escortMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> hybridMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> pushMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> bhMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> ctfMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> dmMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> elimMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> teamDmMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> skirmishMap = new ArrayList<>();
    public ArrayList<BoolInfoWithName> extensionBools = new ArrayList<>();

    public Settings(){

        assaultMap.add(new BoolInfoWithName("Hanamura", new ImBoolean(true)));
        assaultMap.add(new BoolInfoWithName("Hanamura Winter", new ImBoolean(true)));
        assaultMap.add(new BoolInfoWithName("Horizon Lunar Colony", new ImBoolean(true)));
        assaultMap.add(new BoolInfoWithName("Paris", new ImBoolean(true)));
        assaultMap.add(new BoolInfoWithName("Temple Of Anubis", new ImBoolean(true)));
        assaultMap.add(new BoolInfoWithName("Volskaya Industries", new ImBoolean(true)));

        controlMap.add(new BoolInfoWithName("Antarctic Peninsula", new ImBoolean(true)));
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

        ctfMap.add(new BoolInfoWithName("Ayutthaya", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Busan Downtown Lunar New Year", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Busan Sanctuary Lunar New Year", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Ilios Lighthouse", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Ilios Ruins", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Ilios Well", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Lijiang Control Center", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Lijiang Control Center Lunar New Year", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Lijiang Garden", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Lijiang Garden Lunar New Year", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Lijiang Night Market", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Lijiang Night Market Lunar New Year", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Nepal Sanctum", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Nepal Shrine", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Nepal Village", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Oasis City Center", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Oasis Gardens", new ImBoolean(true)));
        ctfMap.add(new BoolInfoWithName("Oasis University ", new ImBoolean(true)));

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
        dmMap.add(new BoolInfoWithName("Ilios Well", new ImBoolean(true)));
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

        //BH maps are the same maps as DM Maps
        for (int i = 0; i < dmMap.size(); i++) {
            bhMap.add(new BoolInfoWithName(dmMap.get(i).name, new ImBoolean(true)));
        }

        elimMap.add(new BoolInfoWithName("Ayutthaya", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Black Forest", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Black Forest Winter", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Castillo", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Ecopoint: Antarctica", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Ecopoint: Antarctica Winter", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Ilios Lighthouse", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Ilios Ruins", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Ilios Well", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Lijiang Control Center", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Lijiang Control Center Lunar New Year", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Lijiang Garden", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Lijiang Lunar New Year", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Lijiang Night Market", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Lijiang Night Market Lunar New Year", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Necropolis", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Nepal Sanctum", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Nepal Shrine", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Nepal Village", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Oasis City Center", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Oasis Gardens", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Oasis University", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Workshop Chamber", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Workshop Expanse", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Workshop Expanse Night", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Workshop Green Screen", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Workshop Island", new ImBoolean(true)));
        elimMap.add(new BoolInfoWithName("Workshop Island Night", new ImBoolean(true)));

        for (int i = 0; i < dmMap.size(); i++) {
            teamDmMap.add(new BoolInfoWithName(dmMap.get(i).name, new ImBoolean(true)));
        }

        skirmishMap.add(new BoolInfoWithName("Blizzard World", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Blizzard World Winter", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Busan", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Circuit Royal", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Colosseo", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Dorado", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Eichenwalde", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Eichenwalde Halloween", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Esperança", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Hanamura", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Hanamura Winter", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Havana", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Hollywood", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Hollywood Halloween", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Horizon Lunar Colony", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Ilios", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Junkertown", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("King's Row", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("King's Row Winter", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Lijiang Tower", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Lijiang Tower Lunar New Year", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Nepal", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("New Queen Street", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Numbani", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Oasis", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Paraíso", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Paris", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Rialto", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Route 66", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Shambali Monastery", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Temple of Anubis", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Volskaya Industries", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Watchpoint: Gibraltar", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Workshop Chamber", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Workshop Expanse", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Workshop Expanse Night", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Workshop Green Screen", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Workshop Island", new ImBoolean(true)));
        skirmishMap.add(new BoolInfoWithName("Workshop Island Night", new ImBoolean(true)));

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

        ImGui.separator();
        //Lobby Settings
        if(ImGui.collapsingHeader("Lobby Settings", ImGuiTreeNodeFlags.DefaultOpen)) {
            if (ImGui.button("Extension Options")) {
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

            ImGui.text("Max Spectators");
            ImGui.sameLine();
            ImGui.sliderInt("##Max Spectators", maxSpectators, 0, 12);

            ImGui.text("Map Rotation");
            ImGui.sameLine();
            ImGui.combo("##MapRotation", mapRotCurrent, mapRotItems);

            ImGui.text("Return To Lobby");
            ImGui.sameLine();
            ImGui.combo("##RetrunToLobby", returnToLobbyCurrent, returnToLobbyItems);

            ImGui.text("Allow Players Who Are In Queue");
            ImGui.sameLine();
            ImGui.checkbox("##AllowInQueue", allowInQueueOnOff);
        }

        ImGui.separator();

//        ImGui.newLine();
        if(ImGui.collapsingHeader("Modes", ImGuiTreeNodeFlags.DefaultOpen)) {
            ImGui.checkbox("##Assault", assaultOnOff);
            ImGui.sameLine();
            ImGui.text("Assault");
            ImGui.sameLine();
            if (ImGui.button("Assault Options")) {
                ImGui.openPopup("Assault_options");
            }
            ImGui.checkbox("##Control", controlOnOff);
            ImGui.sameLine();
            ImGui.text("Control");
            ImGui.sameLine();
            if (ImGui.button("Control Options")) {
                ImGui.openPopup("Control_options");
            }
            ImGui.checkbox("##Escort", escortOnOff);
            ImGui.sameLine();
            ImGui.text("Escort");
            ImGui.sameLine();
            if (ImGui.button("Escort Options")) {
                ImGui.openPopup("Escort_options");
            }
            ImGui.checkbox("##Hybrid", hybridOnOff);
            ImGui.sameLine();
            ImGui.text("Hybrid");
            ImGui.sameLine();
            if (ImGui.button("Hybrid Options")) {
                ImGui.openPopup("Hybrid_options");
            }
            ImGui.checkbox("##Push", pushOnOff);
            ImGui.sameLine();
            ImGui.text("Push");
            ImGui.sameLine();
            if (ImGui.button("Push Options")) {
                ImGui.openPopup("Push_options");
            }
            ImGui.checkbox("##BountyHunter", bountyHunterOnOff);
            ImGui.sameLine();
            ImGui.text("Bouty Hunter");
            ImGui.sameLine();
            if(ImGui.button("Bounty Hunter Options")){
                ImGui.openPopup("Bountyhunter_options");
            }
            ImGui.checkbox("##CTF", ctfOnOff);
            ImGui.sameLine();
            ImGui.text("CTF");
            ImGui.sameLine();
            if(ImGui.button("CTF Options")){
                ImGui.openPopup("Ctf_options");
            }
            ImGui.checkbox("##DeathMatch", deathmatchOnOff);
            ImGui.sameLine();
            ImGui.text("DeathMatch");
            ImGui.sameLine();
            if (ImGui.button("Deathmatch Options")) {
                ImGui.openPopup("Deathmatch_options");
            }
            ImGui.checkbox("##Elimination", eliminationOnOff);
            ImGui.sameLine();
            ImGui.text("Elimination");
            ImGui.sameLine();
            if(ImGui.button("Elimination Options")){
                ImGui.openPopup("Elimination_options");
            }
            ImGui.checkbox("##TeamDeathMatch", teamDeathmatchOnOff);
            ImGui.sameLine();
            ImGui.text("Team DeathMatch");
            ImGui.sameLine();
            if(ImGui.button("Team DeathMatch Options")){
                ImGui.openPopup("Teamdeathmatch_options");
            }
            ImGui.checkbox("##PracticeRange", practiceRangeOnOff);
            ImGui.sameLine();
            ImGui.text("Practice Range");
            ImGui.sameLine();
            if(ImGui.button("Practice Range Options")){
                ImGui.openPopup("Practicerange_options");
            }
            ImGui.checkbox("##Skirmish", skirmishOnOff);
            ImGui.sameLine();
            ImGui.text("Skirmish");
                ImGui.sameLine();
            if(ImGui.button("Skirmish Options")){
                ImGui.openPopup("Skirmish_options");
            }
        }

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
                for(BoolInfoWithName biwn : pushMap) {
                    ImGui.checkbox("##" + biwn.name, biwn.bool);
                    ImGui.sameLine();
                    ImGui.text(biwn.name);
                }

                ImGui.endPopup();
            }
        }

        if(ImGui.isPopupOpen("Bountyhunter_options"))
        {
            if(ImGui.beginPopup("Bountyhunter_options")){
                ImGui.sliderInt("##baseScoreKillingTarget", baseScoreForKillingABountyTarget, 0, 1000);
                ImGui.sameLine();
                ImGui.text("Base Score For Killing A Bounty Target");
                ImGui.sliderInt("##bountyIncrease", bountyIncreasePerKillAsBountyTarget, 0, 1000);
                ImGui.sameLine();
                ImGui.text("Bounty Increase Per Kill As Bounty Target");
                ImGui.sliderInt("##ScorePerKillBH", scorePerKillBH, 0, 1000);
                ImGui.sameLine();
                ImGui.text("Score Per Kill");
                ImGui.sliderInt("##ScorePerKillBHAsTarget", scorePerKillAsBountyTarget, 0, 1000);
                ImGui.sameLine();
                ImGui.text("Score Per Kill As Bounty Target");
                ImGui.sliderInt("##gameTimeBH", gameTimeBH, 5, 15);
                ImGui.sameLine();
                ImGui.text("Game Time in Minutes");
                ImGui.sliderInt("##scoreToWinBH", scoreToWinBH, 1, 5000);
                ImGui.sameLine();
                ImGui.text("Score To Win");
                ImGui.checkbox("##bhInitRespawn", initRespawnOnOffBH);
                ImGui.sameLine();
                ImGui.text("Self Initialize Respawn");

                showMapToggle(bhMap);
                ImGui.endPopup();
            }
        }

        if(ImGui.isPopupOpen("Ctf_options"))
        {
            if(ImGui.beginPopup("Ctf_options")){

                ImGui.checkbox("##BlitzFlagLocation", blitzFlagLocation);
                ImGui.sameLine();
                ImGui.text("Blitz Flag Location");
                ImGui.checkbox("##DamageInterruptsFlagInteraction", damageInterruptsFlagInteractions);
                ImGui.sameLine();
                ImGui.text("Damage Interrupts Flag Interactions");
                ImGui.combo("##FlagCarrierAbilities", flagCarrierAbilitiesSelection, flagCarrierAbilitiesOptions);
                ImGui.sameLine();
                ImGui.text("Flag Carrier Abilities");
                ImGui.sliderFloat("##FlagDroppedLockTime", flagDroppedLockTime, 0.0f, 10.0f);
                ImGui.sameLine();
                ImGui.text("Flag Dropped Lock Time");
                ImGui.sliderFloat("##FlagPickupTime", flagPickupTime, 0.0f, 5.0f);
                ImGui.sameLine();
                ImGui.text("Flag Pickup Time");
                ImGui.sliderFloat("##FlagReturnTime", flagReturnTime, 0.0f, 5.0f);
                ImGui.sameLine();
                ImGui.text("Flag Return Time");
                ImGui.sliderFloat("##FlagScoreRespawnTime", flagScoreRespawnTime, 0.0f, 20.0f);
                ImGui.sameLine();
                ImGui.text("Flag Score Respawn Time");
                ImGui.sliderInt("##GameTimeCtf", gameTimeCTF, 5, 15);
                ImGui.sameLine();
                ImGui.text("Game Time");
                ImGui.sliderFloat("##RespawnSpeedBuffDuration", respawnSpeedBuffDuration, 0, 60);
                ImGui.sameLine();
                ImGui.text("Respawn Speed Buff Duration");
                ImGui.sliderInt("##ScoreToWinCtf", scoreToWinCtf, 1, 9);
                ImGui.sameLine();
                ImGui.text("Score To Win");
                ImGui.checkbox("##TeamNeedsFlagAtBaseToScore", teamNeedsFlagAtBaseToScore);
                ImGui.sameLine();
                ImGui.text("Team Needs Flag At Base To Score");

                showMapToggle(ctfMap);
                ImGui.endPopup();
            }
        }

        //Death Match options popup
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

        //Elimination Options
        if(ImGui.isPopupOpen("Elimination_options")){
            if(ImGui.beginPopup("Elimination_options")){
                ImGui.sliderInt("##HeroSelectionTime", heroSelectionTime, 20, 60);
                ImGui.sameLine();
                ImGui.text("Hero Selection Time");
                ImGui.sliderInt("##ScoreToWinElim", scoreToWinElim, 1, 9);
                ImGui.sameLine();
                ImGui.text("Score To Win");
                ImGui.combo("##RescrictPrevUsedHeroElim", restrictPreviouslyUsedHeroElimSelection, restrictPreviouslyUsedHeroElimOptions);
                ImGui.sameLine();
                ImGui.text("Restrict Previously Used Hero");
                ImGui.combo("##HeroSelectionElim", heroSelectionElimSelection, heroSelectionElimOptions);
                ImGui.sameLine();
                ImGui.text("Hero Selection");
                ImGui.combo("##LimitedChoicePoolElim", limitedChoicePoolElimSelection, limitedChoicePoolElimOptions);
                ImGui.sameLine();
                ImGui.text("Limited Choice Pool");
                ImGui.checkbox("##CaptureObjectiveTiebreakerElim", captureObjectiveTiebreakerElim);
                ImGui.sameLine();
                ImGui.text("Capture Objective Tiebreaker");
                ImGui.sliderInt("##TiebreakerAfterMatchTimeElapsedElim", tiebreakerAfterMatchTimeElapsedElim, 30, 300);
                ImGui.sameLine();
                ImGui.text("Tiebreaker After Match Time Elapsed");
                ImGui.sliderInt("##TimeToCaptureElim", timeToCapture, 1, 7);
                ImGui.sameLine();
                ImGui.text("Time To Capture");
                ImGui.sliderInt("##DrawAfterMatchTimeElapsedWithNoTiebreakerElim", drawAfterMatchTimeElapsedWithNoTiebreakerElim, 60, 300);
                ImGui.sameLine();
                ImGui.text("Draw After Match Time Elapsed With No Tiebreaker");
                ImGui.checkbox("##RevealHeroesElim", revealHeroesElim);
                ImGui.sameLine();
                ImGui.text("Reveal Heroes");
                ImGui.sliderInt("##RevealHeroesAfterMatchTimeElapsedElim", revealHeroesAfterMatchTimeElapsedElim, 0, 180);

                showMapToggle(elimMap);

                ImGui.endPopup();
            }
        }

        if(ImGui.isPopupOpen("Teamdeathmatch_options")){
            if(ImGui.beginPopup("Teamdeathmatch_options")){
                ImGui.sliderInt("##gameTimeTDM", gameTimeTDM, 5, 15);
                ImGui.sameLine();
                ImGui.text("Game Time");
                ImGui.checkbox("##mercyResCounteractsKillsTDM", mercyResCounteractsKillsTDM);
                ImGui.sameLine();
                ImGui.text("Mercy Resurrect Counteracts Kills");
                ImGui.sliderInt("##scoreToWinTDM", scoreToWinTDM, 1, 200);
                ImGui.sameLine();
                ImGui.text("Score To Win");
                ImGui.checkbox("##selfInitRespawnTDM", initRespawnOnOfTDM);
                ImGui.sameLine();
                ImGui.text("Self Initiated Respawn");
                ImGui.checkbox("##imbalancedTeamScoreToWinTDM", imbalancedTeamScoreToWinTDM);
                ImGui.sameLine();
                ImGui.text("Imbalanced Team Score To Win");
                ImGui.sliderInt("##team1ScoreToWinTDM", team1ScoreToWinTDM, 1, 200);
                ImGui.sameLine();
                ImGui.text("Team 1 Score To Win");
                ImGui.sliderInt("##team2ScoreToWinTDM", team2ScoreToWinTDM, 1, 200);
                ImGui.sameLine();
                ImGui.text("Team 2 Score To Win");

                showMapToggle(teamDmMap);

                ImGui.endPopup();
            }
        }

        if(ImGui.isPopupOpen("Practicerange_options")){
            if(ImGui.beginPopup("Practicerange_options")){
                ImGui.checkbox("##SpawnTrainingBots", spawnTrainingBots);
                ImGui.sameLine();
                ImGui.text("Spawn Training Bots");
                ImGui.sliderInt("##trainingBotRespawnTimeScaler", trainingBotRespawnTimeScaler, 10, 500);
                ImGui.sameLine();
                ImGui.text("Training Bot Respawn Time Scaler");
                ImGui.checkbox("##trainingPartner", trainingPartner);
                ImGui.sameLine();
                ImGui.text("Training Partner");

                ImGui.endPopup();
            }
        }

        if(ImGui.isPopupOpen("Skirmish_options")){
            if(ImGui.beginPopup("Skirmish_options")){
                ImGui.combo("##LimitValidControlsPointsSkirmish", skirmishValidControlPointsSelection, skirmishValidControlPointsOptions);
                ImGui.sameLine();
                ImGui.text("Limit Valid Control Points");

                showMapToggle(skirmishMap);

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

    private void showMapToggle(ArrayList<BoolInfoWithName> mapList){
        if(ImGui.button("All")) {
            for (int i = 0; i < mapList.size(); i++) {
                BoolInfoWithName bool = mapList.get(i);
                bool.bool.set(true);
            }
        }
        ImGui.sameLine();
        if(ImGui.button("None")){
            for (int i = 0; i < mapList.size(); i++) {
                BoolInfoWithName bool = mapList.get(i);
                bool.bool.set(false);
            }
        }
        ImGui.separator();
        for(BoolInfoWithName biwn : mapList) {
            ImGui.checkbox("##" + biwn.name, biwn.bool);
            ImGui.sameLine();
            ImGui.text(biwn.name);
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
            output += "Map Rotation: " + mapRotItems[mapRotCurrent.get()] + "\n";
            output += "Max Spectators:" + maxSpectators[0] + "\n";
            output += "Return To Lobby: " + returnToLobbyItems[returnToLobbyCurrent.get()] + "\n";
            output += "Allow Players Who Are In Queue:" + (allowInQueueOnOff.get() ? "Yes" : "No") + "\n";
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
                output += "{\n";

                output += "Blitz Flag Locations: " + (blitzFlagLocation.get() ? "On" : "Off") + "\n";
                output += "Damage Interrupts Flag Interaction: " + (damageInterruptsFlagInteractions.get() ? "On" : "Off") + "\n";
                output += "Flag Dropped Lock Time:" + flagDroppedLockTime[0] + "\n";
                output += "Flag Pickup Time:" + flagPickupTime[0] + "\n";
                output += "Flag Return Time:" + flagReturnTime[0] + "\n";
                output += "Flag Score Respawn Time:" + flagScoreRespawnTime[0] + "\n";
                output += "Game Length Minutes:" + gameTimeCTF[0] + "\n";
                output += "Respawn Speed Buff Duration:" + respawnSpeedBuffDuration[0] + "\n";
                output += "Score To Win:" + scoreToWinCtf[0] + "\n";
                output += "Team Needs Flag At Base To Score:" + (teamNeedsFlagAtBaseToScore.get() ? "On" : "Off") + "\n";

                output += getMapOutput(ctfMap);

                output += "}\n";
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
                output += "{\n";

                output += "Capture Objective Tiebreaker: " + (captureObjectiveTiebreakerElim.get() ? "On" : "Off") + "\n";
                output += "Draw After Match Time Elapsed With No Tiebreaker: " + drawAfterMatchTimeElapsedWithNoTiebreakerElim[0] + "\n";
                output += "Hero Selection: " + heroSelectionElimOptions[heroSelectionElimSelection.get()] + "\n";
                output += "Hero Selection Time: " + heroSelectionTime[0] + "\n";
                output += "Limited Choice Pool: " + limitedChoicePoolElimOptions[limitedChoicePoolElimSelection.get()] + "\n";
                output += "Restrict Previously Used Heroes: " + restrictPreviouslyUsedHeroElimOptions[restrictPreviouslyUsedHeroElimSelection.get()] + "\n";
                output += "Reveal Heroes: " + (revealHeroesElim.get() ? "On" : "Off") + "\n";
                output += "Reveal Heroes After Match Time Elapsed: " + revealHeroesAfterMatchTimeElapsedElim[0] + "\n";
                output += "Score To Win: " + scoreToWinElim[0] + "\n";
                output += "Tiebreaker After Match Time Elapsed: " + tiebreakerAfterMatchTimeElapsedElim[0] + "\n";
                output += "Time To Capture: " + timeToCapture[0] + "\n";

                output += getMapOutput(elimMap);

                output += "}\n";
            }

            if(teamDeathmatchOnOff.get()){
                output += "Team Deathmatch\n";

                output += "{\n";

                output += "Game Length In Minutes: " + gameTimeTDM[0] + "\n";
                output += "Imbalanced Team Score To Win: " + (imbalancedTeamScoreToWinTDM.get() ? "On" : "Off") + "\n";
                output += "Mercy Resurrect Counteracts Kills: " + (mercyResCounteractsKillsTDM.get() ? "On" : "Off") + "\n";
                output += "Score To Win: " + scoreToWinTDM[0] + "\n";
                output += "Self Initiated Respawn: " + (initRespawnOnOfTDM.get() ? "On" : "Off") + "\n";
                output += "Team 1 Score To Win: " + team1ScoreToWinTDM[0] + "\n";
                output += "Team 2 Score To Win: " + team2ScoreToWinTDM[0] + "\n";

                output += getMapOutput(teamDmMap);
                output += "}\n";
            }

            if(practiceRangeOnOff.get()){
                output += "Practice Range\n";

                output += "{\n";

                output += "Spawn Training Bots: " + (spawnTrainingBots.get() ? "On" : "Off") + "\n";
                output += "Training Bot Respawn Time Scalar: " + trainingBotRespawnTimeScaler[0] + "%\n";
                output += "Training Partner: " + (trainingPartner.get() ? "On" : "Off") + "\n";

                output += "}\n";
            }

            if(skirmishOnOff.get()){
                output += "Skirmish\n";

                output += "{\n";

                output += "Limit Valid Control Points: " + skirmishValidControlPointsOptions[skirmishValidControlPointsSelection.get()] + "\n";

                output += getMapOutput(skirmishMap);

                output += "}\n";
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
