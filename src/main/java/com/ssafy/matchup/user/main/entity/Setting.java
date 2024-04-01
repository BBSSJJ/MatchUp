package com.ssafy.matchup.user.main.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Data
public class Setting {

    @Column(name = "use_mike")
    private Boolean useMike;

}
