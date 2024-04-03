package com.ssafy.matchup.user.main.dto.request;

import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.user.main.entity.type.AuthorityType;
import com.ssafy.matchup.user.main.entity.type.SnsType;
import com.ssafy.matchup.user.riotaccount.api.dto.RiotAccountDto;
import com.ssafy.matchup.user.riotaccount.entity.SummonerProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSnsDto {
    private Long userId;
    private AuthorityType role;
    private RiotAccountDto riotAccount;
    private String snsId;
    private SnsType snsType;


    public UserSnsDto(UserDto userDto, String snsId, SnsType snsType) {
        this.userId = userDto.getUserId();
        this.role = userDto.getRole();
        this.riotAccount = userDto.getRiotAccount();
        this.snsId = snsId;
        this.snsType = snsType;
    }

    public UserSnsDto(String snsId, SnsType snsType) {
        this.userId = -1L;
        this.role = AuthorityType.ROLE_USER;
        this.riotAccount = RiotAccountDto
                .builder()
                .id("not registered yet")
                .summonerProfile(SummonerProfile.builder()
                        .name("not registered yet")
                        .tag("not registered yet")
                        .iconUrl("https://ddragon.leagueoflegends.com/cdn/14.6.1/img/profileicon/29.png")
                        .level(0L)
                        .build())
                .tier("not registered yet")
                .leagueRank("not registered yet")
                .leaguePoint(0)
                .build();
        this.snsId = snsId;
        this.snsType = snsType;
    }
}
