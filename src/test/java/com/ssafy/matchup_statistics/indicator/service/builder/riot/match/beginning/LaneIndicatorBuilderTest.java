package com.ssafy.matchup_statistics.indicator.service.builder.riot.match.beginning;

import com.ssafy.matchup_statistics.global.config.TestConfiguration;
import com.ssafy.matchup_statistics.indicator.entity.riot.match.beginning.DetailLaneIndicator;
import com.ssafy.matchup_statistics.match.api.dto.response.MatchTimelineResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.Target;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
class LaneIndicatorBuilderTest {

    @Mock
    LaneIndicatorBuilder target;

    @Autowired
    MatchTimelineResponseDto mockMatchTimelineResponseDto;

    @Test
    @DisplayName("타임라인 객체 테스트")
    void mockTimelineObjectTest() {
        // given
        assertThat(mockMatchTimelineResponseDto
                .getInfo()
                .getFrames()
                .get(0)
                .getParticipantFrames()._1.currentGold).isEqualTo(500);
        // when

        // then
    }

//    @Test
//    @DisplayName("15분 이전에 끝났으면 해당 매치는 비정상 종료로 표기")
//    void checkIsFinishedBeforeFifteenTest(){
//        // given
//        // 생성할 LaneIndicator
//        DetailLaneIndicator laneIndicatorBeforeFifteen = new DetailLaneIndicator();
//        DetailLaneIndicator laneIndicatorAfterFifteen = new DetailLaneIndicator();
//
//        // 15분 이전게임
//        MatchTimelineResponseDto matchTimelineDtoBeforeFifteen = new MatchTimelineResponseDto();
//        MatchTimelineResponseDto.Info info1 = new MatchTimelineResponseDto.Info();
//        List<MatchTimelineResponseDto.Info.Frame> frames1 = new ArrayList<>();
//        for (long i = 0; i < 15; i++) {
//            MatchTimelineResponseDto.Info.Frame frame = new MatchTimelineResponseDto.Info.Frame();
//            frame.setTimestamp(i * 60000L);
//            frames1.add(frame);
//        }
//        info1.setFrames(frames1);
//
//        // 15분 이후게임
//        MatchTimelineResponseDto matchTimelineDtoAfterFifteen = new MatchTimelineResponseDto();
//        MatchTimelineResponseDto.Info info2 = new MatchTimelineResponseDto.Info();
//        List<MatchTimelineResponseDto.Info.Frame> frames2 = new ArrayList<>();
//        for (long i = 0; i <= 15; i++) {
//            MatchTimelineResponseDto.Info.Frame frame = new MatchTimelineResponseDto.Info.Frame();
//            frame.setTimestamp(i * 60000L);
//            frames1.add(frame);
//        }
//        info2.setFrames(frames2);
//
//        // when
//        target.checkIsFinishedBeforeFifteen(matchTimelineDtoBeforeFifteen, laneIndicatorBeforeFifteen);
//        target.checkIsFinishedBeforeFifteen(matchTimelineDtoAfterFifteen, laneIndicatorAfterFifteen);
//
//        // then
//        assertThat(laneIndicatorBeforeFifteen.getIsFinishedBeforeFifteen()).isTrue();
//        assertThat(laneIndicatorAfterFifteen.getIsFinishedBeforeFifteen()).isFalse();
//    }
//
//    @Test
//    @DisplayName("목표 프레임 내부에서 내 데이터 꺼내서 상대방 데이터와 비교 후 저장")
//    void buildDefaultBaseTest(){
//        // given
//        MatchTimelineResponseDto matchTimelineDto = new MatchTimelineResponseDto();
//        MatchTimelineResponseDto.Info info = new MatchTimelineResponseDto.Info();
//        List<MatchTimelineResponseDto.Info.Frame> frames = new ArrayList<>();
//
//        // 15분 이전 frame 채워넣음
//        for (long i = 0; i < 15; i++) {
//            MatchTimelineResponseDto.Info.Frame frame = new MatchTimelineResponseDto.Info.Frame();
//            frame.setTimestamp(i * 60000L);
//            frames.add(frame);
//        }
//
//        // 15분 frame
//        MatchTimelineResponseDto.Info.Frame frame = new MatchTimelineResponseDto.Info.Frame();
//        frame.setTimestamp(15L * 60000L);
//
//        // 내 프레임 : 탑, cs 100개
//        int myLaneNumber = 1;
//        MatchTimelineResponseDto.Info.Frame.ParticipantFrame participantFrames = new MatchTimelineResponseDto.Info.Frame.ParticipantFrame();
//        MatchTimelineResponseDto.Info.Frame.ParticipantFrame.ParticipantStatus myInfo = new MatchTimelineResponseDto.Info.Frame.ParticipantFrame.ParticipantStatus();
//        myInfo.setMinionsKilled(100L);
//        participantFrames.setParticipantNumberOne(myInfo);
//
//        // 상대방 프레임 : 탑, cs 120개
//        int oppositeLaneNumber = 1;
//        MatchTimelineResponseDto.Info.Frame.ParticipantFrame.ParticipantStatus oppositeInfo = new MatchTimelineResponseDto.Info.Frame.ParticipantFrame.ParticipantStatus();
//        oppositeInfo.setMinionsKilled(120L);
//        participantFrames.setParticipantNumberSix(oppositeInfo);
//
//        frame.setParticipantFrames(participantFrames);
//        info.setFrames(frames);
//        matchTimelineDto.setInfo(info);
//
//        DetailLaneIndicator detailLaneIndicator = new DetailLaneIndicator();
//
//        // when
//        target.buildDefaultBase(myLaneNumber, oppositeLaneNumber, matchTimelineDto, detailLaneIndicator);
//
//        // then
//        assertThat(detailLaneIndicator.getBasicWeight().getCsDiffer()).isEqualTo(-20L);
//    }
//
//    @Test
//    @DisplayName("현재 내 포지션이 바텀이면 아군 바텀정보도 같이 저장")
//    void buildBottomBaseTest(){
//        // given
//        MatchTimelineResponseDto matchTimelineDto = new MatchTimelineResponseDto();
//        MatchTimelineResponseDto.Info info = new MatchTimelineResponseDto.Info();
//        List<MatchTimelineResponseDto.Info.Frame> frames = new ArrayList<>();
//
//        int myLaneNumber = 4;
//        int oppositeLaneNumber = 1;
//        // 15분 이전 frame 채워넣음
//        for (long i = 1; i < 15; i++) {
//            MatchTimelineResponseDto.Info.Frame frame = new MatchTimelineResponseDto.Info.Frame();
//            frame.setTimestamp(i * 60000L);
//
//            // 상대 원딜 14번 죽임
//            makeKillEvent(4, 9, 5, frame, frames);
//        }
//
//        // 15분 frame
//        MatchTimelineResponseDto.Info.Frame frame = new MatchTimelineResponseDto.Info.Frame();
//        frame.setTimestamp(15L * 60000L);
//
//        // 상대 원딜이 우리 서폿 1번 죽임
//        makeKillEvent(9, 5, 10, frame, frames);
//
//        info.setFrames(frames);
//        matchTimelineDto.setInfo(info);
//
//        DetailLaneIndicator detailLaneIndicator = new DetailLaneIndicator();
//
//        // when
//        target.buildDefaultBase(myLaneNumber, oppositeLaneNumber, matchTimelineDto, detailLaneIndicator);
//
//        // then
//        assertThat(detailLaneIndicator.getAggresiveLaneAbilility().getDuoKillAndSoloKillDiffer()).isEqualTo(13L);
//    }
//
//    @Test
//    @DisplayName("라인 지표 정상적으로 생성되는지 확인")
//    void buildTest() {
//        // given
//        boolean isBottomLane = false;
//        int myLaneNumber = 4;
//        int oppositeLaneNumber = 9;
//        int[] oppositeBottomDuoLaneNumbers = new int[] {9, 10};
//
//        MatchTimelineResponseDto matchTimelineDto = new MatchTimelineResponseDto();
//        MatchTimelineResponseDto.Info info = new MatchTimelineResponseDto.Info();
//        List<MatchTimelineResponseDto.Info.Frame> frames = new ArrayList<>();
//
//        // 15분 frame에 골드차이 채우기
//
//        info.setFrames(frames);
//        matchTimelineDto.setInfo(info);
//
//        // when
//        DetailLaneIndicator laneIndicator = target.build(isBottomLane, myLaneNumber, oppositeLaneNumber, oppositeBottomDuoLaneNumbers, matchTimelineDto);
//
//        // then
//        assertThat(laneIndicator.getBasicWeight().getTowerGoldDiffer()).isEqualTo(500);
//    }
//
//    @Test
//    @DisplayName("라인 지표에 세부지표 잘 추가하는지 확인")
//    void addTest() {
//
//    }

//    private static void makeKillEvent(int killerId, int victimId, int e, MatchTimelineResponseDto.Info.Frame frame, List<MatchTimelineResponseDto.Info.Frame> frames) {
//        List<MatchTimelineResponseDto.Info..Event> killEvents = new ArrayList<>();
//        MatchTimelineResponseDto.Info.Frame.Event killEvent = new MatchTimelineResponseDto.Info.Frame.Event();
//        killEvent.setKillerId(killerId);
//        killEvent.setVictimId(victimId);
//        List<Integer> assistantingParticipantId = new ArrayList<>();
//        assistantingParticipantId.add(e);
//        killEvent.setAssistantingParticipantIds(assistantingParticipantId);
//        killEvents.add(killEvent);
//        frame.setKillEvents(killEvents);
//        frames.add(frame);
//    }
}