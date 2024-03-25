package com.ssafy.matchup.user.main.service.sub;

import com.ssafy.matchup.user.main.api.dto.LeagueInfoDto;
import com.ssafy.matchup.user.main.api.dto.SummonerInfoDto;
import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueInfoResponseDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import com.ssafy.matchup.user.main.repository.UserRepository;
import com.ssafy.matchup.user.riotaccount.entity.RiotAccount;
import com.ssafy.matchup.user.riotaccount.entity.SummonerProfile;
import com.ssafy.matchup.user.riotaccount.respository.RiotAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitUserService {

    @Value("${ddragon.version}")
    String ddragonVersion = "";

    private final UserRepository userRepository;
    private final RiotAccountRepository riotAccountRepository;

    @Transactional
    public User initUser(RegistUserRequestDto registUserRequestDto) {
        User user = User.builder()
                .snsType(registUserRequestDto.getSnsType())
                .snsId(registUserRequestDto.getSnsId())
                .role(AuthorityType.ROLE_USER)
                .build();
        userRepository.save(user);
        return user;
    }


    @Transactional
    public RiotAccount initRiotAccount(RegistUserRequestDto registUserRequestDto, SummonerLeagueInfoResponseDto summonerLeagueInfoResponseDto) {
        String summonerName = registUserRequestDto.getSummonerName();
        SummonerInfoDto summonerInfoDto = summonerLeagueInfoResponseDto.getSummonerInfoDto();
        LeagueInfoDto leagueInfoDto = summonerLeagueInfoResponseDto.getLeagueInfoDto();
        String[] parts = summonerName.split("#");
        String tag = parts[1].trim();
        String name = summonerInfoDto.getName();

        SummonerProfile summonerProfile = SummonerProfile.builder()
                .name(name)
                .tag(tag)
                .iconUrl("https://ddragon.leagueoflegends.com/cdn/" + ddragonVersion
                        + "/img/profileicon/" + summonerInfoDto.getProfileIconId() + ".png")
                .level(summonerInfoDto.getSummonerLevel()).build();


        RiotAccount riotAccount = RiotAccount.builder()
                .id(summonerInfoDto.getId())
                .revisionDate(summonerInfoDto.getRevisionDate())
                .summonerProfile(summonerProfile)
                .tier(leagueInfoDto.getTier())
                .leagueRank(leagueInfoDto.getRank())
                .leaguePoint(leagueInfoDto.getLeaguePoints())
                .build();

        riotAccountRepository.save(riotAccount);
        return riotAccount;
    }

}
