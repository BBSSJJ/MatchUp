package com.ssafy.matchup.mz.comment.dto;

import com.ssafy.matchup.user.main.dto.UserDto;
import com.ssafy.matchup.mz.comment.entity.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private UserDto writer;
    private List<CommentDto> childrenComments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.writer = new UserDto(comment.getWriter());
        this.childrenComments = comment.getChildrenComments().stream().map(CommentDto::new).collect(toList());
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
