package ovs.graph.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImVec2;
import imgui.extension.nodeditor.NodeEditor;
import ovs.Global;
import ovs.TaskSchedule;
import ovs.graph.Graph;
import ovs.graph.Settings;
import ovs.graph.Task;
import ovs.graph.Variable;
import ovs.graph.node.Node;
import ovs.graph.pin.Pin;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class GraphSaver {

    private transient GraphSave graphSave = new GraphSave();
    private static StringBuilder sb = new StringBuilder();

    String dir = Global.SCRIPTS_DIR;

    public boolean save(String fileName, Settings settings, Graph graph){
        validateDirExists(dir);
        validateDirExists(dir + File.separator + fileName);


        File file = new File(dir + File.separator + fileName + File.separator + "script.json");
        File backup = new File(dir + File.separator + fileName + File.separator + "backup_script.json");
        try {
            Global.createBackup(file, backup);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        graphSave.nodeSaves.clear();
        graphSave.globalVariables.clear();
        graphSave.playerVariables.clear();
        graphSave.subroutines.clear();
        graphSave.saveSettings.extensionToggle.clear();
        graphSave.saveSettings.assaultSettings.MapToggle.clear();
        graphSave.saveSettings.controlSettings.MapToggle.clear();
        graphSave.saveSettings.escortSettings.MapToggle.clear();
        graphSave.saveSettings.hybridSettings.MapToggle.clear();
        graphSave.saveSettings.pushSettings.MapToggle.clear();
        graphSave.saveSettings.bhSettings.MapToggle.clear();
        graphSave.saveSettings.ctfSettings.MapToggle.clear();
        graphSave.saveSettings.dmSettings.MapToggle.clear();
        graphSave.saveSettings.elimSettings.MapToggle.clear();
        graphSave.saveSettings.teamDmSettings.MapToggle.clear();
        graphSave.saveSettings.skirmishSettings.MapToggle.clear();

        ////Lobby Settings
        graphSave.saveSettings.modeName = settings.modeName.get();
        graphSave.saveSettings.description = settings.description.get();
        graphSave.saveSettings.maxT1Players = settings.maxT1Players[0];
        graphSave.saveSettings.maxT2Players = settings.maxT2Players[0];
        graphSave.saveSettings.maxFFAPlayers = settings.maxFFAPlayers[0];
        graphSave.saveSettings.mapRotation = settings.mapRotCurrent.get();
        graphSave.saveSettings.returnToLobby = settings.returnToLobbyCurrent.get();

        graphSave.saveSettings.assaultMode = settings.assaultOnOff.get();
        graphSave.saveSettings.controlMode = settings.controlOnOff.get();
        graphSave.saveSettings.escortMode = settings.escortOnOff.get();
        graphSave.saveSettings.hybridMode = settings.hybridOnOff.get();
        graphSave.saveSettings.pushMode = settings.pushOnOff.get();
        graphSave.saveSettings.bountyHunterMode = settings.bountyHunterOnOff.get();
        graphSave.saveSettings.ctfMode = settings.ctfOnOff.get();
        graphSave.saveSettings.deathmatchMode = settings.deathmatchOnOff.get();
        graphSave.saveSettings.eliminationMode = settings.eliminationOnOff.get();
        graphSave.saveSettings.teamDeathmatchMode = settings.teamDeathmatchOnOff.get();
        graphSave.saveSettings.practiceRangeMode = settings.practiceRangeOnOff.get();
        graphSave.saveSettings.skirmishMode = settings.skirmishOnOff.get();

        for (int i = 0; i < settings.extensionBools.size(); i++) {
            Settings.BoolInfoWithName info = settings.extensionBools.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.extensionToggle.add(saveInfo);
        }

        //TODO save other game mode settings

        //Assault Options
        graphSave.saveSettings.assaultSettings.speedModifier = settings.assaultSpeedModifier[0];
        graphSave.saveSettings.assaultSettings.compRules = settings.assaultCompRulesOnOff.get();
        for (int i = 0; i < settings.assaultMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.assaultMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.assaultSettings.MapToggle.add(saveInfo);
        }

        //Control Options
        graphSave.saveSettings.controlSettings.speedModifier = settings.controlSpeedModifier[0];
        graphSave.saveSettings.controlSettings.compRules = settings.controlCompetitiveRules.get();
        graphSave.saveSettings.controlSettings.validPoints = settings.controlValidControlPointsSelection.get();
        graphSave.saveSettings.controlSettings.scoreToWin = settings.controlScoreToWin[0];
        graphSave.saveSettings.controlSettings.scoringSpeedModifier = settings.controlSpeedModifier[0];
        for (int i = 0; i < settings.controlMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.controlMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.controlSettings.MapToggle.add(saveInfo);
        }

        //Escort Options
        graphSave.saveSettings.escortSettings.compRules = settings.escortCompRulesOnOff.get();
        graphSave.saveSettings.escortSettings.speedModifier = settings.escortSpeedModifier[0];
        for (int i = 0; i < settings.escortMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.escortMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.escortSettings.MapToggle.add(saveInfo);
        }

        //Hybrid Options
        graphSave.saveSettings.hybridSettings.speedModifier = settings.hybridSpeedModifier[0];
        graphSave.saveSettings.hybridSettings.compRules = settings.hybridCompRulesOnOff.get();
        graphSave.saveSettings.hybridSettings.payloadSpeedModifier = settings.hybridPayloadSpeedModifier[0];
        for (int i = 0; i < settings.hybridMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.hybridMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.hybridSettings.MapToggle.add(saveInfo);
        }

        //Push Options
        graphSave.saveSettings.pushSettings.compRules = settings.pushCompRulesOnOff.get();
        graphSave.saveSettings.pushSettings.pushSpeedModifier = settings.pushPushSpeedModifier[0];
        graphSave.saveSettings.pushSettings.walkSpeedModifier = settings.pushWalkSpeedModifier[0];
        for (int i = 0; i < settings.pushMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.pushMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.pushSettings.MapToggle.add(saveInfo);
        }

        //BountyHunter Options
        graphSave.saveSettings.bhSettings.baseScoreForKillingBountyTarget = settings.baseScoreForKillingABountyTarget[0];
        graphSave.saveSettings.bhSettings.bountyIncreasePerKillAsBountyTarget = settings.bountyIncreasePerKillAsBountyTarget[0];
        graphSave.saveSettings.bhSettings.scorePerKillBH = settings.scorePerKillBH[0];
        graphSave.saveSettings.bhSettings.scorePerKillAsBountyTarget = settings.scorePerKillAsBountyTarget[0];
        graphSave.saveSettings.bhSettings.gameTimeBH = settings.gameTimeBH[0];
        graphSave.saveSettings.bhSettings.scoreToWinBH = settings.scoreToWinBH[0];
        graphSave.saveSettings.bhSettings.initRespawnBH = settings.initRespawnOnOffBH.get();
        for (int i = 0; i < settings.bhMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.bhMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.bhSettings.MapToggle.add(saveInfo);
        }

        //Ctf Options
        graphSave.saveSettings.ctfSettings.blitzFlagLocations = settings.blitzFlagLocation.get();
        graphSave.saveSettings.ctfSettings.damageInterruptsFlagInteractions = settings.damageInterruptsFlagInteractions.get();
        graphSave.saveSettings.ctfSettings.flagCarrierAbilities = settings.flagCarrierAbilitiesSelection.get();
        graphSave.saveSettings.ctfSettings.flagDroppedLockTime = settings.flagDroppedLockTime[0];
        graphSave.saveSettings.ctfSettings.flagPickupTime = settings.flagPickupTime[0];
        graphSave.saveSettings.ctfSettings.flagReturnTime = settings.flagReturnTime[0];
        graphSave.saveSettings.ctfSettings.flagScoreRespawnTime = settings.flagScoreRespawnTime[0];
        graphSave.saveSettings.ctfSettings.gameTimeCtf = settings.gameTimeCTF[0];
        graphSave.saveSettings.ctfSettings.respawnSpeedBuffDuration = settings.respawnSpeedBuffDuration[0];
        graphSave.saveSettings.ctfSettings.scoreToWinCtf = settings.scoreToWinCtf[0];
        graphSave.saveSettings.ctfSettings.teamNeedsFlagAtBaseToScore = settings.teamNeedsFlagAtBaseToScore.get();
        for (int i = 0; i < settings.ctfMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.ctfMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.ctfSettings.MapToggle.add(saveInfo);
        }

        //DeathMatch Options
        graphSave.saveSettings.dmSettings.GameLenthDM = settings.gameTimeDM[0];
        graphSave.saveSettings.dmSettings.scoreToWinDM = settings.scoreToWinDM[0];
        graphSave.saveSettings.dmSettings.initRespawnDM = settings.initRespawnOnOffDM.get();
        for (int i = 0; i < settings.dmMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.dmMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.dmSettings.MapToggle.add(saveInfo);
        }

        //Elimination Options
        graphSave.saveSettings.elimSettings.heroSelectioinTime = settings.heroSelectionTime[0];
        graphSave.saveSettings.elimSettings.scoreToWinElim = settings.scoreToWinElim[0];
        graphSave.saveSettings.elimSettings.restrictPreviouslyUsedHero = settings.restrictPreviouslyUsedHeroElimSelection.get();
        graphSave.saveSettings.elimSettings.heroSelection = settings.heroSelectionElimSelection.get();
        graphSave.saveSettings.elimSettings.limitChoicePool = settings.limitedChoicePoolElimSelection.get();
        graphSave.saveSettings.elimSettings.captureObjectiveTiebreaker = settings.captureObjectiveTiebreakerElim.get();
        graphSave.saveSettings.elimSettings.tiebreakerAfterMatchTimeElapsed = settings.tiebreakerAfterMatchTimeElapsedElim[0];
        graphSave.saveSettings.elimSettings.timeToCapture = settings.timeToCapture[0];
        graphSave.saveSettings.elimSettings.drawAfterMatchTimeElapsedWithNoTiebreaker = settings.drawAfterMatchTimeElapsedWithNoTiebreakerElim[0];
        graphSave.saveSettings.elimSettings.revealHeroes = settings.revealHeroesElim.get();
        graphSave.saveSettings.elimSettings.revealHeroesAfterMatchTimeElapsed = settings.revealHeroesAfterMatchTimeElapsedElim[0];
        for (int i = 0; i < settings.elimMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.elimMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.elimSettings.MapToggle.add(saveInfo);
        }

        //Team Deathmatch Options
        graphSave.saveSettings.teamDmSettings.gameTimeTDM = settings.gameTimeTDM[0];
        graphSave.saveSettings.teamDmSettings.mercyResCounteractsKills = settings.mercyResCounteractsKillsTDM.get();
        graphSave.saveSettings.teamDmSettings.scoreToWinTDM = settings.scoreToWinTDM[0];
        graphSave.saveSettings.teamDmSettings.initRespawnTDM = settings.initRespawnOnOfTDM.get();
        graphSave.saveSettings.teamDmSettings.imbalancedTeamScoreToWin = settings.imbalancedTeamScoreToWinTDM.get();
        graphSave.saveSettings.teamDmSettings.team1ScoreToWin = settings.team1ScoreToWinTDM[0];
        graphSave.saveSettings.teamDmSettings.team2ScoreToWin = settings.team2ScoreToWinTDM[0];
        for (int i = 0; i < settings.teamDmMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.teamDmMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.teamDmSettings.MapToggle.add(saveInfo);
        }

        //Practice Range Options
        graphSave.saveSettings.practiceRangeSettings.spawnTrainingBots = settings.spawnTrainingBots.get();
        graphSave.saveSettings.practiceRangeSettings.trainingBotRespawnTimeScaler = settings.trainingBotRespawnTimeScaler[0];
        graphSave.saveSettings.practiceRangeSettings.trainingPartner = settings.trainingPartner.get();

        //Skirmish Options
        graphSave.saveSettings.skirmishSettings.limitValidControlPoints = settings.skirmishValidControlPointsSelection.get();
        for (int i = 0; i < settings.skirmishMap.size(); i++) {
            Settings.BoolInfoWithName info = settings.skirmishMap.get(i);
            BoolInfo saveInfo = new BoolInfo();
            saveInfo.name = info.name;
            saveInfo.value = info.bool.get();
            graphSave.saveSettings.skirmishSettings.MapToggle.add(saveInfo);
        }

//        for(Variable var : graph.globalVariables.getList()){
        for (int i = 0; i < graph.globalVariables.size(); i++){
//            graphSave.globalVariables.add(var.ID + ":" + var.name);
            Variable var = graph.globalVariables.get(i);
//            graphSave.globalVariables.add(var.ID + ":" + var.name);
            graphSave.globalVariables.add(i + ":" + var.name);
        }

//        for(Variable var : graph.playerVariables.getList()){
        for (int i = 0; i < graph.playerVariables.size(); i++){
            Variable var = graph.playerVariables.get(i);
//            graphSave.playerVariables.add(var.ID + ":" + var.name);
            graphSave.playerVariables.add(i + ":" + var.name);
        }

        for (int i = 0; i < graph.subroutines.size(); i++) {
            graphSave.subroutines.add(i + ":" + graph.subroutines.get(i));
        }


        for(Node node : graph.getNodes().values()){
            String className = node.getClass().getName();
            //get the nodes position in the graph
            ImVec2 pos = new ImVec2();
            NodeEditor.getNodePosition(node.getID(), pos);

            NodeSave save = new NodeSave();
            save.className = className;
            save.nodeName = node.getName();
            save.x = pos.x;
            save.y = pos.y;

            for(String extraData : node.getExtraSaveData()){
                save.extraData.add(extraData);
            }

            for(Pin inputs : node.inputPins){
                PinSave pinSave = new PinSave();
                pinSave.ID = inputs.getID();
                pinSave.type = inputs.getClass().getName();
//                pinSave.connectedTo = inputs.connectedTo;
                pinSave.canDelete = inputs.isCanDelete();

                pinSave.connections.clear();

                for (int i = 0; i < inputs.connectedToList.size(); i++) {
                    pinSave.connections.add(inputs.connectedToList.get(i));
                }

                if(inputs.getData() != null){
                    if(inputs.getData().getValue() != null){
                        pinSave.value = inputs.getData().getValue().toString();
                    }
                }

                save.inputPins.add(pinSave);
            }

            for(Pin outputs : node.outputPins){
                PinSave pinSave = new PinSave();
                pinSave.ID = outputs.getID();
                pinSave.type = outputs.getClass().getName();
                pinSave.canDelete = outputs.isCanDelete();
//                pinSave.connectedTo = outputs.connectedTo;

                pinSave.connections.clear();
                for (int i = 0; i < outputs.connectedToList.size(); i++) {
                    pinSave.connections.add(outputs.connectedToList.get(i));
                }
                save.outputPins.add(pinSave);
            }

            graphSave.nodeSaves.add(save);
        }

        try{
            Gson json = new GsonBuilder().setPrettyPrinting().create();
            String output = json.toJson(graphSave);

            PrintWriter pw = new PrintWriter(file);
            pw.write(output);
            pw.flush();
            pw.close();
            return true;
        }catch (FileNotFoundException e){
            e.printStackTrace();
            return false;
        }
    }

    private void validateDirExists(String directory) {
        File file = new File(directory);
        if(!file.exists()){
            file.mkdir();
        }
    }

    public Graph load(String fileName, Settings settings){
        try {
            File file = new File(dir + File.separator + fileName + File.separator + "script.json");
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            String content = "";
            while ((line = br.readLine()) != null) {
                content += line;
            }

            br.close();

            return loadFromString(content, settings);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Graph loadFromString(String content, Settings settings){
        try {
            Gson json = new GsonBuilder().setPrettyPrinting().create();

            GraphSave gs = json.fromJson(content, GraphSave.class);

            Node[] loaded = new Node[gs.nodeSaves.size()];

            //Load settings
            //Lobby Settings
            settings.modeName.set(gs.saveSettings.modeName);
            settings.description.set(gs.saveSettings.description);
            settings.maxT1Players[0] = (gs.saveSettings.maxT1Players);
            settings.maxT2Players[0] = (gs.saveSettings.maxT2Players);
            settings.maxFFAPlayers[0] = (gs.saveSettings.maxFFAPlayers);
            settings.mapRotCurrent.set(gs.saveSettings.mapRotation);
            settings.returnToLobbyCurrent.set(gs.saveSettings.returnToLobby);
            settings.assaultOnOff.set(gs.saveSettings.assaultMode);
            settings.controlOnOff.set(gs.saveSettings.controlMode);
            settings.escortOnOff.set(gs.saveSettings.escortMode);
            settings.hybridOnOff.set(gs.saveSettings.hybridMode);
            settings.pushOnOff.set(gs.saveSettings.pushMode);
            settings.bountyHunterOnOff.set(gs.saveSettings.bountyHunterMode);
            settings.ctfOnOff.set(gs.saveSettings.ctfMode);
            settings.deathmatchOnOff.set(gs.saveSettings.deathmatchMode);
            settings.eliminationOnOff.set(gs.saveSettings.eliminationMode);
            settings.teamDeathmatchOnOff.set(gs.saveSettings.teamDeathmatchMode);
            settings.practiceRangeOnOff.set(gs.saveSettings.practiceRangeMode);
            settings.skirmishOnOff.set(gs.saveSettings.skirmishMode);

            for (int i = 0; i < gs.saveSettings.extensionToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.extensionToggle.get(i);

                for (int j = 0; j < settings.extensionBools.size(); j++) {
                    Settings.BoolInfoWithName extInfo = settings.extensionBools.get(j);
                    if (info.name.equals(extInfo.name)) {
                        extInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Assault Options
            settings.assaultSpeedModifier[0] = gs.saveSettings.assaultSettings.speedModifier;
            settings.assaultCompRulesOnOff.set(gs.saveSettings.assaultSettings.compRules);
            for (int i = 0; i < gs.saveSettings.assaultSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.assaultSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.assaultMap.size(); j++) {
                    Settings.BoolInfoWithName mapInfo = settings.assaultMap.get(j);
                    if (info.name.equals(mapInfo.name)) {
                        mapInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Control Options
            settings.controlSpeedModifier[0] = gs.saveSettings.controlSettings.speedModifier;
            settings.controlCompetitiveRules.set(gs.saveSettings.controlSettings.compRules);
            settings.controlValidControlPointsSelection.set(gs.saveSettings.controlSettings.validPoints);
            settings.controlScoreToWin[0] = gs.saveSettings.controlSettings.scoreToWin;
            settings.controlScoringSpeedModifier[0] = gs.saveSettings.controlSettings.scoringSpeedModifier;
            for (int i = 0; i < gs.saveSettings.controlSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.controlSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.controlMap.size(); j++) {
                    Settings.BoolInfoWithName mapInfo = settings.controlMap.get(j);
                    if (info.name.equals(mapInfo.name)) {
                        mapInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Escort Options
            settings.escortCompRulesOnOff.set(gs.saveSettings.escortSettings.compRules);
            settings.escortSpeedModifier[0] = gs.saveSettings.escortSettings.speedModifier;
            for (int i = 0; i < gs.saveSettings.escortSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.escortSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.escortMap.size(); j++) {
                    Settings.BoolInfoWithName mapInfo = settings.escortMap.get(j);
                    if (info.name.equals(mapInfo.name)) {
                        mapInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Hybrid Options
            settings.hybridSpeedModifier[0] = gs.saveSettings.hybridSettings.speedModifier;
            settings.hybridCompRulesOnOff.set(gs.saveSettings.hybridSettings.compRules);
            settings.hybridPayloadSpeedModifier[0] = gs.saveSettings.hybridSettings.payloadSpeedModifier;
            for (int i = 0; i < gs.saveSettings.hybridSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.hybridSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.hybridMap.size(); j++) {
                    Settings.BoolInfoWithName mapInfo = settings.hybridMap.get(j);
                    if (info.name.equals(mapInfo.name)) {
                        mapInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Push Options
            settings.pushCompRulesOnOff.set(gs.saveSettings.pushSettings.compRules);
            settings.pushPushSpeedModifier[0] = gs.saveSettings.pushSettings.pushSpeedModifier;
            settings.pushWalkSpeedModifier[0] = gs.saveSettings.pushSettings.walkSpeedModifier;
            for (int i = 0; i < gs.saveSettings.pushSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.pushSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.pushMap.size(); j++) {
                    Settings.BoolInfoWithName mapInfo = settings.pushMap.get(j);
                    if (info.name.equals(mapInfo.name)) {
                        mapInfo.bool.set(info.value);
                        break;
                    }
                }
            }

//            Bounty Hunter Options
            settings.baseScoreForKillingABountyTarget[0] = gs.saveSettings.bhSettings.baseScoreForKillingBountyTarget;
            settings.bountyIncreasePerKillAsBountyTarget[0] = gs.saveSettings.bhSettings.bountyIncreasePerKillAsBountyTarget;
            settings.scorePerKillBH[0] = gs.saveSettings.bhSettings.scorePerKillBH;
            settings.scorePerKillAsBountyTarget[0] = gs.saveSettings.bhSettings.scorePerKillAsBountyTarget;
            settings.gameTimeBH[0] = gs.saveSettings.bhSettings.gameTimeBH;
            settings.scoreToWinBH[0] = gs.saveSettings.bhSettings.scoreToWinBH;
            settings.initRespawnOnOffBH.set(gs.saveSettings.bhSettings.initRespawnBH);
            for (int i = 0; i < gs.saveSettings.bhSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.bhSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.bhMap.size(); j++) {
                    Settings.BoolInfoWithName bhInfo = settings.bhMap.get(j);
                    if (info.name.equals(bhInfo.name)) {
                        bhInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Ctf Options
            settings.blitzFlagLocation.set(gs.saveSettings.ctfSettings.blitzFlagLocations);
            settings.damageInterruptsFlagInteractions.set(gs.saveSettings.ctfSettings.damageInterruptsFlagInteractions);
            settings.flagCarrierAbilitiesSelection.set(gs.saveSettings.ctfSettings.flagCarrierAbilities);
            settings.flagDroppedLockTime[0] = gs.saveSettings.ctfSettings.flagDroppedLockTime;
            settings.flagPickupTime[0] = gs.saveSettings.ctfSettings.flagPickupTime;
            settings.flagReturnTime[0] = gs.saveSettings.ctfSettings.flagReturnTime;
            settings.flagScoreRespawnTime[0] = gs.saveSettings.ctfSettings.flagScoreRespawnTime;
            settings.gameTimeCTF[0] = gs.saveSettings.ctfSettings.gameTimeCtf;
            settings.respawnSpeedBuffDuration[0] = gs.saveSettings.ctfSettings.respawnSpeedBuffDuration;
            settings.scoreToWinCtf[0] = gs.saveSettings.ctfSettings.scoreToWinCtf;
            settings.teamNeedsFlagAtBaseToScore.set(gs.saveSettings.ctfSettings.teamNeedsFlagAtBaseToScore);
            for (int i = 0; i < gs.saveSettings.ctfSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.ctfSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.ctfMap.size(); j++) {
                    Settings.BoolInfoWithName ctfInfo = settings.ctfMap.get(j);
                    if (info.name.equals(ctfInfo.name)) {
                        ctfInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //DeathMatch Options
            settings.gameTimeDM[0] = (gs.saveSettings.dmSettings.GameLenthDM);
            settings.scoreToWinDM[0] = (gs.saveSettings.dmSettings.scoreToWinDM);
            settings.initRespawnOnOffDM.set(gs.saveSettings.dmSettings.initRespawnDM);
            for (int i = 0; i < gs.saveSettings.dmSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.dmSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.dmMap.size(); j++) {
                    Settings.BoolInfoWithName dmInfo = settings.dmMap.get(j);
                    if (info.name.equals(dmInfo.name)) {
                        dmInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Elimination Options
            settings.heroSelectionTime[0] = gs.saveSettings.elimSettings.heroSelectioinTime;
            settings.scoreToWinElim[0] = gs.saveSettings.elimSettings.scoreToWinElim;
            settings.restrictPreviouslyUsedHeroElimSelection.set(gs.saveSettings.elimSettings.restrictPreviouslyUsedHero);
            settings.heroSelectionElimSelection.set(gs.saveSettings.elimSettings.heroSelection);
            settings.limitedChoicePoolElimSelection.set(gs.saveSettings.elimSettings.limitChoicePool);
            settings.captureObjectiveTiebreakerElim.set(gs.saveSettings.elimSettings.captureObjectiveTiebreaker);
            settings.tiebreakerAfterMatchTimeElapsedElim[0] = gs.saveSettings.elimSettings.tiebreakerAfterMatchTimeElapsed;
            settings.timeToCapture[0] = gs.saveSettings.elimSettings.timeToCapture;
            settings.drawAfterMatchTimeElapsedWithNoTiebreakerElim[0] = gs.saveSettings.elimSettings.drawAfterMatchTimeElapsedWithNoTiebreaker;
            settings.revealHeroesElim.set(gs.saveSettings.elimSettings.revealHeroes);
            settings.revealHeroesAfterMatchTimeElapsedElim[0] = gs.saveSettings.elimSettings.revealHeroesAfterMatchTimeElapsed;
            for (int i = 0; i < gs.saveSettings.elimSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.elimSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.elimMap.size(); j++) {
                    Settings.BoolInfoWithName elimInfo = settings.elimMap.get(j);
                    if (info.name.equals(elimInfo.name)) {
                        elimInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Team Deathmatch Options
            settings.gameTimeTDM[0] = gs.saveSettings.teamDmSettings.gameTimeTDM;
            settings.mercyResCounteractsKillsTDM.set(gs.saveSettings.teamDmSettings.mercyResCounteractsKills);
            settings.scoreToWinTDM[0] = gs.saveSettings.teamDmSettings.scoreToWinTDM;
            settings.initRespawnOnOfTDM.set(gs.saveSettings.teamDmSettings.initRespawnTDM);
            settings.imbalancedTeamScoreToWinTDM.set(gs.saveSettings.teamDmSettings.imbalancedTeamScoreToWin);
            settings.team1ScoreToWinTDM[0] = gs.saveSettings.teamDmSettings.team1ScoreToWin;
            settings.team2ScoreToWinTDM[0] = gs.saveSettings.teamDmSettings.team2ScoreToWin;
            for (int i = 0; i < gs.saveSettings.teamDmSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.teamDmSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.teamDmMap.size(); j++) {
                    Settings.BoolInfoWithName tdmInfo = settings.teamDmMap.get(j);
                    if (info.name.equals(tdmInfo.name)) {
                        tdmInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            //Practice Range Options
            settings.spawnTrainingBots.set(gs.saveSettings.practiceRangeSettings.spawnTrainingBots);
            settings.trainingBotRespawnTimeScaler[0] = gs.saveSettings.practiceRangeSettings.trainingBotRespawnTimeScaler;
            settings.trainingPartner.set(gs.saveSettings.practiceRangeSettings.trainingPartner);

            //Skirmish Options
            settings.skirmishValidControlPointsSelection.set(gs.saveSettings.skirmishSettings.limitValidControlPoints);
            for (int i = 0; i < gs.saveSettings.skirmishSettings.MapToggle.size(); i++) {
                BoolInfo info = gs.saveSettings.skirmishSettings.MapToggle.get(i);

                //find name and change value;
                for (int j = 0; j < settings.skirmishMap.size(); j++) {
                    Settings.BoolInfoWithName skirmishInfo = settings.skirmishMap.get(j);
                    if (info.name.equals(skirmishInfo.name)) {
                        skirmishInfo.bool.set(info.value);
                        break;
                    }
                }
            }

            Graph graph = new Graph();

            for (String var : gs.globalVariables) {
                String[] split = var.split(":");
                Variable variable = new Variable();
                variable.type = Variable.Type.GLOBAL;
//                variable.ID = Integer.valueOf(split[0]);
                variable.name = split[1];
                graph.globalVariables.add(variable);
            }

            for (String var : gs.playerVariables) {
                String[] split = var.split(":");
                Variable variable = new Variable();
                variable.type = Variable.Type.PLAYER;
//                variable.ID = Integer.valueOf(split[0]);
                variable.name = split[1];
                graph.playerVariables.add(variable);
            }

            for (String sub : gs.subroutines) {
                String[] split = sub.split(":");
                graph.subroutines.add(split[1]);
            }

            for (int i = 0; i < gs.nodeSaves.size(); i++) {
                NodeSave save = gs.nodeSaves.get(i);
                Class classNode = null;

                try {
                    ClassLoader loader = GraphSaver.class.getClassLoader();
                    classNode = Class.forName(save.className, true, loader);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (classNode == null) {
                    System.out.println("Class was null, couldn't load: " + save.className);
//                    return null;
                    continue;
                }

                Node node = (Node) classNode.getDeclaredConstructor(Graph.class).newInstance(graph);
                node.setName(save.nodeName);
                graph.addNode(node);
                node.posX = save.x;
                node.posY = save.y;

                for (String extraData : save.extraData) {
                    node.getExtraSaveData().add(extraData);
                }

                loaded[i] = node;
            }

            for (int i = 0; i < loaded.length; i++) {
                Node node = loaded[i];

                if (node != null) {
                    NodeSave save = gs.nodeSaves.get(i);

                    //load input pins
                    for (int j = 0; j < save.inputPins.size(); j++) {
                        if (j >= node.inputPins.size()) {
                            Class classNode = null;

                            ClassLoader loader = GraphSaver.class.getClassLoader();
                            classNode = Class.forName(save.inputPins.get(j).type, true, loader);

                            if (classNode == null) {
                                System.out.println("Class " + save.inputPins.get(j).type + " was null, couldn't load");
                                return null;
                            }

//                            int id = Graph.getNextAvailablePinID();
                            Pin pin = (Pin) classNode.getDeclaredConstructor().newInstance();
                            pin.setNode(node);
                            pin.setCanDelete(save.inputPins.get(j).canDelete);

//                            pin.setCanDelete(true);
                            pin.setPinType(Pin.PinType.Input);
                            node.inputPins.add(pin);
                        }

                        node.inputPins.get(j).setID(save.inputPins.get(j).ID);

                        //Load any custom values set to pins that haven't got a connection
                        //
                        if (node.inputPins.get(j).getData() != null) {
                            node.inputPins.get(j).loadValue(save.inputPins.get(j).value);
                        }
                    }

                    //load output pins
                    for (int j = 0; j < save.outputPins.size(); j++) {
                        if (j >= node.outputPins.size()) {
                            Class classNode = null;

                            ClassLoader loader = GraphSaver.class.getClassLoader();
                            classNode = Class.forName(save.outputPins.get(j).type, true, loader);

                            if (classNode == null) {
                                System.out.println("Class " + save.inputPins.get(j).type + " was null, couldn't load");
                                return null;
                            }

                            Pin pin = (Pin) classNode.getDeclaredConstructor().newInstance();
                            pin.setNode(node);
                            pin.setCanDelete(save.outputPins.get(j).canDelete);

//                            pin.setCanDelete(true);
                            pin.setPinType(Pin.PinType.Output);
                            node.outputPins.add(pin);
                        }

                        node.outputPins.get(j).setID(save.outputPins.get(j).ID);

                    }
                    node.onLoaded();

                    // Load and setup pin connections
                    for (int j = 0; j < save.inputPins.size(); j++) {
                        //check and set the pin ID which this Pin is connected to
//                        int ID = save.inputPins.get(j).connectedTo;
//                        node.inputPins.get(j).connectedTo = ID;

                        for (int k = 0; k < save.inputPins.get(j).connections.size(); k++) {
                            int ID = save.inputPins.get(j).connections.get(k);
                            node.inputPins.get(j).connectedToList.add(ID);
                        }
//                        System.out.println(node.inputPins.get(j).connectedTo);
//                        System.out.println(node.inputPins.get(j).isConnected());
//                        System.out.println(node.inputPins.get(j).getConnectedPin());
//
//                        if(node.inputPins.get(j).getConnectedPin() == null){
//                            node.inputPins.get(j).connectedTo = -1;
//                        }
                    }

                    for (int j = 0; j < save.outputPins.size(); j++) {
//                        if(save.outputPins.get(j).connectedTo != -1){
//                            if(node.outputPins.get(j).getNode().getGraph().findPinById(save.outputPins.get(j).connectedTo) != null)
                        {
//                                node.outputPins.get(j).connectedTo = save.outputPins.get(j).connectedTo;

                            for (int k = 0; k < save.outputPins.get(j).connections.size(); k++) {
                                int ID = save.outputPins.get(j).connections.get(k);
                                node.outputPins.get(j).connectedToList.add(ID);
                            }
//                                System.out.println(node.outputPins.get(j).connectedTo);
//                                System.out.println(node.outputPins.get(j).isConnected());
//                                System.out.println(node.outputPins.get(j).getConnectedPin());
//
//                                if(node.outputPins.get(j).getConnectedPin() == null){
//                                    node.outputPins.get(j).connectedTo = -1;
//                                }
                        }
//                        }
                    }

                }

            }

            //loop through all nodes on graph and validate all links
            TaskSchedule.addTask(new Task() {
                @Override
                public void onFinished() {
                    for (Node node : graph.getNodes().values()) {
                        for (int i = 0; i < node.inputPins.size(); i++) {
                            Pin pin = node.inputPins.get(i);
                            pin.validateAllConnections();
                        }

                        for (int i = 0; i < node.outputPins.size(); i++) {
                            Pin pin = node.outputPins.get(i);
                            pin.validateAllConnections();
                        }
                    }
                }
            }, 2000);


            return graph;

        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class GraphSave{
        //Settings
        private SaveSettings saveSettings = new SaveSettings();
        private ArrayList<String> globalVariables = new ArrayList<>();
        private ArrayList<String> playerVariables = new ArrayList<>();
        private ArrayList<String> subroutines = new ArrayList<>();
        private ArrayList<NodeSave> nodeSaves = new ArrayList<>();
    }

    private static class SaveSettings{
        private String modeName = "";
        private String description = "";

        private int maxT1Players = 5;
        private int maxT2Players = 5;
        private int maxFFAPlayers = 2;
        private int mapRotation = 0;
        private int returnToLobby = 0;

        private boolean assaultMode;
        private boolean controlMode = true;
        private boolean escortMode = true;
        private boolean hybridMode = true;
        private boolean pushMode = true;
        private boolean bountyHunterMode;
        private boolean ctfMode;
        private boolean deathmatchMode;
        private boolean eliminationMode;
        private boolean teamDeathmatchMode;
        private boolean practiceRangeMode;
        private boolean skirmishMode;

        private ArrayList<BoolInfo> extensionToggle = new ArrayList<>();

        private AssaultSettings assaultSettings = new AssaultSettings();
        private ControlSettings controlSettings = new ControlSettings();
        private EscortSettings escortSettings = new EscortSettings();
        private HybridSettings hybridSettings = new HybridSettings();
        private PushSettings pushSettings = new PushSettings();
        private BountyHunterSettings bhSettings = new BountyHunterSettings();
        private CtfSettings ctfSettings = new CtfSettings();
        private DeatchMatchSettings dmSettings = new DeatchMatchSettings();
        private EliminationSettings elimSettings = new EliminationSettings();
        private TeamDeatchMatchSettings teamDmSettings = new TeamDeatchMatchSettings();
        private PracticeRangeSettings practiceRangeSettings = new PracticeRangeSettings();
        private SkirmishSettings skirmishSettings = new SkirmishSettings();
    }

    private static class AssaultSettings{
        private int speedModifier = 100;
        private boolean compRules = false;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class ControlSettings{
        private int speedModifier = 100;
        private boolean compRules = false;
        private int validPoints = 0;
        private int scoreToWin = 2;
        private int scoringSpeedModifier = 100;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class EscortSettings{
        private boolean compRules = false;
        private int speedModifier = 100;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class HybridSettings{
        private int speedModifier = 100;
        private boolean compRules = false;
        private int payloadSpeedModifier = 100;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class PushSettings{
        private boolean compRules = false;
        private int pushSpeedModifier = 100;
        private int walkSpeedModifier = 100;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class BountyHunterSettings{
        private int baseScoreForKillingBountyTarget = 300;
        private int bountyIncreasePerKillAsBountyTarget = 0;
        private int gameTimeBH = 10;
        private int scorePerKillBH = 100;
        private int scorePerKillAsBountyTarget = 300;
        private int scoreToWinBH = 20;
        private boolean initRespawnBH = true;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class CtfSettings{
        private boolean blitzFlagLocations = false;
        private boolean damageInterruptsFlagInteractions = false;
        private int flagCarrierAbilities = 2;
        private float flagDroppedLockTime = 5.0f;
        private float flagPickupTime = 0.0f;
        private float flagReturnTime = 4.0f;
        private float flagScoreRespawnTime = 15.0f;
        private int gameTimeCtf = 10;
        private float respawnSpeedBuffDuration = 0.0f;
        private int scoreToWinCtf = 3;
        private boolean teamNeedsFlagAtBaseToScore = false;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class DeatchMatchSettings{
        //Deathmatch Options
        private int GameLenthDM = 10;
        private int scoreToWinDM = 20;
        private boolean initRespawnDM = true;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class EliminationSettings{
        private int heroSelectioinTime = 20;
        private int scoreToWinElim = 3;
        private int restrictPreviouslyUsedHero = 0;
        private int heroSelection = 0;
        private int limitChoicePool = 0;
        private boolean captureObjectiveTiebreaker = true;
        private int tiebreakerAfterMatchTimeElapsed = 105;
        private int timeToCapture = 3;
        private int drawAfterMatchTimeElapsedWithNoTiebreaker = 135;
        private boolean revealHeroes = false;
        private int revealHeroesAfterMatchTimeElapsed = 75;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class TeamDeatchMatchSettings{
        private int gameTimeTDM = 10;
        private boolean mercyResCounteractsKills = true;
        private int scoreToWinTDM = 30;
        private boolean initRespawnTDM = true;
        private boolean imbalancedTeamScoreToWin = false;
        private int team1ScoreToWin = 30;
        private int team2ScoreToWin = 30;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class PracticeRangeSettings{
        private boolean spawnTrainingBots = true;
        private int trainingBotRespawnTimeScaler = 100;
        private boolean trainingPartner = true;
    }

    private static class SkirmishSettings{
        private int limitValidControlPoints = 0;
        private ArrayList<BoolInfo> MapToggle = new ArrayList<>();
    }

    private static class BoolInfo{
        String name;
        boolean value;
    }

    private static class PinSave{
        private Integer ID;
        private String type;
        private String value;
//        private Integer connectedTo;
        private ArrayList<Integer> connections = new ArrayList<>();
        private boolean canDelete;
    }


    private static class NodeSave{
        private String className;
        private String nodeName;
        private float x;
        private float y;
        private ArrayList<String> extraData = new ArrayList<>();
        //save Pin array
        private ArrayList<PinSave> inputPins = new ArrayList<>();
        private ArrayList<PinSave> outputPins = new ArrayList<>();
//        private ArrayList<Integer> connectedToList = new ArrayList<>();

    }
}
