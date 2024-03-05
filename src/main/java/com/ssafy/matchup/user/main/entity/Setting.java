package com.ssafy.matchup.user.main.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Setting {

    @Column(name = "use_mike")
    private Boolean useMike;

}
