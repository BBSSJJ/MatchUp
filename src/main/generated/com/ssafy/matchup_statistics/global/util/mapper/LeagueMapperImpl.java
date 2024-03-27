package com.ssafy.matchup_statistics.global.util.mapper;

import com.ssafy.matchup_statistics.global.dto.response.LeagueInfoResponseDto;
import com.ssafy.matchup_statistics.league.entity.League;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-27T11:54:03+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class LeagueMapperImpl implements LeagueMapper {

    @Override
    public League leagueInfoResponseDtoToLeague(LeagueInfoResponseDto leagueInfoResponseDto) {
        if ( leagueInfoResponseDto == null ) {
            return null;
        }

        League.LeagueBuilder league = League.builder();

        league.leagueId( leagueInfoResponseDto.getLeagueId() );
        league.queueType( leagueInfoResponseDto.getQueueType() );
        league.tier( leagueInfoResponseDto.getTier() );
        league.rank( leagueInfoResponseDto.getRank() );
        league.summonerId( leagueInfoResponseDto.getSummonerId() );
        league.summonerName( leagueInfoResponseDto.getSummonerName() );
        league.leaguePoints( String.valueOf( leagueInfoResponseDto.getLeaguePoints() ) );
        league.wins( String.valueOf( leagueInfoResponseDto.getWins() ) );
        league.losses( String.valueOf( leagueInfoResponseDto.getLosses() ) );
        league.veteran( String.valueOf( leagueInfoResponseDto.isVeteran() ) );
        league.inactive( String.valueOf( leagueInfoResponseDto.isInactive() ) );
        league.freshBlood( String.valueOf( leagueInfoResponseDto.isFreshBlood() ) );
        league.hotStreak( String.valueOf( leagueInfoResponseDto.isHotStreak() ) );

        return league.build();
    }
}
