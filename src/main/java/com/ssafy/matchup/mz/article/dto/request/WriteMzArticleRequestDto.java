package com.ssafy.matchup.mz.article.dto.request;

import lombok.Data;

@Data
public class WriteMzArticleRequestDto {
    private String title;
    private String content;
    private String leftSympathyTitle;
    private String rightSympathyTitle;
}
