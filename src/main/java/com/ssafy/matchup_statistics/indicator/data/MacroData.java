package com.ssafy.matchup_statistics.indicator.data;

import com.ssafy.matchup_statistics.global.dto.response.MatchDetailResponseDto;
import com.ssafy.matchup_statistics.indicator.entity.match.LaneInfo;
import com.ssafy.matchup_statistics.indicator.entity.match.TimeInfo;
import lombok.Data;

@Data
public class MacroData {
    MatchDetailResponseDto.Participant myData;
    TeamData teamData;
    private TimeInfo timeInfo;

    public MacroData(LaneInfo laneInfo, MatchDetailResponseDto matchDetailResponseDto) {
        timeInfo = new TimeInfo(matchDetailResponseDto.getInfo().gameDuration,
                matchDetailResponseDto.getInfo().getGameStartTimestamp(),
                matchDetailResponseDto.getInfo().getGameEndTimestamp());
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
                    if (t.objectives.baron != null) myTeamGetObjectives += t.objectives.baron.kills;
                    if (t.objectives.dragon != null) myTeamGetObjectives += t.objectives.dragon.kills;
                    if (t.objectives.horde != null) myTeamGetObjectives += t.objectives.horde.kills;
                    if (t.objectives.riftHerald != null) myTeamGetObjectives += t.objectives.riftHerald.kills;
                } else {
                    if (t.objectives.baron != null) oppositeTeamGetObjectives += t.objectives.baron.kills;
                    if (t.objectives.dragon != null) oppositeTeamGetObjectives += t.objectives.dragon.kills;
                    if (t.objectives.horde != null) oppositeTeamGetObjectives += t.objectives.horde.kills;
                    if (t.objectives.riftHerald != null) oppositeTeamGetObjectives += t.objectives.riftHerald.kills;
                }
            });
        }
    }
}
