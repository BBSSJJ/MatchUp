package com.ssafy.matchup.mz.article.repository;

import com.ssafy.matchup.mz.article.entity.MzArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MzArticleRepository extends JpaRepository<MzArticle, Long> {
    @Modifying
    @Query("UPDATE mz_article m SET m.views = m.views + 1 WHERE m.id = :id")
    void updateViews(Long id);

    @Query("SELECT m FROM mz_article m JOIN FETCH m.comments")
    MzArticle findMzArticleById(Long id);
}
