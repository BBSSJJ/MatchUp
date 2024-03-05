package com.ssafy.matchup.user.riotaccount.entity;

import com.ssafy.matchup.user.main.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "riot_account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiotAccount {
    @Id
    @Column(name = "riot_token_value")
    private String riotTokenValue;

    @Embedded
    private SummonerProfile summonerProfile;

    @Column(name = "revision_data")
    private String revisionDate;

    @Column(name = "tier")
    private String tier;

    @Column(name = "league_rank")
    private String leagueRank;

    @Column(name = "league_point")
    private String leaguePoint;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
