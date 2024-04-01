package com.ssafy.matchup_statistics.summoner.service.sub.renewal;

import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SummonerRenewalServiceImpl implements SummonerRenewalService{

    @Override
    public void renew(Summoner summoner) {
        // 사용자 최근게임 정보(Indicator.matches) DB에서 조회

        // 현재 라이엇에 있는 전적정보와 비교

        // 없는 게임이 있다면 해당게임 정보 불러와서 전적정보에 더하기
    }

    private boolean isMatchToRenew(List<String> matchInDB, String matchInRiot) {
        return false;
    }
}
