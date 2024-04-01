package com.ssafy.matchup_statistics.summoner.service.sub.detail;

import com.ssafy.matchup_statistics.global.dao.UserDao;
import com.ssafy.matchup_statistics.global.dto.jpa.UserRiotAccountDto;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.summoner.dao.SummonerDao;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerDetailInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.service.builder.SummonerBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SummonerDetailServiceImpl implements SummonerDetailService{

    private final SummonerDao summonerDao;
    private final UserDao userDao;

    @Override
    public SummonerDetailInfoResponseDto getSummonerDetailInfo(String gameName, String tagLine) {
        Summoner summonerInDB = summonerDao.getSummonerInDB(gameName, tagLine);
        Indicator indicatorInDB = summonerDao.getIndicatorInDB(summonerInDB.getId());

        return new SummonerDetailInfoResponseDto(summonerInDB, indicatorInDB);
    }

    @Override
    public SummonerDetailInfoResponseDto getSummonerDetailInfo(Long userId) {
        UserRiotAccountDto userRiotAccountDto = userDao.getUserRiotAccountDto(userId);
        return getSummonerDetailInfo(userRiotAccountDto.getRiotAccountInfo().getSummonerProfile().getName(),
                userRiotAccountDto.getRiotAccountInfo().getSummonerProfile().getTag());
    }
}
