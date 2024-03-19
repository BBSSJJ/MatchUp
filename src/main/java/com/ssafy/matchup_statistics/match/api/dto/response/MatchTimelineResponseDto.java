package com.ssafy.matchup_statistics.match.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MatchTimelineResponseDto {
    public Metadata metadata;
    public Info info;

    @Data
    public static class _1 {
        public ChampionStats championStats;
        public int currentGold;
        public DamageStats damageStats;
        public int goldPerSecond;
        public int jungleMinionsKilled;
        public int level;
        public int minionsKilled;
        public int participantId;
        public Position position;
        public int timeEnemySpentControlled;
        public int totalGold;
        public int xp;
    }

    @Data
    public static class _10 {
        public ChampionStats championStats;
        public int currentGold;
        public DamageStats damageStats;
        public int goldPerSecond;
        public int jungleMinionsKilled;
        public int level;
        public int minionsKilled;
        public int participantId;
        public Position position;
        public int timeEnemySpentControlled;
        public int totalGold;
        public int xp;
    }

    @Data
    public static class _2 {
        public ChampionStats championStats;
        public int currentGold;
        public DamageStats damageStats;
        public int goldPerSecond;
        public int jungleMinionsKilled;
        public int level;
        public int minionsKilled;
        public int participantId;
        public Position position;
        public int timeEnemySpentControlled;
        public int totalGold;
        public int xp;
    }

    @Data
    public static class _3 {
        public ChampionStats championStats;
        public int currentGold;
        public DamageStats damageStats;
        public int goldPerSecond;
        public int jungleMinionsKilled;
        public int level;
        public int minionsKilled;
        public int participantId;
        public Position position;
        public int timeEnemySpentControlled;
        public int totalGold;
        public int xp;
    }

    @Data
    public static class _4 {
        public ChampionStats championStats;
        public int currentGold;
        public DamageStats damageStats;
        public int goldPerSecond;
        public int jungleMinionsKilled;
        public int level;
        public int minionsKilled;
        public int participantId;
        public Position position;
        public int timeEnemySpentControlled;
        public int totalGold;
        public int xp;
    }

    @Data
    public static class _5 {
        public ChampionStats championStats;
        public int currentGold;
        public DamageStats damageStats;
        public int goldPerSecond;
        public int jungleMinionsKilled;
        public int level;
        public int minionsKilled;
        public int participantId;
        public Position position;
        public int timeEnemySpentControlled;
        public int totalGold;
        public int xp;
    }

    @Data
    public static class _6 {
        public ChampionStats championStats;
        public int currentGold;
        public DamageStats damageStats;
        public int goldPerSecond;
        public int jungleMinionsKilled;
        public int level;
        public int minionsKilled;
        public int participantId;
        public Position position;
        public int timeEnemySpentControlled;
        public int totalGold;
        public int xp;
    }

    @Data
    public static class _7 {
        public ChampionStats championStats;
        public int currentGold;
        public DamageStats damageStats;
        public int goldPerSecond;
        public int jungleMinionsKilled;
        public int level;
        public int minionsKilled;
        public int participantId;
        public Position position;
        public int timeEnemySpentControlled;
        public int totalGold;
        public int xp;
    }

    @Data
    public static class _8 {
        public ChampionStats championStats;
        public int currentGold;
        public DamageStats damageStats;
        public int goldPerSecond;
        public int jungleMinionsKilled;
        public int level;
        public int minionsKilled;
        public int participantId;
        public Position position;
        public int timeEnemySpentControlled;
        public int totalGold;
        public int xp;
    }

    @Data
    public static class _9 {
        public ChampionStats championStats;
        public int currentGold;
        public DamageStats damageStats;
        public int goldPerSecond;
        public int jungleMinionsKilled;
        public int level;
        public int minionsKilled;
        public int participantId;
        public Position position;
        public int timeEnemySpentControlled;
        public int totalGold;
        public int xp;
    }

    @Data
    public static class ChampionStats {
        public int abilityHaste;
        public int abilityPower;
        public int armor;
        public int armorPen;
        public int armorPenPercent;
        public int attackDamage;
        public int attackSpeed;
        public int bonusArmorPenPercent;
        public int bonusMagicPenPercent;
        public int ccReduction;
        public int cooldownReduction;
        public int health;
        public int healthMax;
        public int healthRegen;
        public int lifesteal;
        public int magicPen;
        public int magicPenPercent;
        public int magicResist;
        public int movementSpeed;
        public int omnivamp;
        public int physicalVamp;
        public int power;
        public int powerMax;
        public int powerRegen;
        public int spellVamp;
    }

    @Data
    public static class DamageStats {
        public int magicDamageDone;
        public int magicDamageDoneToChampions;
        public int magicDamageTaken;
        public int physicalDamageDone;
        public int physicalDamageDoneToChampions;
        public int physicalDamageTaken;
        public int totalDamageDone;
        public int totalDamageDoneToChampions;
        public int totalDamageTaken;
        public int trueDamageDone;
        public int trueDamageDoneToChampions;
        public int trueDamageTaken;
    }

    @Data
    public static class Event {
        public Object realTimestamp;
        public int timestamp;
        public String type;
        public int itemId;
        public int participantId;
        public String levelUpType;
        public int skillSlot;
        public int creatorId;
        public String wardType;
        public int level;
        public ArrayList<Integer> assistingParticipantIds;
        public int bounty;
        public int killStreakLength;
        public int killerId;
        public Position position;
        public int shutdownBounty;
        public ArrayList<VictimDamageDealt> victimDamageDealt;
        public ArrayList<VictimDamageReceived> victimDamageReceived;
        public int victimId;
        public String killType;
        public String laneType;
        public int teamId;
        public int killerTeamId;
        public String monsterType;
        public String monsterSubType;
        public int afterId;
        public int beforeId;
        public int goldGain;
        public int multiKillLength;
        public String buildingType;
        public String towerType;
        public long gameId;
        public int winningTeam;
    }

    @Data
    public static class Frame {
        public ArrayList<Event> events;
        public ParticipantFrames participantFrames;
        public int timestamp;
    }

    @Data
    public static class Info {
        public String endOfGameResult;
        public int frameInterval;
        public ArrayList<Frame> frames;
        public long gameId;
        public ArrayList<Participant> participants;
    }

    @Data
    public static class Metadata {
        public String dataVersion;
        public String matchId;
        public ArrayList<String> participants;
    }

    @Data
    public static class Participant {
        public int participantId;
        public String puuid;
    }

    @Data
    public static class ParticipantFrames {
        @JsonProperty("1")
        public _1 _1;
        @JsonProperty("2")
        public _2 _2;
        @JsonProperty("3")
        public _3 _3;
        @JsonProperty("4")
        public _4 _4;
        @JsonProperty("5")
        public _5 _5;
        @JsonProperty("6")
        public _6 _6;
        @JsonProperty("7")
        public _7 _7;
        @JsonProperty("8")
        public _8 _8;
        @JsonProperty("9")
        public _9 _9;
        @JsonProperty("10")
        public _10 _10;
    }

    @Data
    public static class Position {
        public int x;
        public int y;
    }

    @Data
    public static class VictimDamageDealt {
        public boolean basic;
        public int magicDamage;
        public String name;
        public int participantId;
        public int physicalDamage;
        public String spellName;
        public int spellSlot;
        public int trueDamage;
        public String type;
    }

    @Data
    public static class VictimDamageReceived {
        public boolean basic;
        public int magicDamage;
        public String name;
        public int participantId;
        public int physicalDamage;
        public String spellName;
        public int spellSlot;
        public int trueDamage;
        public String type;
    }

}
