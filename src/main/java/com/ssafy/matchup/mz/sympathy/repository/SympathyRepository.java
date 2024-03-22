package com.ssafy.matchup.mz.sympathy.repository;

import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.mz.sympathy.entity.Sympathy;
import com.ssafy.matchup.user.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SympathyRepository extends JpaRepository<Sympathy, Long> {
    @Query("SELECT s FROM sympathy s WHERE s.mzArticleLeft.id = ?1")
    List<Sympathy> findLeftSympathiesByMzArticleId(Long mzArticleId);

    @Query("SELECT s FROM sympathy s WHERE s.mzArticleRight.id = ?1")
    List<Sympathy> findRightSympathiesByMzArticleId(Long mzArticleId);

    Optional<Sympathy> findSympathyByUserAndMzArticleLeft(User user, MzArticle mzArticle);

    Optional<Sympathy> findSympathyByUserAndMzArticleRight(User user, MzArticle mzArticle);

}
