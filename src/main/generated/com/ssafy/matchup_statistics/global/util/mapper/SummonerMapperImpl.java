package com.ssafy.matchup_statistics.global.util.mapper;

import com.ssafy.matchup_statistics.global.dto.response.SummonerInfoResponseDto;
import com.ssafy.matchup_statistics.summoner.entity.SummonerDetail;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-27T11:54:03+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class SummonerMapperImpl implements SummonerMapper {

    @Override
    public SummonerDetail summonerInfoResponseDtoToSummonerDetail(SummonerInfoResponseDto summonerInfoResponseDto) {
        if ( summonerInfoResponseDto == null ) {
            return null;
        }

        String puuid = null;
        String accountId = null;
        String name = null;
        Integer profileIconId = null;
        Long revisionDate = null;
        Long summonerLevel = null;

        puuid = summonerInfoResponseDto.getPuuid();
        accountId = summonerInfoResponseDto.getAccountId();
        name = summonerInfoResponseDto.getName();
        profileIconId = summonerInfoResponseDto.getProfileIconId();
        revisionDate = summonerInfoResponseDto.getRevisionDate();
        summonerLevel = summonerInfoResponseDto.getSummonerLevel();

        SummonerDetail summonerDetail = new SummonerDetail( puuid, accountId, name, profileIconId, revisionDate, summonerLevel );

        return summonerDetail;
    }
}
