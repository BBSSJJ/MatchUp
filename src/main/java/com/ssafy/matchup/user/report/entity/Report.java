package com.ssafy.matchup.user.report.entity;

import com.ssafy.matchup.user.main.entity.User;
import com.ssafy.matchup.user.report.entity.type.ReportCategoryType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "report")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportCategoryType category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id", nullable = false)
    private User reportedUser;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_user_id", nullable = false)
    private User reportingUser;
}
