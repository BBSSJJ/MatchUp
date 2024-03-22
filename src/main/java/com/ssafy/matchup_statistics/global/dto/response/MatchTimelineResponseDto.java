package com.ssafy.matchup_statistics.global.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.matchup_statistics.global.exception.RiotDataError;
import com.ssafy.matchup_statistics.global.exception.RiotDataException;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MatchTimelineResponseDto {
    public Metadata metadata;
    public Info info;

    @Data
    public static class ParticipantNumber {
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
        public int actualStartTime;
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
        public ParticipantNumber _1;
        @JsonProperty("2")
        public ParticipantNumber _2;
        @JsonProperty("3")
        public ParticipantNumber _3;
        @JsonProperty("4")
        public ParticipantNumber _4;
        @JsonProperty("5")
        public ParticipantNumber _5;
        @JsonProperty("6")
        public ParticipantNumber _6;
        @JsonProperty("7")
        public ParticipantNumber _7;
        @JsonProperty("8")
        public ParticipantNumber _8;
        @JsonProperty("9")
        public ParticipantNumber _9;
        @JsonProperty("10")
        public ParticipantNumber _10;

        public ParticipantNumber getDataByNumber(int number) {
            return switch (number) {
                case 1 -> _1;
                case 2 -> _2;
                case 3 -> _3;
                case 4 -> _4;
                case 5 -> _5;
                case 6 -> _6;
                case 7 -> _7;
                case 8 -> _8;
                case 9 -> _9;
                case 10 -> _10;
                default -> throw new RiotDataException(RiotDataError.ILLEGAL_LANE_NUMBER_ERROR);
            };
        }
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
