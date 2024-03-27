package com.ssafy.matchup.mz.article.repository;

import com.ssafy.matchup.mz.article.entity.MzArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MzArticleRepository extends JpaRepository<MzArticle, Long> {
    @Modifying
    @Query("UPDATE mz_article m SET m.views = m.views + 1 WHERE m.id = :id")
    void updateViews(Long id);

//    Page<MzArticle> findMzArticlesByAuthor(User user, Pageable pageable);

//    @Query("SELECT m FROM mz_article m " +
//            "JOIN FETCH m.author a " +
//            "JOIN FETCH a.riotAccount r " +
//            "JOIN FETCH r.summonerProfile")
//    Page<MzArticle> findAll(Pageable pageable);

    @Query("SELECT m FROM mz_article m " +
            "JOIN FETCH m.author a " +
            "JOIN FETCH a.riotAccount r " +
            "JOIN FETCH r.summonerProfile " +
            "LEFT JOIN FETCH m.comments WHERE m.id = :id")
    Optional<MzArticle> findMzArticleById(Long id);

    @Query("SELECT m FROM mz_article m " +
            "JOIN FETCH m.author a " +
            "JOIN FETCH a.riotAccount r " +
            "JOIN FETCH r.summonerProfile ")
    List<MzArticle> findAllMzArticles();

    @Query("SELECT m FROM mz_article m " +
            "JOIN FETCH m.author a " +
            "JOIN FETCH a.riotAccount r " +
            "JOIN FETCH r.summonerProfile " +
            "WHERE m.title LIKE CONCAT('%', :title, '%')")
    List<MzArticle> findMzArticlesByTitleContaining(String title);

    @Query("SELECT m FROM mz_article m " +
            "JOIN FETCH m.author a " +
            "JOIN FETCH a.riotAccount r " +
            "JOIN FETCH r.summonerProfile " +
            "WHERE m.author.id = :authorId")
    List<MzArticle> findMzArticlesByAuthorId(Long authorId);
}
