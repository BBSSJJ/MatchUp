package com.ssafy.chat.entity;

import com.ssafy.chat.dto.RecruitDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "recruits")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Recruit {

    @Id
    private ObjectId objectId;
    private String name;
    private String url;
    private String tier;
    private String line;
    private String wishLine;
    private String gameType;
    private String content;
    private Long win;
    private Long lose;
    private Double kill;
    private Double death;
    private Double assist;

    public void set(RecruitDto recruitDto) {

        this.name = recruitDto.getName();
        this.url = recruitDto.getUrl();
        this.tier = recruitDto.getTier();
        this.line = recruitDto.getLine();
        this.wishLine = recruitDto.getWishLine();
        this.gameType = recruitDto.getGameType();
        this.content = recruitDto.getContent();
        this.win = recruitDto.getWin();
        this.lose = recruitDto.getLose();
        this.kill = recruitDto.getKill();
        this.death = recruitDto.getDeath();
        this.assist = recruitDto.getAssist();

    }
}
