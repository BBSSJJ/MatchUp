package com.ssafy.matchup_statistics.global.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "riot_account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class RiotAccount {
    @Id
    @Column(name = "riot_account_id")
    private String id;

    @Embedded
    private SummonerProfile summonerProfile;

    @Column(name = "revision_data")
    private Long revisionDate;

    @Column(name = "tier")
    private String tier;

    @Column(name = "league_rank")
    private String leagueRank;

    @Column(name = "league_point")
    private Integer leaguePoint;

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
