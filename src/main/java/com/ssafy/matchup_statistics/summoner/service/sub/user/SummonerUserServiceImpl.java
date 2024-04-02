package com.ssafy.matchup_statistics.summoner.service.sub.user;

import com.ssafy.matchup_statistics.global.api.flux.RiotWebClientFactory;
import com.ssafy.matchup_statistics.global.dao.UserDao;
import com.ssafy.matchup_statistics.global.dto.jpa.UserRiotAccountDto;
import com.ssafy.matchup_statistics.global.dto.response.AccountResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.global.exception.RiotApiError;
import com.ssafy.matchup_statistics.global.exception.RiotApiException;
import com.ssafy.matchup_statistics.summoner.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.service.builder.SummonerBuilder;
import com.ssafy.matchup_statistics.summoner.service.sub.renewal.SummonerRenewalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SummonerUserServiceImpl implements SummonerUserService {

    private final SummonerBuilder summonerBuilder;
    private final SummonerRenewalService summonerRenewalService;
    private final RiotWebClientFactory riotWebClientFactory;
    private final UserDao userDao;

    @Override
    public SummonerLeagueAccountInfoResponseDto registSummoner(String gameName, String tagLine) {

        AccountResponseDto accountInfo = riotWebClientFactory.getAccountInfo(gameName, tagLine).block();
        if (accountInfo == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);
        log.debug("account info : {}", accountInfo);

        SummonerInfoResponseDto summonerInfo = riotWebClientFactory.getSummonerInfoResponseDtoByPuuid(accountInfo.getPuuid()).block();
        if (summonerInfo == null) throw new RiotApiException(RiotApiError.NOT_IN_RIOT_API);
        log.debug("summonerInfo : {}", summonerInfo);

        LeagueInfoResponseDto leagueInfo = riotWebClientFactory.getLeagueInfoResponseBySummonerId(summonerInfo.getId()).blockFirst();
        if (leagueInfo == null) leagueInfo = new LeagueInfoResponseDto();
        log.debug("leagueInfo : {}", leagueInfo);

        summonerBuilder.buildAsync(gameName, tagLine);

        log.debug("account info : {}", accountInfo);
        return new SummonerLeagueAccountInfoResponseDto(summonerInfo, leagueInfo, accountInfo);
    }

    @Override
    public SummonerLeagueAccountInfoResponseDto loginSummoner(String gameName, String tagLine) {
        // 소환사 id로 소환사 불러오기
        Summoner summoner = summonerBuilder.build(gameName, tagLine);

        // 로그인 시 갱신 1회 수행
        summonerRenewalService.renew(summoner);

        return new SummonerLeagueAccountInfoResponseDto(summoner);
    }

    @Override
    public SummonerLeagueAccountInfoResponseDto loginSummoner(Long userId) {

        // DB에서 소환사 불러오기
        UserRiotAccountDto userRiotAccountDto = userDao.getUserRiotAccountDto(userId);

        // 소환사 id로 소환사 불러오기
        return loginSummoner(userRiotAccountDto.getRiotAccountInfo().getSummonerProfile().getName(), userRiotAccountDto.getRiotAccountInfo().getSummonerProfile().getTag());
    }
}