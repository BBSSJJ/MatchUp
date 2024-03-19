package com.ssafy.matchup_statistics.indicator.service.builder.riot.match.beginning;

import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.DetailLaneIndicator;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.LaneIndicator;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import org.springframework.stereotype.Component;

@Component
public class LaneIndicatorBuilder {
    public DetailLaneIndicator build(boolean isBottomLane,
                                     int myLaneNumber,
                                     int oppositeLaneNumber,
                                     int[] bottomDuoLaneNumbers,
                                     MatchTimelineResponseDto matchTimelineDto) {
        DetailLaneIndicator detailLaneIndicator = new DetailLaneIndicator();

        // 15분 이전 종료여부 확인
        checkIsFinishedBeforeFifteen(matchTimelineDto, detailLaneIndicator);

        // 기본 지표 생성
        buildDefaultBase(myLaneNumber, oppositeLaneNumber, matchTimelineDto, detailLaneIndicator);

        // 바텀 지표 생성
        buildBottomBase(isBottomLane, myLaneNumber, bottomDuoLaneNumbers, matchTimelineDto, detailLaneIndicator);

        return detailLaneIndicator;
    }
    public void add(LaneIndicator laneIndicator, DetailLaneIndicator detailLaneIndicator) {
    }

    protected void checkIsFinishedBeforeFifteen(MatchTimelineResponseDto matchTimelineDto, DetailLaneIndicator detailLaneIndicator) {
    }
    protected void buildDefaultBase(int myLaneNumber, int oppositeLaneNumber, MatchTimelineResponseDto matchTimelineDto, DetailLaneIndicator detailLaneIndicator) {
    }
    protected void buildBottomBase(boolean isBottomlane, int myLaneNumber, int[] bottomDuoLaneNumbers, MatchTimelineResponseDto matchTimelineDto, DetailLaneIndicator detailLaneIndicator) {
    }

}
