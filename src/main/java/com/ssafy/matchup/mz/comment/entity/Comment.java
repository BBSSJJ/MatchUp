package com.ssafy.matchup.mz.comment.entity;

import com.ssafy.matchup.global.entity.BaseTimeEntity;
import com.ssafy.matchup.mz.article.entity.MzArticle;
import com.ssafy.matchup.user.main.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentComment")
    private final List<Comment> childrenComments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mz_article_id")
    private MzArticle mzArticle;

    public void deleteComment() {
        this.writer = null;
        this.content = null;
    }

    public void updateContent(String content){
        this.content = content;
    }
}
