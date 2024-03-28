package com.ssafy.matchup.mz.comment.service;

import com.ssafy.matchup.global.dto.ListDto;
import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.mz.article.repository.MzArticleRepository;
import com.ssafy.matchup.mz.comment.dto.CommentDto;
import com.ssafy.matchup.mz.comment.dto.request.RegistCommentRequestDto;
import com.ssafy.matchup.mz.comment.dto.request.UpdateCommentRequestDto;
import com.ssafy.matchup.mz.comment.entity.Comment;
import com.ssafy.matchup.mz.comment.repository.CommentRepository;
import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final MzArticleRepository mzArticleRepository;

    @Transactional
    @Override
    public void addComment(Long articleId, Long userId, RegistCommentRequestDto registCommentRequestDto) {
        User user = userRepository.getReferenceById(userId);
        MzArticle mzArticle = mzArticleRepository.getReferenceById(articleId);
        Comment parentComment = null;
        if (registCommentRequestDto.getParentCommentId() != -1)
            parentComment = commentRepository.getReferenceById(registCommentRequestDto.getParentCommentId());
        Comment comment = Comment.builder()
                .content(registCommentRequestDto.getContent())
                .writer(user)
                .parentComment(parentComment)
                .mzArticle(mzArticle)
                .build();
        mzArticle.getComments().add(comment);
    }

    @Transactional
    @Override
    public ListDto<CommentDto> listComment(Long articleId) {
        MzArticle mzArticle = mzArticleRepository.getReferenceById(articleId);
        List<CommentDto> comments = commentRepository.findCommentsByMzArticleAndParentComment(mzArticle, null).stream()
                .map(CommentDto::new).collect(toList());
        return new ListDto<>(comments);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.getReferenceById(commentId);
        if (!userId.equals(comment.getWriter().getId())) throw new SecurityException();
        comment.deleteComment();
    }

    @Transactional
    @Override
    public void updateComment(Long commentId, Long userId, UpdateCommentRequestDto updateCommentRequestDto) {
        Comment comment = commentRepository.getReferenceById(commentId);
        if (!userId.equals(comment.getWriter().getId())) throw new SecurityException();
        log.info("updated content is : {}", updateCommentRequestDto.getContent());
        comment.updateContent(updateCommentRequestDto.getContent());
    }
}
