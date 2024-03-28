package com.ssafy.matchup.mz.comment.controller;

import com.ssafy.matchup.global.dto.ListDto;
import com.ssafy.matchup.global.util.JwtTokenUtil;
import com.ssafy.matchup.mz.comment.dto.CommentDto;
import com.ssafy.matchup.mz.comment.dto.request.RegistCommentRequestDto;
import com.ssafy.matchup.mz.comment.dto.request.UpdateCommentRequestDto;
import com.ssafy.matchup.mz.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mz/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "댓글 등록", description = "댓글을 등록하는 API입니다.")
    @PostMapping("/articles/{article-id}")
    ResponseEntity<Void> commentRegist(HttpServletRequest request, @PathVariable("article-id") Long articleId, @RequestBody RegistCommentRequestDto registCommentRequestDto) {
        log.info("add comment request : " + registCommentRequestDto.toString());
        commentService.addComment(articleId, jwtTokenUtil.getUserId(request), registCommentRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "댓글 조회", description = "댓글을 조회하는 API입니다.")
    @GetMapping("/articles/{article-id}")
    ResponseEntity<ListDto<CommentDto>> commentList(@PathVariable("article-id") Long articleId) {
        return new ResponseEntity<>(commentService.listComment(articleId), HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제하는 API입니다.")
    @DeleteMapping("/{comment-id}")
    ResponseEntity<Void> commentRemove(HttpServletRequest request, @PathVariable("comment-id") Long commentId) {
        commentService.deleteComment(commentId, jwtTokenUtil.getUserId(request));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정하는 API입니다.")
    @PatchMapping("/{comment-id}")
    ResponseEntity<Void> commentModify(HttpServletRequest request, @PathVariable("comment-id") Long commentId, @RequestBody UpdateCommentRequestDto updateCommentRequestDto) {
        commentService.updateComment(commentId, jwtTokenUtil.getUserId(request), updateCommentRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
