package com.ssafy.matchup_statistics.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
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

