package com.ssafy.matchup.user.riotaccount.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SummonerProfile {
    @Column(name = "name")
    private String name;
    @Column(name = "tag")
    private String tag;
    @Column(name = "icon_url")
    private String iconUrl;
    @Column(name = "level")
    private Long level;

}