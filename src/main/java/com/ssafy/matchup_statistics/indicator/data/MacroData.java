package com.ssafy.matchup_statistics.indicator.data;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.match.LaneInfo;
import lombok.Data;

@Data
public class MacroData {
    MatchDetailResponseDto.Participant myData;
    TeamData teamData;
    private long gameDuration;

    public MacroData(LaneInfo laneInfo, MatchDetailResponseDto matchDetailResponseDto) {
        gameDuration = matchDetailResponseDto.getInfo().gameDuration;
        myData = matchDetailResponseDto.getInfo().participants.get(laneInfo.getMyLaneNumber() - 1);
        teamData = new TeamData(laneInfo, matchDetailResponseDto);
    }

    @Data
    public static class TeamData {
        private int teamDamageDealtToTurrets;
        private int teamTotalDamageTaken;
        private int myTeamGetObjectives;
        private int oppositeTeamGetObjectives;

        public TeamData(LaneInfo laneInfo, MatchDetailResponseDto matchDetailResponseDto) {
            matchDetailResponseDto.getInfo().participants.forEach(p -> {
                if (p.teamId != laneInfo.getMyTeamId()) return;
                teamDamageDealtToTurrets += p.damageDealtToTurrets;
                teamTotalDamageTaken += p.totalDamageTaken;
            });
            matchDetailResponseDto.getInfo().teams.forEach(t -> {
                if (t.teamId == laneInfo.getMyTeamId()) {
                    myTeamGetObjectives += t.objectives.baron.kills;
                    myTeamGetObjectives += t.objectives.dragon.kills;
                    myTeamGetObjectives += t.objectives.horde.kills;
                    myTeamGetObjectives += t.objectives.riftHerald.kills;
                } else {
                    oppositeTeamGetObjectives += t.objectives.baron.kills;
                    oppositeTeamGetObjectives += t.objectives.dragon.kills;
                    oppositeTeamGetObjectives += t.objectives.horde.kills;
                    oppositeTeamGetObjectives += t.objectives.riftHerald.kills;
                }
            });
        }
    }
}
