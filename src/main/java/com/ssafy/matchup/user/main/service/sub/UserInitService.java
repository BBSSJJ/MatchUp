package com.ssafy.matchup.user.main.service.sub;

import com.ssafy.matchup.user.main.api.dto.response.AccountResponseDto;
import com.ssafy.matchup.user.main.api.dto.response.LeagueInfoDto;
import com.ssafy.matchup.user.main.api.dto.response.SummonerInfoDto;
import com.ssafy.matchup.user.main.api.dto.response.SummonerLeagueAccountInfoResponseDto;
import com.ssafy.matchup.user.main.dto.request.RegistUserRequestDto;
import com.ssafy.matchup.user.main.entity.Setting;
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
public class UserInitService {

    @Value("${ddragon.version}")
    String ddragonVersion;

    private final UserRepository userRepository;
    private final RiotAccountRepository riotAccountRepository;

    @Transactional
    public User initUser(RegistUserRequestDto registUserRequestDto) {
        Setting setting = Setting.builder().useMike(false).build();
        User user = User.builder()
                .snsType(registUserRequestDto.getSnsType())
                .snsId(registUserRequestDto.getSnsId())
                .role(AuthorityType.ROLE_USER)
                .setting(setting)
                .build();
        userRepository.save(user);
        return user;
    }


    @Transactional
    public RiotAccount initRiotAccount(SummonerLeagueAccountInfoResponseDto summonerLeagueAccountInfoResponseDto) {
        SummonerInfoDto summonerInfoDto = summonerLeagueAccountInfoResponseDto.getSummonerInfoDto();
        LeagueInfoDto leagueInfoDto = summonerLeagueAccountInfoResponseDto.getLeagueInfoDto();
        AccountResponseDto accountResponseDto = summonerLeagueAccountInfoResponseDto.getAccountResponseDto();


        SummonerProfile summonerProfile = SummonerProfile.builder()
                .name(accountResponseDto.getGameName())
                .tag(accountResponseDto.getTagLine())
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

    @Transactional
    public void updateInfo(User user, SummonerLeagueAccountInfoResponseDto summonerLeagueAccountInfoResponseDto) {
        SummonerInfoDto summonerInfoDto = summonerLeagueAccountInfoResponseDto.getSummonerInfoDto();
        LeagueInfoDto leagueInfoDto = summonerLeagueAccountInfoResponseDto.getLeagueInfoDto();
        AccountResponseDto accountResponseDto = summonerLeagueAccountInfoResponseDto.getAccountResponseDto();

        SummonerProfile summonerProfile = SummonerProfile.builder()
                .name(accountResponseDto.getGameName())
                .tag(accountResponseDto.getTagLine())
                .iconUrl("https://ddragon.leagueoflegends.com/cdn/" + ddragonVersion
                        + "/img/profileicon/" + summonerInfoDto.getProfileIconId() + ".png")
                .level(summonerInfoDto.getSummonerLevel()).build();

        user.getRiotAccount().update(
                summonerInfoDto.getRevisionDate(),
                summonerProfile,
                leagueInfoDto.getTier(),
                leagueInfoDto.getRank(),
                leagueInfoDto.getLeaguePoints()
        );
    }

}
