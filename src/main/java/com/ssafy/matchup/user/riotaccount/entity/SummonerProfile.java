package com.ssafy.matchup.user.riotaccount.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SummonerProfile {
    @Column(name = "name")
    private String name;
    @Column(name = "icon_url")
    private String iconUrl;
    @Column(name = "level")
    private String level;
}
