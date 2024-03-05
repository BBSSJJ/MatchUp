# User Server Branch

유저와 관련된 비즈니스 로직을 수행하는 WAS(Web Application Server)입니다.

이 브랜치는 develop 브랜치에서 분기되었습니다.

# Directory 구조

```java
── src
    ├── main  // 메인 디렉터리
    │   ├── java
    │   │   └── com
    │   │       └── ssafy
    │   │           └── matchup  // artifact : matchup
    │   │               ├── MatchupApplication.java // 메인 어플리케이션
    │   │               ├── global  // 글로벌 세팅
    │   │               │   └── entity  // BaseEntity : Auditing을 위한 BaseEntity
    │   │               │       ├── BaseTimeActorEntity.java
    │   │               │       └── BaseTimeEntity.java
    │   │               ├── mz  // 게시판 도메인
    │   │               │   ├── article  // 게시글
    │   │               │   │   └── entity
    │   │               │   │       ├── ArticleContent.java
    │   │               │   │       └── MzArticle.java
    │   │               │   ├── comment  // 댓글
    │   │               │   │   └── entity
    │   │               │   │       └── Comment.java
    │   │               │   └── sympathy  // 공감
    │   │               │       └── entity
    │   │               │           ├── Sympathy.java
    │   │               │           └── SympathyType.java
    │   │               └── user  // 사용자 도메인
    │   │                   ├── feedback  // 피드백
    │   │                   │   └── entity
    │   │                   │       └── Feedback.java
    │   │                   ├── lbti  // 롤비티아이
    │   │                   │   └── entity
    │   │                   │       └── Lbti.java
    │   │                   ├── main  // 사용자 메인 엔티티, 서비스, 레포지토리 등
    │   │                   │   ├── entity
    │   │                   │   │   ├── Setting.java
    │   │                   │   │   ├── User.java
    │   │                   │   │   └── type
    │   │                   │   │       ├── AuthorityType.java
    │   │                   │   │       └── SnsType.java
    │   │                   │   └── service
    │   │                   ├── report  // 신고
    │   │                   │   └── entity
    │   │                   │       ├── Report.java
    │   │                   │       └── type
    │   │                   │           └── ReportCategoryType.java
    │   │                   └── riotaccount  // 라이엇 계정
    │   │                       └── entity
    │   │                           ├── RiotAccount.java
    │   │                           └── SummonerProfile.java
    │   └── resources  // 정적파일, 세팅파일
    │       ├── application.yml
    │       ├── static
    │       └── templates
    └── test  // 테스트 디렉터리
        └── java
            └── com
                └── ssafy
                    └── matchup
                        └── MatchupApplicationTests.java


```

# ERD

![ERD](./src/main/resources/static/ERD.png)
