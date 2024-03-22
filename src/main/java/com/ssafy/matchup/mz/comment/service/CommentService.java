package com.ssafy.matchup.mz.comment.service;

import com.ssafy.matchup.global.dto.ListDto;
import com.ssafy.matchup.mz.comment.dto.CommentDto;
import com.ssafy.matchup.mz.comment.dto.request.RegistCommentRequestDto;
import com.ssafy.matchup.mz.comment.dto.request.UpdateCommentRequestDto;

public interface CommentService {
    void addComment(Long articleId, Long userId, RegistCommentRequestDto registCommentRequestDto);

    ListDto<CommentDto> listComment(Long articleId);

    void deleteComment(Long commentId, Long userId);

    void updateComment(Long commentId, Long userId, UpdateCommentRequestDto updateCommentRequestDto);
}
