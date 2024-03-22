package com.ssafy.matchup.mz.comment.dto.request;

import lombok.Data;

@Data
public class RegistCommentRequestDto {
    private String content;
    private Long parentCommentId;
}
