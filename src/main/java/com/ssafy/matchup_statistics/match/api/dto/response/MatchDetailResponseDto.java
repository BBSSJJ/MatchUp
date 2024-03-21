package com.ssafy.matchup_statistics.match.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MatchDetailResponseDto {
    public Metadata metadata;
    public Info info;

    @Data
    public static class Ban {
        public int championId;
        public int pickTurn;
    }

    @Data
    public static class Baron {
        public boolean first;
        public int kills;
    }

    @Data
    public static class Challenges {
        @JsonProperty("12AssistStreakCount")
        public int _12AssistStreakCount;
        public int abilityUses;
        public int acesBefore15Minutes;
        public int alliedJungleMonsterKills;
        public int baronTakedowns;
        public int blastConeOppositeOpponentCount;
        public int bountyGold;
        public int buffsStolen;
        public int completeSupportQuestInTime;
        public double controlWardTimeCoverageInRiverOrEnemyHalf;
        public int controlWardsPlaced;
        public double damagePerMinute;
        public double damageTakenOnTeamPercentage;
        public int dancedWithRiftHerald;
        public int deathsByEnemyChamps;
        public int dodgeSkillShotsSmallWindow;
        public int doubleAces;
        public int dragonTakedowns;
        public int earlyLaningPhaseGoldExpAdvantage;
        public double effectiveHealAndShielding;
        public int elderDragonKillsWithOpposingSoul;
        public int elderDragonMultikills;
        public int enemyChampionImmobilizations;
        public int enemyJungleMonsterKills;
        public int epicMonsterKillsNearEnemyJungler;
        public int epicMonsterKillsWithin30SecondsOfSpawn;
        public int epicMonsterSteals;
        public int epicMonsterStolenWithoutSmite;
        public int firstTurretKilled;
        public int flawlessAces;
        public int fullTeamTakedown;
        public double gameLength;
        public double shortestTimeToAceFromFirstTakedown;
        public int fasterSupportQuestCompletion;
        public int getTakedownsInAllLanesEarlyJungleAsLaner;
        public double goldPerMinute;
        public int hadOpenNexus;
        public int immobilizeAndKillWithAlly;
        public int initialBuffCount;
        public int initialCrabCount;
        public double jungleCsBefore10Minutes;
        public int junglerTakedownsNearDamagedEpicMonster;
        public int kTurretsDestroyedBeforePlatesFall;
        public double kda;
        public int killAfterHiddenWithAlly;
        public double killParticipation;
        public int killedChampTookFullTeamDamageSurvived;
        public int killingSprees;
        public int killsNearEnemyTurret;
        public int killsOnOtherLanesEarlyJungleAsLaner;
        public int killsOnRecentlyHealedByAramPack;
        public int killsUnderOwnTurret;
        public int killsWithHelpFromEpicMonster;
        public int knockEnemyIntoTeamAndKill;
        public int landSkillShotsEarlyGame;
        public int laneMinionsFirst10Minutes;
        public int laningPhaseGoldExpAdvantage;
        public int legendaryCount;
        public ArrayList<Integer> legendaryItemUsed;
        public int lostAnInhibitor;
        public double maxCsAdvantageOnLaneOpponent;
        public int maxKillDeficit;
        public int maxLevelLeadLaneOpponent;
        public int mejaisFullStackInTime;
        public double moreEnemyJungleThanOpponent;
        public int multiKillOneSpell;
        public int multiTurretRiftHeraldCount;
        public int multikills;
        public int multikillsAfterAggressiveFlash;
        public int outerTurretExecutesBefore10Minutes;
        public int outnumberedKills;
        public int outnumberedNexusKill;
        public int perfectDragonSoulsTaken;
        public int perfectGame;
        public int pickKillWithAlly;
        public int playedChampSelectPosition;
        public int poroExplosions;
        public int quickCleanse;
        public int quickFirstTurret;
        public int quickSoloKills;
        public int riftHeraldTakedowns;
        public int saveAllyFromDeath;
        public int scuttleCrabKills;
        public int skillshotsDodged;
        public int skillshotsHit;
        public int snowballsHit;
        public int soloBaronKills;
        public int soloKills;
        public int soloTurretsLategame;
        public int stealthWardsPlaced;
        public int survivedSingleDigitHpCount;
        public int survivedThreeImmobilizesInFight;
        public int takedownOnFirstTurret;
        public int takedowns;
        public int takedownsAfterGainingLevelAdvantage;
        public int takedownsBeforeJungleMinionSpawn;
        public int takedownsFirstXMinutes;
        public int takedownsInAlcove;
        public int takedownsInEnemyFountain;
        public int teamBaronKills;
        public double teamDamagePercentage;
        public int teamElderDragonKills;
        public int teamRiftHeraldKills;
        public int tookLargeDamageSurvived;
        public int turretPlatesTaken;
        public int turretTakedowns;
        public int turretsTakenWithRiftHerald;
        public int twentyMinionsIn3SecondsCount;
        public int twoWardsOneSweeperCount;
        public int unseenRecalls;
        public double visionScoreAdvantageLaneOpponent;
        public double visionScorePerMinute;
        public int wardTakedowns;
        public int wardTakedownsBefore20M;
        public int wardsGuarded;
        public double earliestDragonTakedown;
        public int junglerKillsEarlyJungle;
        public int killsOnLanersEarlyJungleAsJungler;
        public int baronBuffGoldAdvantageOverThreshold;
        public double earliestBaron;
        public double firstTurretKilledTime;
        public int teleportTakedowns;
        public int highestChampionDamage;
        public int highestCrowdControlScore;
        public int highestWardKills;
    }

    @Data
    public static class Champion {
        public boolean first;
        public int kills;
    }

    @Data
    public static class Dragon {
        public boolean first;
        public int kills;
    }

    @Data
    public static class Horde {
        public boolean first;
        public int kills;
    }

    @Data
    public static class Info {
        public String endOfGameResult;
        public long gameCreation;
        public int gameDuration;
        public long gameEndTimestamp;
        public long gameId;
        public String gameMode;
        public String gameName;
        public long gameStartTimestamp;
        public String gameType;
        public String gameVersion;
        public int mapId;
        public ArrayList<Participant> participants;
        public String platformId;
        public int queueId;
        public ArrayList<Team> teams;
        public String tournamentCode;
    }

    @Data
    public static class Inhibitor {
        public boolean first;
        public int kills;
    }

    @Data
    public static class Metadata {
        public String dataVersion;
        public String matchId;
        public ArrayList<String> participants;
    }

    @Data
    public static class Missions {
        public int playerScore0;
        public int playerScore1;
        public int playerScore10;
        public int playerScore11;
        public int playerScore2;
        public int playerScore3;
        public int playerScore4;
        public int playerScore5;
        public int playerScore6;
        public int playerScore7;
        public int playerScore8;
        public int playerScore9;
        public int PlayerScore0;
        public int PlayerScore1;
        public int PlayerScore10;
        public int PlayerScore11;
        public int PlayerScore2;
        public int PlayerScore3;
        public int PlayerScore4;
        public int PlayerScore5;
        public int PlayerScore6;
        public int PlayerScore7;
        public int PlayerScore8;
        public int PlayerScore9;
    }

    @Data
    public static class Objectives {
        public Baron baron;
        public Champion champion;
        public Dragon dragon;
        public Horde horde;
        public Inhibitor inhibitor;
        public RiftHerald riftHerald;
        public Tower tower;
    }

    @Data
    public static class Participant {
        public int allInPings;
        public int assistMePings;
        public int assists;
        public int baronKills;
        public int basicPings;
        public int bountyLevel;
        public Challenges challenges;
        public int champExperience;
        public int champLevel;
        public int championId;
        public String championName;
        public int championTransform;
        public int commandPings;
        public int consumablesPurchased;
        public int damageDealtToBuildings;
        public int damageDealtToObjectives;
        public int damageDealtToTurrets;
        public int damageSelfMitigated;
        public int dangerPings;
        public int deaths;
        public int detectorWardsPlaced;
        public int doubleKills;
        public int dragonKills;
        public boolean eligibleForProgression;
        public int enemyMissingPings;
        public int enemyVisionPings;
        public boolean firstBloodAssist;
        public boolean firstBloodKill;
        public boolean firstTowerAssist;
        public boolean firstTowerKill;
        public boolean gameEndedInEarlySurrender;
        public boolean gameEndedInSurrender;
        public int getBackPings;
        public int goldEarned;
        public int goldSpent;
        public int holdPings;
        public String individualPosition;
        public int inhibitorKills;
        public int inhibitorTakedowns;
        public int inhibitorsLost;
        public int item0;
        public int item1;
        public int item2;
        public int item3;
        public int item4;
        public int item5;
        public int item6;
        public int itemsPurchased;
        public int killingSprees;
        public int kills;
        public String lane;
        public int largestCriticalStrike;
        public int largestKillingSpree;
        public int largestMultiKill;
        public int longestTimeSpentLiving;
        public int magicDamageDealt;
        public int magicDamageDealtToChampions;
        public int magicDamageTaken;
        public Missions missions;
        public int needVisionPings;
        public int neutralMinionsKilled;
        public int nexusKills;
        public int nexusLost;
        public int nexusTakedowns;
        public int objectivesStolen;
        public int objectivesStolenAssists;
        public int onMyWayPings;
        public int participantId;
        public int pentaKills;
        public Perks perks;
        public int physicalDamageDealt;
        public int physicalDamageDealtToChampions;
        public int physicalDamageTaken;
        public int placement;
        public int playerAugment1;
        public int playerAugment2;
        public int playerAugment3;
        public int playerAugment4;
        public int playerScore0;
        public int playerScore1;
        public int playerScore10;
        public int playerScore11;
        public int playerScore2;
        public int playerScore3;
        public int playerScore4;
        public int playerScore5;
        public int playerScore6;
        public int playerScore7;
        public int playerScore8;
        public int playerScore9;
        public int playerSubteamId;
        public int profileIcon;
        public int pushPings;
        public String puuid;
        public int quadraKills;
        public String riotIdGameName;
        public String riotIdTagline;
        public String role;
        public int sightWardsBoughtInGame;
        public int spell1Casts;
        public int spell2Casts;
        public int spell3Casts;
        public int spell4Casts;
        public int subteamPlacement;
        public int summoner1Casts;
        public int summoner1Id;
        public int summoner2Casts;
        public int summoner2Id;
        public String summonerId;
        public int summonerLevel;
        public String summonerName;
        public boolean teamEarlySurrendered;
        public int teamId;
        public String teamPosition;
        public int timeCCingOthers;
        public int timePlayed;
        public int totalAllyJungleMinionsKilled;
        public int totalDamageDealt;
        public int totalDamageDealtToChampions;
        public int totalDamageShieldedOnTeammates;
        public int totalDamageTaken;
        public int totalEnemyJungleMinionsKilled;
        public int totalHeal;
        public int totalHealsOnTeammates;
        public int totalMinionsKilled;
        public int totalTimeCCDealt;
        public int totalTimeSpentDead;
        public int totalUnitsHealed;
        public int tripleKills;
        public int trueDamageDealt;
        public int trueDamageDealtToChampions;
        public int trueDamageTaken;
        public int turretKills;
        public int turretTakedowns;
        public int turretsLost;
        public int unrealKills;
        public int visionClearedPings;
        public int visionScore;
        public int visionWardsBoughtInGame;
        public int wardsKilled;
        public int wardsPlaced;
        public boolean win;
    }

    @Data
    public static class Perks {
        public StatPerks statPerks;
        public ArrayList<Style> styles;
    }

    @Data
    public static class RiftHerald {
        public boolean first;
        public int kills;
    }


    @Data
    public static class Selection {
        public int perk;
        public int var1;
        public int var2;
        public int var3;
    }

    @Data
    public static class StatPerks {
        public int defense;
        public int flex;
        public int offense;
    }

    @Data
    public static class Style {
        public String description;
        public ArrayList<Selection> selections;
        public int style;
    }

    @Data
    public static class Team {
        public ArrayList<Ban> bans;
        public Objectives objectives;
        public int teamId;
        public boolean win;
    }

    @Data
    public static class Tower {
        public boolean first;
        public int kills;
    }
}
