package com.ssafy.matchup_statistics.global.util.mapper;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.match.entity.MatchDetail;
import java.util.ArrayList;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-27T11:54:03+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class MatchMapperImpl implements MatchMapper {

    @Override
    public MatchDetail matchDetailResponseDtoToMatchDetail(MatchDetailResponseDto matchDetailResponseDto) {
        if ( matchDetailResponseDto == null ) {
            return null;
        }

        MatchDetail matchDetail = new MatchDetail();

        matchDetail.metadata = metadataToMetadata( matchDetailResponseDto.getMetadata() );
        matchDetail.info = infoToInfo( matchDetailResponseDto.getInfo() );

        return matchDetail;
    }

    protected MatchDetail.Metadata metadataToMetadata(MatchDetailResponseDto.Metadata metadata) {
        if ( metadata == null ) {
            return null;
        }

        MatchDetail.Metadata metadata1 = new MatchDetail.Metadata();

        if ( metadata1.getParticipants() != null ) {
            ArrayList<String> arrayList = metadata.getParticipants();
            if ( arrayList != null ) {
                metadata1.getParticipants().addAll( arrayList );
            }
        }
        metadata1.dataVersion = metadata.getDataVersion();
        metadata1.matchId = metadata.getMatchId();

        return metadata1;
    }

    protected MatchDetail.Challenges challengesToChallenges(MatchDetailResponseDto.Challenges challenges) {
        if ( challenges == null ) {
            return null;
        }

        MatchDetail.Challenges challenges1 = new MatchDetail.Challenges();

        if ( challenges1.getLegendaryItemUsed() != null ) {
            ArrayList<Integer> arrayList = challenges.getLegendaryItemUsed();
            if ( arrayList != null ) {
                challenges1.getLegendaryItemUsed().addAll( arrayList );
            }
        }
        challenges1.kTurretsDestroyedBeforePlatesFall = challenges.kTurretsDestroyedBeforePlatesFall;
        challenges1._12AssistStreakCount = challenges.get_12AssistStreakCount();
        challenges1.abilityUses = challenges.getAbilityUses();
        challenges1.acesBefore15Minutes = challenges.getAcesBefore15Minutes();
        challenges1.alliedJungleMonsterKills = challenges.getAlliedJungleMonsterKills();
        challenges1.baronTakedowns = challenges.getBaronTakedowns();
        challenges1.blastConeOppositeOpponentCount = challenges.getBlastConeOppositeOpponentCount();
        challenges1.bountyGold = challenges.getBountyGold();
        challenges1.buffsStolen = challenges.getBuffsStolen();
        challenges1.completeSupportQuestInTime = challenges.getCompleteSupportQuestInTime();
        challenges1.controlWardTimeCoverageInRiverOrEnemyHalf = challenges.getControlWardTimeCoverageInRiverOrEnemyHalf();
        challenges1.controlWardsPlaced = challenges.getControlWardsPlaced();
        challenges1.damagePerMinute = challenges.getDamagePerMinute();
        challenges1.damageTakenOnTeamPercentage = challenges.getDamageTakenOnTeamPercentage();
        challenges1.dancedWithRiftHerald = challenges.getDancedWithRiftHerald();
        challenges1.deathsByEnemyChamps = challenges.getDeathsByEnemyChamps();
        challenges1.dodgeSkillShotsSmallWindow = challenges.getDodgeSkillShotsSmallWindow();
        challenges1.doubleAces = challenges.getDoubleAces();
        challenges1.dragonTakedowns = challenges.getDragonTakedowns();
        challenges1.earlyLaningPhaseGoldExpAdvantage = challenges.getEarlyLaningPhaseGoldExpAdvantage();
        challenges1.effectiveHealAndShielding = challenges.getEffectiveHealAndShielding();
        challenges1.elderDragonKillsWithOpposingSoul = challenges.getElderDragonKillsWithOpposingSoul();
        challenges1.elderDragonMultikills = challenges.getElderDragonMultikills();
        challenges1.enemyChampionImmobilizations = challenges.getEnemyChampionImmobilizations();
        challenges1.enemyJungleMonsterKills = challenges.getEnemyJungleMonsterKills();
        challenges1.epicMonsterKillsNearEnemyJungler = challenges.getEpicMonsterKillsNearEnemyJungler();
        challenges1.epicMonsterKillsWithin30SecondsOfSpawn = challenges.getEpicMonsterKillsWithin30SecondsOfSpawn();
        challenges1.epicMonsterSteals = challenges.getEpicMonsterSteals();
        challenges1.epicMonsterStolenWithoutSmite = challenges.getEpicMonsterStolenWithoutSmite();
        challenges1.firstTurretKilled = challenges.getFirstTurretKilled();
        challenges1.flawlessAces = challenges.getFlawlessAces();
        challenges1.fullTeamTakedown = challenges.getFullTeamTakedown();
        challenges1.gameLength = challenges.getGameLength();
        challenges1.shortestTimeToAceFromFirstTakedown = challenges.getShortestTimeToAceFromFirstTakedown();
        challenges1.fasterSupportQuestCompletion = challenges.getFasterSupportQuestCompletion();
        challenges1.getTakedownsInAllLanesEarlyJungleAsLaner = challenges.getGetTakedownsInAllLanesEarlyJungleAsLaner();
        challenges1.goldPerMinute = challenges.getGoldPerMinute();
        challenges1.hadOpenNexus = challenges.getHadOpenNexus();
        challenges1.immobilizeAndKillWithAlly = challenges.getImmobilizeAndKillWithAlly();
        challenges1.initialBuffCount = challenges.getInitialBuffCount();
        challenges1.initialCrabCount = challenges.getInitialCrabCount();
        challenges1.jungleCsBefore10Minutes = challenges.getJungleCsBefore10Minutes();
        challenges1.junglerTakedownsNearDamagedEpicMonster = challenges.getJunglerTakedownsNearDamagedEpicMonster();
        challenges1.kda = challenges.getKda();
        challenges1.killAfterHiddenWithAlly = challenges.getKillAfterHiddenWithAlly();
        challenges1.killParticipation = challenges.getKillParticipation();
        challenges1.killedChampTookFullTeamDamageSurvived = challenges.getKilledChampTookFullTeamDamageSurvived();
        challenges1.killingSprees = challenges.getKillingSprees();
        challenges1.killsNearEnemyTurret = challenges.getKillsNearEnemyTurret();
        challenges1.killsOnOtherLanesEarlyJungleAsLaner = challenges.getKillsOnOtherLanesEarlyJungleAsLaner();
        challenges1.killsOnRecentlyHealedByAramPack = challenges.getKillsOnRecentlyHealedByAramPack();
        challenges1.killsUnderOwnTurret = challenges.getKillsUnderOwnTurret();
        challenges1.killsWithHelpFromEpicMonster = challenges.getKillsWithHelpFromEpicMonster();
        challenges1.knockEnemyIntoTeamAndKill = challenges.getKnockEnemyIntoTeamAndKill();
        challenges1.landSkillShotsEarlyGame = challenges.getLandSkillShotsEarlyGame();
        challenges1.laneMinionsFirst10Minutes = challenges.getLaneMinionsFirst10Minutes();
        challenges1.laningPhaseGoldExpAdvantage = challenges.getLaningPhaseGoldExpAdvantage();
        challenges1.legendaryCount = challenges.getLegendaryCount();
        challenges1.lostAnInhibitor = challenges.getLostAnInhibitor();
        challenges1.maxCsAdvantageOnLaneOpponent = challenges.getMaxCsAdvantageOnLaneOpponent();
        challenges1.maxKillDeficit = challenges.getMaxKillDeficit();
        challenges1.maxLevelLeadLaneOpponent = challenges.getMaxLevelLeadLaneOpponent();
        challenges1.mejaisFullStackInTime = challenges.getMejaisFullStackInTime();
        challenges1.moreEnemyJungleThanOpponent = challenges.getMoreEnemyJungleThanOpponent();
        challenges1.multiKillOneSpell = challenges.getMultiKillOneSpell();
        challenges1.multiTurretRiftHeraldCount = challenges.getMultiTurretRiftHeraldCount();
        challenges1.multikills = challenges.getMultikills();
        challenges1.multikillsAfterAggressiveFlash = challenges.getMultikillsAfterAggressiveFlash();
        challenges1.outerTurretExecutesBefore10Minutes = challenges.getOuterTurretExecutesBefore10Minutes();
        challenges1.outnumberedKills = challenges.getOutnumberedKills();
        challenges1.outnumberedNexusKill = challenges.getOutnumberedNexusKill();
        challenges1.perfectDragonSoulsTaken = challenges.getPerfectDragonSoulsTaken();
        challenges1.perfectGame = challenges.getPerfectGame();
        challenges1.pickKillWithAlly = challenges.getPickKillWithAlly();
        challenges1.playedChampSelectPosition = challenges.getPlayedChampSelectPosition();
        challenges1.poroExplosions = challenges.getPoroExplosions();
        challenges1.quickCleanse = challenges.getQuickCleanse();
        challenges1.quickFirstTurret = challenges.getQuickFirstTurret();
        challenges1.quickSoloKills = challenges.getQuickSoloKills();
        challenges1.riftHeraldTakedowns = challenges.getRiftHeraldTakedowns();
        challenges1.saveAllyFromDeath = challenges.getSaveAllyFromDeath();
        challenges1.scuttleCrabKills = challenges.getScuttleCrabKills();
        challenges1.skillshotsDodged = challenges.getSkillshotsDodged();
        challenges1.skillshotsHit = challenges.getSkillshotsHit();
        challenges1.snowballsHit = challenges.getSnowballsHit();
        challenges1.soloBaronKills = challenges.getSoloBaronKills();
        challenges1.soloKills = challenges.getSoloKills();
        challenges1.soloTurretsLategame = challenges.getSoloTurretsLategame();
        challenges1.stealthWardsPlaced = challenges.getStealthWardsPlaced();
        challenges1.survivedSingleDigitHpCount = challenges.getSurvivedSingleDigitHpCount();
        challenges1.survivedThreeImmobilizesInFight = challenges.getSurvivedThreeImmobilizesInFight();
        challenges1.takedownOnFirstTurret = challenges.getTakedownOnFirstTurret();
        challenges1.takedowns = challenges.getTakedowns();
        challenges1.takedownsAfterGainingLevelAdvantage = challenges.getTakedownsAfterGainingLevelAdvantage();
        challenges1.takedownsBeforeJungleMinionSpawn = challenges.getTakedownsBeforeJungleMinionSpawn();
        challenges1.takedownsFirstXMinutes = challenges.getTakedownsFirstXMinutes();
        challenges1.takedownsInAlcove = challenges.getTakedownsInAlcove();
        challenges1.takedownsInEnemyFountain = challenges.getTakedownsInEnemyFountain();
        challenges1.teamBaronKills = challenges.getTeamBaronKills();
        challenges1.teamDamagePercentage = challenges.getTeamDamagePercentage();
        challenges1.teamElderDragonKills = challenges.getTeamElderDragonKills();
        challenges1.teamRiftHeraldKills = challenges.getTeamRiftHeraldKills();
        challenges1.tookLargeDamageSurvived = challenges.getTookLargeDamageSurvived();
        challenges1.turretPlatesTaken = challenges.getTurretPlatesTaken();
        challenges1.turretTakedowns = challenges.getTurretTakedowns();
        challenges1.turretsTakenWithRiftHerald = challenges.getTurretsTakenWithRiftHerald();
        challenges1.twentyMinionsIn3SecondsCount = challenges.getTwentyMinionsIn3SecondsCount();
        challenges1.twoWardsOneSweeperCount = challenges.getTwoWardsOneSweeperCount();
        challenges1.unseenRecalls = challenges.getUnseenRecalls();
        challenges1.visionScoreAdvantageLaneOpponent = challenges.getVisionScoreAdvantageLaneOpponent();
        challenges1.visionScorePerMinute = challenges.getVisionScorePerMinute();
        challenges1.wardTakedowns = challenges.getWardTakedowns();
        challenges1.wardTakedownsBefore20M = challenges.getWardTakedownsBefore20M();
        challenges1.wardsGuarded = challenges.getWardsGuarded();
        challenges1.earliestDragonTakedown = challenges.getEarliestDragonTakedown();
        challenges1.junglerKillsEarlyJungle = challenges.getJunglerKillsEarlyJungle();
        challenges1.killsOnLanersEarlyJungleAsJungler = challenges.getKillsOnLanersEarlyJungleAsJungler();
        challenges1.baronBuffGoldAdvantageOverThreshold = challenges.getBaronBuffGoldAdvantageOverThreshold();
        challenges1.earliestBaron = challenges.getEarliestBaron();
        challenges1.firstTurretKilledTime = challenges.getFirstTurretKilledTime();
        challenges1.teleportTakedowns = challenges.getTeleportTakedowns();
        challenges1.highestChampionDamage = challenges.getHighestChampionDamage();
        challenges1.highestCrowdControlScore = challenges.getHighestCrowdControlScore();
        challenges1.highestWardKills = challenges.getHighestWardKills();

        return challenges1;
    }

    protected MatchDetail.Missions missionsToMissions(MatchDetailResponseDto.Missions missions) {
        if ( missions == null ) {
            return null;
        }

        MatchDetail.Missions missions1 = new MatchDetail.Missions();

        missions1.PlayerScore0 = missions.PlayerScore0;
        missions1.PlayerScore1 = missions.PlayerScore1;
        missions1.PlayerScore10 = missions.PlayerScore10;
        missions1.PlayerScore11 = missions.PlayerScore11;
        missions1.PlayerScore2 = missions.PlayerScore2;
        missions1.PlayerScore3 = missions.PlayerScore3;
        missions1.PlayerScore4 = missions.PlayerScore4;
        missions1.PlayerScore5 = missions.PlayerScore5;
        missions1.PlayerScore6 = missions.PlayerScore6;
        missions1.PlayerScore7 = missions.PlayerScore7;
        missions1.PlayerScore8 = missions.PlayerScore8;
        missions1.PlayerScore9 = missions.PlayerScore9;
        missions1.playerScore0 = missions.getPlayerScore0();
        missions1.playerScore1 = missions.getPlayerScore1();
        missions1.playerScore10 = missions.getPlayerScore10();
        missions1.playerScore11 = missions.getPlayerScore11();
        missions1.playerScore2 = missions.getPlayerScore2();
        missions1.playerScore3 = missions.getPlayerScore3();
        missions1.playerScore4 = missions.getPlayerScore4();
        missions1.playerScore5 = missions.getPlayerScore5();
        missions1.playerScore6 = missions.getPlayerScore6();
        missions1.playerScore7 = missions.getPlayerScore7();
        missions1.playerScore8 = missions.getPlayerScore8();
        missions1.playerScore9 = missions.getPlayerScore9();

        return missions1;
    }

    protected MatchDetail.Selection selectionToSelection(MatchDetailResponseDto.Selection selection) {
        if ( selection == null ) {
            return null;
        }

        MatchDetail.Selection selection1 = new MatchDetail.Selection();

        selection1.perk = selection.getPerk();
        selection1.var1 = selection.getVar1();
        selection1.var2 = selection.getVar2();
        selection1.var3 = selection.getVar3();

        return selection1;
    }

    protected ArrayList<MatchDetail.Selection> selectionArrayListToSelectionArrayList(ArrayList<MatchDetailResponseDto.Selection> arrayList) {
        if ( arrayList == null ) {
            return null;
        }

        ArrayList<MatchDetail.Selection> arrayList1 = new ArrayList<MatchDetail.Selection>();
        for ( MatchDetailResponseDto.Selection selection : arrayList ) {
            arrayList1.add( selectionToSelection( selection ) );
        }

        return arrayList1;
    }

    protected MatchDetail.Style styleToStyle(MatchDetailResponseDto.Style style) {
        if ( style == null ) {
            return null;
        }

        MatchDetail.Style style1 = new MatchDetail.Style();

        if ( style1.getSelections() != null ) {
            ArrayList<MatchDetail.Selection> arrayList = selectionArrayListToSelectionArrayList( style.getSelections() );
            if ( arrayList != null ) {
                style1.getSelections().addAll( arrayList );
            }
        }
        style1.description = style.getDescription();
        style1.style = style.getStyle();

        return style1;
    }

    protected ArrayList<MatchDetail.Style> styleArrayListToStyleArrayList(ArrayList<MatchDetailResponseDto.Style> arrayList) {
        if ( arrayList == null ) {
            return null;
        }

        ArrayList<MatchDetail.Style> arrayList1 = new ArrayList<MatchDetail.Style>();
        for ( MatchDetailResponseDto.Style style : arrayList ) {
            arrayList1.add( styleToStyle( style ) );
        }

        return arrayList1;
    }

    protected MatchDetail.StatPerks statPerksToStatPerks(MatchDetailResponseDto.StatPerks statPerks) {
        if ( statPerks == null ) {
            return null;
        }

        MatchDetail.StatPerks statPerks1 = new MatchDetail.StatPerks();

        statPerks1.defense = statPerks.getDefense();
        statPerks1.flex = statPerks.getFlex();
        statPerks1.offense = statPerks.getOffense();

        return statPerks1;
    }

    protected MatchDetail.Perks perksToPerks(MatchDetailResponseDto.Perks perks) {
        if ( perks == null ) {
            return null;
        }

        MatchDetail.Perks perks1 = new MatchDetail.Perks();

        if ( perks1.getStyles() != null ) {
            ArrayList<MatchDetail.Style> arrayList = styleArrayListToStyleArrayList( perks.getStyles() );
            if ( arrayList != null ) {
                perks1.getStyles().addAll( arrayList );
            }
        }
        perks1.statPerks = statPerksToStatPerks( perks.getStatPerks() );

        return perks1;
    }

    protected MatchDetail.Participant participantToParticipant(MatchDetailResponseDto.Participant participant) {
        if ( participant == null ) {
            return null;
        }

        MatchDetail.Participant participant1 = new MatchDetail.Participant();

        participant1.allInPings = participant.getAllInPings();
        participant1.assistMePings = participant.getAssistMePings();
        participant1.assists = participant.getAssists();
        participant1.baronKills = participant.getBaronKills();
        participant1.basicPings = participant.getBasicPings();
        participant1.bountyLevel = participant.getBountyLevel();
        participant1.challenges = challengesToChallenges( participant.getChallenges() );
        participant1.champExperience = participant.getChampExperience();
        participant1.champLevel = participant.getChampLevel();
        participant1.championId = participant.getChampionId();
        participant1.championName = participant.getChampionName();
        participant1.championTransform = participant.getChampionTransform();
        participant1.commandPings = participant.getCommandPings();
        participant1.consumablesPurchased = participant.getConsumablesPurchased();
        participant1.damageDealtToBuildings = participant.getDamageDealtToBuildings();
        participant1.damageDealtToObjectives = participant.getDamageDealtToObjectives();
        participant1.damageDealtToTurrets = participant.getDamageDealtToTurrets();
        participant1.damageSelfMitigated = participant.getDamageSelfMitigated();
        participant1.dangerPings = participant.getDangerPings();
        participant1.deaths = participant.getDeaths();
        participant1.detectorWardsPlaced = participant.getDetectorWardsPlaced();
        participant1.doubleKills = participant.getDoubleKills();
        participant1.dragonKills = participant.getDragonKills();
        participant1.eligibleForProgression = participant.isEligibleForProgression();
        participant1.enemyMissingPings = participant.getEnemyMissingPings();
        participant1.enemyVisionPings = participant.getEnemyVisionPings();
        participant1.firstBloodAssist = participant.isFirstBloodAssist();
        participant1.firstBloodKill = participant.isFirstBloodKill();
        participant1.firstTowerAssist = participant.isFirstTowerAssist();
        participant1.firstTowerKill = participant.isFirstTowerKill();
        participant1.gameEndedInEarlySurrender = participant.isGameEndedInEarlySurrender();
        participant1.gameEndedInSurrender = participant.isGameEndedInSurrender();
        participant1.getBackPings = participant.getGetBackPings();
        participant1.goldEarned = participant.getGoldEarned();
        participant1.goldSpent = participant.getGoldSpent();
        participant1.holdPings = participant.getHoldPings();
        participant1.individualPosition = participant.getIndividualPosition();
        participant1.inhibitorKills = participant.getInhibitorKills();
        participant1.inhibitorTakedowns = participant.getInhibitorTakedowns();
        participant1.inhibitorsLost = participant.getInhibitorsLost();
        participant1.item0 = participant.getItem0();
        participant1.item1 = participant.getItem1();
        participant1.item2 = participant.getItem2();
        participant1.item3 = participant.getItem3();
        participant1.item4 = participant.getItem4();
        participant1.item5 = participant.getItem5();
        participant1.item6 = participant.getItem6();
        participant1.itemsPurchased = participant.getItemsPurchased();
        participant1.killingSprees = participant.getKillingSprees();
        participant1.kills = participant.getKills();
        participant1.lane = participant.getLane();
        participant1.largestCriticalStrike = participant.getLargestCriticalStrike();
        participant1.largestKillingSpree = participant.getLargestKillingSpree();
        participant1.largestMultiKill = participant.getLargestMultiKill();
        participant1.longestTimeSpentLiving = participant.getLongestTimeSpentLiving();
        participant1.magicDamageDealt = participant.getMagicDamageDealt();
        participant1.magicDamageDealtToChampions = participant.getMagicDamageDealtToChampions();
        participant1.magicDamageTaken = participant.getMagicDamageTaken();
        participant1.missions = missionsToMissions( participant.getMissions() );
        participant1.needVisionPings = participant.getNeedVisionPings();
        participant1.neutralMinionsKilled = participant.getNeutralMinionsKilled();
        participant1.nexusKills = participant.getNexusKills();
        participant1.nexusLost = participant.getNexusLost();
        participant1.nexusTakedowns = participant.getNexusTakedowns();
        participant1.objectivesStolen = participant.getObjectivesStolen();
        participant1.objectivesStolenAssists = participant.getObjectivesStolenAssists();
        participant1.onMyWayPings = participant.getOnMyWayPings();
        participant1.participantId = participant.getParticipantId();
        participant1.pentaKills = participant.getPentaKills();
        participant1.perks = perksToPerks( participant.getPerks() );
        participant1.physicalDamageDealt = participant.getPhysicalDamageDealt();
        participant1.physicalDamageDealtToChampions = participant.getPhysicalDamageDealtToChampions();
        participant1.physicalDamageTaken = participant.getPhysicalDamageTaken();
        participant1.placement = participant.getPlacement();
        participant1.playerAugment1 = participant.getPlayerAugment1();
        participant1.playerAugment2 = participant.getPlayerAugment2();
        participant1.playerAugment3 = participant.getPlayerAugment3();
        participant1.playerAugment4 = participant.getPlayerAugment4();
        participant1.playerScore0 = participant.getPlayerScore0();
        participant1.playerScore1 = participant.getPlayerScore1();
        participant1.playerScore10 = participant.getPlayerScore10();
        participant1.playerScore11 = participant.getPlayerScore11();
        participant1.playerScore2 = participant.getPlayerScore2();
        participant1.playerScore3 = participant.getPlayerScore3();
        participant1.playerScore4 = participant.getPlayerScore4();
        participant1.playerScore5 = participant.getPlayerScore5();
        participant1.playerScore6 = participant.getPlayerScore6();
        participant1.playerScore7 = participant.getPlayerScore7();
        participant1.playerScore8 = participant.getPlayerScore8();
        participant1.playerScore9 = participant.getPlayerScore9();
        participant1.playerSubteamId = participant.getPlayerSubteamId();
        participant1.profileIcon = participant.getProfileIcon();
        participant1.pushPings = participant.getPushPings();
        participant1.puuid = participant.getPuuid();
        participant1.quadraKills = participant.getQuadraKills();
        participant1.riotIdGameName = participant.getRiotIdGameName();
        participant1.riotIdTagline = participant.getRiotIdTagline();
        participant1.role = participant.getRole();
        participant1.sightWardsBoughtInGame = participant.getSightWardsBoughtInGame();
        participant1.spell1Casts = participant.getSpell1Casts();
        participant1.spell2Casts = participant.getSpell2Casts();
        participant1.spell3Casts = participant.getSpell3Casts();
        participant1.spell4Casts = participant.getSpell4Casts();
        participant1.subteamPlacement = participant.getSubteamPlacement();
        participant1.summoner1Casts = participant.getSummoner1Casts();
        participant1.summoner1Id = participant.getSummoner1Id();
        participant1.summoner2Casts = participant.getSummoner2Casts();
        participant1.summoner2Id = participant.getSummoner2Id();
        participant1.summonerId = participant.getSummonerId();
        participant1.summonerLevel = participant.getSummonerLevel();
        participant1.summonerName = participant.getSummonerName();
        participant1.teamEarlySurrendered = participant.isTeamEarlySurrendered();
        participant1.teamId = participant.getTeamId();
        participant1.teamPosition = participant.getTeamPosition();
        participant1.timeCCingOthers = participant.getTimeCCingOthers();
        participant1.timePlayed = participant.getTimePlayed();
        participant1.totalAllyJungleMinionsKilled = participant.getTotalAllyJungleMinionsKilled();
        participant1.totalDamageDealt = participant.getTotalDamageDealt();
        participant1.totalDamageDealtToChampions = participant.getTotalDamageDealtToChampions();
        participant1.totalDamageShieldedOnTeammates = participant.getTotalDamageShieldedOnTeammates();
        participant1.totalDamageTaken = participant.getTotalDamageTaken();
        participant1.totalEnemyJungleMinionsKilled = participant.getTotalEnemyJungleMinionsKilled();
        participant1.totalHeal = participant.getTotalHeal();
        participant1.totalHealsOnTeammates = participant.getTotalHealsOnTeammates();
        participant1.totalMinionsKilled = participant.getTotalMinionsKilled();
        participant1.totalTimeCCDealt = participant.getTotalTimeCCDealt();
        participant1.totalTimeSpentDead = participant.getTotalTimeSpentDead();
        participant1.totalUnitsHealed = participant.getTotalUnitsHealed();
        participant1.tripleKills = participant.getTripleKills();
        participant1.trueDamageDealt = participant.getTrueDamageDealt();
        participant1.trueDamageDealtToChampions = participant.getTrueDamageDealtToChampions();
        participant1.trueDamageTaken = participant.getTrueDamageTaken();
        participant1.turretKills = participant.getTurretKills();
        participant1.turretTakedowns = participant.getTurretTakedowns();
        participant1.turretsLost = participant.getTurretsLost();
        participant1.unrealKills = participant.getUnrealKills();
        participant1.visionClearedPings = participant.getVisionClearedPings();
        participant1.visionScore = participant.getVisionScore();
        participant1.visionWardsBoughtInGame = participant.getVisionWardsBoughtInGame();
        participant1.wardsKilled = participant.getWardsKilled();
        participant1.wardsPlaced = participant.getWardsPlaced();
        participant1.win = participant.isWin();

        return participant1;
    }

    protected ArrayList<MatchDetail.Participant> participantArrayListToParticipantArrayList(ArrayList<MatchDetailResponseDto.Participant> arrayList) {
        if ( arrayList == null ) {
            return null;
        }

        ArrayList<MatchDetail.Participant> arrayList1 = new ArrayList<MatchDetail.Participant>();
        for ( MatchDetailResponseDto.Participant participant : arrayList ) {
            arrayList1.add( participantToParticipant( participant ) );
        }

        return arrayList1;
    }

    protected MatchDetail.Ban banToBan(MatchDetailResponseDto.Ban ban) {
        if ( ban == null ) {
            return null;
        }

        MatchDetail.Ban ban1 = new MatchDetail.Ban();

        ban1.championId = ban.getChampionId();
        ban1.pickTurn = ban.getPickTurn();

        return ban1;
    }

    protected ArrayList<MatchDetail.Ban> banArrayListToBanArrayList(ArrayList<MatchDetailResponseDto.Ban> arrayList) {
        if ( arrayList == null ) {
            return null;
        }

        ArrayList<MatchDetail.Ban> arrayList1 = new ArrayList<MatchDetail.Ban>();
        for ( MatchDetailResponseDto.Ban ban : arrayList ) {
            arrayList1.add( banToBan( ban ) );
        }

        return arrayList1;
    }

    protected MatchDetail.Baron baronToBaron(MatchDetailResponseDto.Baron baron) {
        if ( baron == null ) {
            return null;
        }

        MatchDetail.Baron baron1 = new MatchDetail.Baron();

        baron1.first = baron.isFirst();
        baron1.kills = baron.getKills();

        return baron1;
    }

    protected MatchDetail.Champion championToChampion(MatchDetailResponseDto.Champion champion) {
        if ( champion == null ) {
            return null;
        }

        MatchDetail.Champion champion1 = new MatchDetail.Champion();

        champion1.first = champion.isFirst();
        champion1.kills = champion.getKills();

        return champion1;
    }

    protected MatchDetail.Dragon dragonToDragon(MatchDetailResponseDto.Dragon dragon) {
        if ( dragon == null ) {
            return null;
        }

        MatchDetail.Dragon dragon1 = new MatchDetail.Dragon();

        dragon1.first = dragon.isFirst();
        dragon1.kills = dragon.getKills();

        return dragon1;
    }

    protected MatchDetail.Horde hordeToHorde(MatchDetailResponseDto.Horde horde) {
        if ( horde == null ) {
            return null;
        }

        MatchDetail.Horde horde1 = new MatchDetail.Horde();

        horde1.first = horde.isFirst();
        horde1.kills = horde.getKills();

        return horde1;
    }

    protected MatchDetail.Inhibitor inhibitorToInhibitor(MatchDetailResponseDto.Inhibitor inhibitor) {
        if ( inhibitor == null ) {
            return null;
        }

        MatchDetail.Inhibitor inhibitor1 = new MatchDetail.Inhibitor();

        inhibitor1.first = inhibitor.isFirst();
        inhibitor1.kills = inhibitor.getKills();

        return inhibitor1;
    }

    protected MatchDetail.RiftHerald riftHeraldToRiftHerald(MatchDetailResponseDto.RiftHerald riftHerald) {
        if ( riftHerald == null ) {
            return null;
        }

        MatchDetail.RiftHerald riftHerald1 = new MatchDetail.RiftHerald();

        riftHerald1.first = riftHerald.isFirst();
        riftHerald1.kills = riftHerald.getKills();

        return riftHerald1;
    }

    protected MatchDetail.Tower towerToTower(MatchDetailResponseDto.Tower tower) {
        if ( tower == null ) {
            return null;
        }

        MatchDetail.Tower tower1 = new MatchDetail.Tower();

        tower1.first = tower.isFirst();
        tower1.kills = tower.getKills();

        return tower1;
    }

    protected MatchDetail.Objectives objectivesToObjectives(MatchDetailResponseDto.Objectives objectives) {
        if ( objectives == null ) {
            return null;
        }

        MatchDetail.Objectives objectives1 = new MatchDetail.Objectives();

        objectives1.baron = baronToBaron( objectives.getBaron() );
        objectives1.champion = championToChampion( objectives.getChampion() );
        objectives1.dragon = dragonToDragon( objectives.getDragon() );
        objectives1.horde = hordeToHorde( objectives.getHorde() );
        objectives1.inhibitor = inhibitorToInhibitor( objectives.getInhibitor() );
        objectives1.riftHerald = riftHeraldToRiftHerald( objectives.getRiftHerald() );
        objectives1.tower = towerToTower( objectives.getTower() );

        return objectives1;
    }

    protected MatchDetail.Team teamToTeam(MatchDetailResponseDto.Team team) {
        if ( team == null ) {
            return null;
        }

        MatchDetail.Team team1 = new MatchDetail.Team();

        if ( team1.getBans() != null ) {
            ArrayList<MatchDetail.Ban> arrayList = banArrayListToBanArrayList( team.getBans() );
            if ( arrayList != null ) {
                team1.getBans().addAll( arrayList );
            }
        }
        team1.objectives = objectivesToObjectives( team.getObjectives() );
        team1.teamId = team.getTeamId();
        team1.win = team.isWin();

        return team1;
    }

    protected ArrayList<MatchDetail.Team> teamArrayListToTeamArrayList(ArrayList<MatchDetailResponseDto.Team> arrayList) {
        if ( arrayList == null ) {
            return null;
        }

        ArrayList<MatchDetail.Team> arrayList1 = new ArrayList<MatchDetail.Team>();
        for ( MatchDetailResponseDto.Team team : arrayList ) {
            arrayList1.add( teamToTeam( team ) );
        }

        return arrayList1;
    }

    protected MatchDetail.Info infoToInfo(MatchDetailResponseDto.Info info) {
        if ( info == null ) {
            return null;
        }

        MatchDetail.Info info1 = new MatchDetail.Info();

        if ( info1.getParticipants() != null ) {
            ArrayList<MatchDetail.Participant> arrayList = participantArrayListToParticipantArrayList( info.getParticipants() );
            if ( arrayList != null ) {
                info1.getParticipants().addAll( arrayList );
            }
        }
        if ( info1.getTeams() != null ) {
            ArrayList<MatchDetail.Team> arrayList1 = teamArrayListToTeamArrayList( info.getTeams() );
            if ( arrayList1 != null ) {
                info1.getTeams().addAll( arrayList1 );
            }
        }
        info1.endOfGameResult = info.getEndOfGameResult();
        info1.gameCreation = info.getGameCreation();
        info1.gameDuration = info.getGameDuration();
        info1.gameEndTimestamp = info.getGameEndTimestamp();
        info1.gameId = info.getGameId();
        info1.gameMode = info.getGameMode();
        info1.gameName = info.getGameName();
        info1.gameStartTimestamp = info.getGameStartTimestamp();
        info1.gameType = info.getGameType();
        info1.gameVersion = info.getGameVersion();
        info1.mapId = info.getMapId();
        info1.platformId = info.getPlatformId();
        info1.queueId = info.getQueueId();
        info1.tournamentCode = info.getTournamentCode();

        return info1;
    }
}
