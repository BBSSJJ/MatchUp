package com.ssafy.matchup.mz.comment.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RegistCommentRequestDto {
    private String content;
    private Long parentCommentId;
}
