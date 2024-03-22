package com.ssafy.matchup.mz.comment.repository;

import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.mz.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByMzArticleAndParentComment(MzArticle mzArticle, Comment comment);
}
