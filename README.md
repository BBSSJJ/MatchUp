# A405 프로젝트

## 목차
1. [프로젝트 이름](#1-프로젝트-이름-매치업matchup)
2. [프로젝트 주제](#2-프로젝트-주제)
3. [코드 컨벤션](#3-코드-컨벤션)
4. [깃 컨벤션](#4-깃-컨벤션)
5. [기능 정의서](#5-기능-정의서)
6. [페르소나 분석](#6-페르소나-분석)

## 1. 프로젝트 이름 : 매치업(MatchUP)

## 2. 프로젝트 주제
### 기획의도
게임 유저 추천 및 전략 분석 커뮤니티 

#### 메인 기능 
- 전적 데이터에 기반해서 승률을 높일 유저 추천
- 유저의 게임 스타일, 사이트 내에서의 평가를 바탕으로 듀오 추천
- 유저간 매칭이 되면 그룹 페이지에서 전략 수립 및 보이스/텍스트 채팅
- 플레이 장면 공유 및 유저 간 소통

#### 세부기능
- 빅데이터 기반 유저 추천
    - 함께 플레이하면 승률이 높을 유저를 추천
    - 플레이 스타일이 맞는 유저를 추천 : 설문조사를 통해 데이터 수집, 플레이 이후 상호평가 데이터 활용
- 유저 구인 단체 채팅방
- 유저 간 친구 맺기 및 접속/게임 상태 확인
- 그룹 형성 및 전적 분석
    - 그룹원 사이에 텍스트/보이스 채팅 및 화면 공유
    - 그룹원끼리 게임맵을 선택하여 그림을 그려가며 전략 수립
    - 게임 종료 후에는 팀의 전적을 기록하고 분석(추가적으로 전략 추천)   
    - 팀원 상호평가(악성 유저 판별, 추후 유저 추천 알고리즘 작성 시 반영) 
- 게시판 기능(매치업 존)
    - 플레이 장면을 영상이나 사진으로 첨부하여 게시글 작성 
    - 다른 유저들은 댓글 및 공감으로 의견 공유 가능
    - 사람들이 어떤 플레이어의 입장에 더 공감하는지 그래프로 시각화

[맨위로](#a405-프로젝트)

## 3. 코드 컨벤션

### 3-1. 프론트엔드

#### [프론트엔드 코드 컨벤션](https://ui.toast.com/fe-guide/ko_CODING-CONVENTION)

### 3-2. 백엔드

#### 1) 변수명
- 상식적인 선에서 변수명 정하기

#### 2) 반복문
- 가급적 for문 사용(람다식 지양)

#### 3) 조건문
- if / else 문 사용(swich - case 지양)
- 예외처리는 if문 위에서 처리 후 로직 수행

#### 4) 유효성 검증
- DTO 유효성 검증 : validated
- Entity 유효성 검증 : DB 제약조건(Constrains)
- Optional은 Repository에서만 사용

#### 5) 공백
- 들여쓰기 4칸
- 이외 IDE(Intellij)가 제공하는 기능 사용

#### 6) 주석
- 컨트롤러 메서드에서만 javadocs 활용 API 명세
- 나머지 메서드에서는 기능 정도만 알아볼 수 있게 작성

#### 7) 테스트
- TDD 적용
- 기능테스트는 미수행

#### 8) API
- API 문서는 swagger 작성
- API 예외처리는 globalExceptionHandler로 수행

#### 9) DTO
- dto 이름 : request/response 나누고 Dto 붙이기
- List만 보내는 dto 별도로 생성
- Message만 보내는 dto 별도 생성

#### 10) 코드리뷰
- MR 단위로 코드리뷰 진행
- 정말 급하거나 중요한 경우를 제외하고는 MR 전에 코드리뷰
- 예외상황은 상호 동의 하에 MR 이후 코드리뷰

[맨위로](#a405-프로젝트)

## 4. 깃 컨벤션

### 4-1. 커밋 컨벤션

##### :fire: 커밋 시 가급적 Body를 작성한다.

- feat: 새로운 기능 추가
```
git commit -m "feat : 로그인 기능 추가
# 로그인 기능 구현 완료
- 이제 사용자는 로그인을 할 수 있다.
"
```
- fix: 버그 수정
```
git commit -m "fix : 로그인 오류 수정
# 로그인 오류 수정
- 로그인 로직 중 00오류 확인 후 수정
- 이제 사용자는 정상적으로 로그인을 할 수 있다.
"
```
- docs: 문서 변경
```
git commit -m "docs : README.md 파일 수정"
```
- style: 코드 스타일 변경(공백, 포맷팅 등)
```
git commit -m "style : 코드 스타일 일부 변경
- 코드 가독성을 위해 컨벤션에 맞춰 코드 수정"
```
- refactor: 코드 리팩토링
```
git commit -m "refactor : 컨트롤러 코드 리팩토링
# 컨트롤러 코드 리펙토링하여 코드 가독성 증가"
```
- test: 테스트 코드 작성 또는 수정
```
git commit -m "test : 컨트롤러 테스트코드 추가
# 컨트롤러 기능테스트 추가
- 기능테스트 작성
- 기능테스트 00건 중 00건 통과 / 00건 수정중
"
```
- chore: 기타 작업(빌드 설정, 패키지 업데이트 등)
```
git commit -m "chore : 서버 포트 변경"
```

### 4-2. 브랜치 컨벤션

![git-graph](/assets/img/git-graph.png)

#### 브랜치 구분
- **master** : 제품의 버전 변경시 작업하기 위한 브랜치
- **hotfix** : 긴급한 오류 발생 시 작업하는 브랜치
- **develop** : 하위 아키텍쳐를 연결하는 브랜치
- **web-server** : 프론트엔드 웹서버
- **user-server** : 유저 비즈니스 로직을 수행하는 WAS(Web Application Server)
- **auth-server** : 인증 및 토큰 발행을 담당하는 WAS
- **chat-server** : 채팅과 관련된 비즈니스 로직을 수행하는 WAS
- **bigdata-server** : 빅데이터 추천 기능을 제공하는 WAS
- **statistics-server** : 통계처리를 하는 서버로 차후 해당 서버에서 처리 및 저장한 데이터를 기반으로 빅데이터 추천 서버가 작동함
- **api-gateway** : 다른 WAS(Web Application Server)로 API 요청을 전송하는 Gateway병합
- **file-server** : 정적 파일(이미지, 동영상 등)을 업로드 및 관리하기 위한 서버

#### 브랜치 작업 순서

##### 1. master → develop branch 분기

→ 깃 담당자가 직접 분기

##### 2. develop → webserver, appserver, … 등 서버(포트)별 branch 분기

→ 깃 담당자가 직접 분기

##### 3. 서버별 branch → feature(기능)별 branch 분기 : Jira Issue 연동

→ 기능 담당하는 개인이 직접 분기

##### 4. 서버별 branch에서 각자 역할 나눠서 작업 및 MR

→ 기능 담당하는 인원들끼리 MR 수행 및 충돌 해결

##### 5. 작업 후 서버별 branch에서 develop으로 MR

→ 깃 담당자가 확인

##### 6. MR 시 충돌여부 확인 후 병합, CI / CD 확인

→ 깃 담당자 & 인프라 담당자가 확인

### 4-3. MR 컨벤션

MR 시 다음 양식에 맞춰 문서를 작성한다.
```
# MR이 필요한 이유
- MR이 수행되어야 하는 이유, 수행 후 변경사항 등을 작성한다.
ex)
 - 사용자 회원가입 로직 구현
 - 이제 사용자는 회원가입을 할 수 있다.

# 구현
- 다른 MR 참여자가 쉽게 이해할 수 있는 구현 내용을 추가한다.
ex) 웹페이지 화면 캡쳐, 기능 동작 동영상 첨부 등

# 주요 코드
- 해당 MR에서 변경되거나 핵심 로직 등을 코드로 첨부한다.
ex) 
    // (생략)
    // 정적 참조 변수
    private static Singleton singletonObject;

    // 객체 생성자(private)
    private Singleton() {
    }
    
    // 객체 획득 메서드(public)
    public static Singleton getInstance() {
        if (singletonObject == null) {
            singletonObject = new Singleton();
        }
        
        return singletonObject;
    }
    // (생략)
```

[맨위로](#a405-프로젝트)

## 5. 기능 정의서

|구분     |주 기능                  |상세 기능                   |설명                                                                           |우선 순위|비고                                     |페이지|API|
|-------|----------------------|------------------------|-----------------------------------------------------------------------------|-----|---------------------------------------|---|---|
|0.인증   |0.1 회원가입              |0.1.1 소셜 회원가입하기         |유저는 소셜 로그인을 통해 회원가입할 수 있다.                                                   |상    |카카오톡 API 활용                            |   |   |
|       |0.1 회원가입              |0.1.2 자체 회원가입하기         |유저는 아이디, 비밀번호 등을 입력하여 회원가입 할 수 있다.                                           |하    |                                       |   |   |
|       |0.1 회원가입              |0.1.3 설문조사하기            |유저는 회원가입 후 간단한 설문조사를 통해 게임 플레이 성향을 입력하고, 이에 맞는 유저를 추천받을 수 있다.                |     |                                       |   |   |
|       |0.2 로그인               |0.2.1 카카오톡 로그인하기        |유저는 카카오톡 로그인을 통해 로그인할 수 있다.                                                  |상    |카카오톡 API 활용                            |   |   |
|       |0.2 로그인               |0.2.2 자체 로그인하기          |유저는 회원가입 시 입력했던 아이디, 비밀번호로 로그인할 수 있다.                                        |하    |                                       |   |   |
|       |0.3 라이엇 계정 연동         |0.3.1 라이엇 계정 인증하기       |유저는 라이엇 RSO 이용해서 라이엇 계정 인증할 수 있다.                                            |     |                                       |   |   |
|       |0.3 라이엇 계정 연동         |0.3.2 라이엇 계정 추가하기       |유저는 추가로 자신의 라이엇 계정을 인증할 수 있다.                                                |     |                                       |   |   |
|       |0.3 라이엇 계정 연동         |0.3.3 라이엇 계정 전환하기       |유저는 현재 사용할 라이엇 계정을 선택하여 전환할 수 있다.                                            |     |                                       |   |   |
|       |0.4 회원탈퇴              |0.3.4 회원 탈퇴하기           |유저는 회원 탈퇴를 할 수 있다.                                                           |     |                                       |   |   |
|       |                      |                        |                                                                             |     |                                       |   |   |
|1. 유저  |1.1 상세페이지             |1.1.1 상세페이지 접속하기        |유저는 정보(전적 + 평점 + 성향)가 담긴 상세페이지에 접속할 수 있다.                                    |     |                                       |   |   |
|       |1.1 상세페이지             |1.1.2 다른 유저 상세페이지 확인하기  |유저는 다른 사용자의 상세페이지에 접속할 수 있다.                                                 |     |                                       |   |   |
|       |1.1 상세페이지             |1.1.3 상세페이지 유저와 DM하기    |유저는 해당 상세페이지의 유저와 1:1 DM방에 들어갈 수 있다.                                         |     |                                       |   |   |
|       |1.2 신고                |1.2.1 유저 신고하기           |유저는 특정 유저를 신고할 수 있다.                                                         |     |                                       |   |   |
|       |1.2 신고                |1.2.2 관리자 신고 처리하기       |관리자는 신고 누적된 유저를 추방하거나 관리할 수 있다.                                              |     |                                       |   |   |
|       |                      |                        |                                                                             |     |                                       |   |   |
|2.채팅   |2.1 로비 채팅             |2.1.1 로비 채팅방 접속하기       |유저는 전체 채팅방에 접속하여 다른 유저들을 확인할 수 있다.                                           |     |최대 채팅 수 100개, 채팅방 참여 유저 리스트            |   |   |
|       |2.1 로비 채팅             |2.1.2 로비 채팅 보내기         |유저는 전체 채팅방에 채팅을 전송할 수 있다.                                                    |     |                                       |   |   |
|       |2.1 로비 채팅             |2.1.3 로비 구인 채팅 보내기      |유저는 본인을 어필할 수 있는 양식에 맞춰 채팅을 전송할 수 있다.                                        |     |                                       |   |   |
|       |2.2 DM                |2.2.1 익명 1:1 DM 접속하기    |유저는 1:1 채팅방에 접속하여 이전 채팅 내역을 불러와 확인할 수 있다.                                    |     |                                       |   |   |
|       |2.2 DM                |2.2.2 익명 1:1 채팅 보내기     |유저는 1:1 채팅을 전송할 수 있다.                                                        |     |                                       |   |   |
|       |2.2 DM                |2.2.3 친구 1:1 DM 접속하기    |유저는 친구와 1:1 채팅방에 접속하여 이전 채팅 내역을 불러와 확인할 수 있다.                                |     |                                       |   |   |
|       |2.2 DM                |2.2.4 친구 1:1 채팅 보내기     |유저는 친구에게 1:1 채팅을 전송할 수 있다.                                                   |     |                                       |   |   |
|       |                      |                        |                                                                             |     |                                       |   |   |
|3. 친구  |3.1 친구 관리             |3.1.1 친구 요청하기           |유저는 다른 유저에게 친구 요청할 수 있다.                                                     |상    |                                       |   |   |
|       |3.1 친구 관리             |3.1.2 친구 요청받기           |유저는 다른 유저의 친구요청을 수락할 수 있다.                                                   |상    |                                       |   |   |
|       |3.1 친구 관리             |3.1.3 친구에서 제외하기         |유저는 특정 친구를 제외할 수 있다.                                                         |상    |                                       |   |   |
|       |3.2 친구목록              |3.2.1 친구목록 상세페이지 확인하기   |유저는 친구의 상세페이지에 접속할 수 있다.                                                     |상    |                                       |   |   |
|       |3.2 친구목록              |3.2.2 친구목록에서 DM하기       |유저는 친구목록에서 특정 친구와 1:1 DM방에 들어갈 수 있다.                                         |상    |                                       |   |   |
|       |3.2 친구목록              |3.2.3 친구목록 로그인 상태 확인하기  |유저는 친구의 로그인 여부를 확인할 수 있다.                                                    |중    |                                       |   |   |
|       |3.2 친구목록              |3.2.4 친구목록 게임중 상태 확인하기  |유저는 친구의 게임중 여부를 확인할 수 있다.                                                    |중    |                                       |   |   |
|       |                      |                        |                                                                             |     |                                       |   |   |
|4. 매치업 |4.1 채팅                |4.1.1 텍스트 채팅하기          |유저는 그룹 내에서 텍스트로 채팅할 수 있다.                                                    |상    |                                       |   |   |
|       |4.2 보이스 채팅            |4.2.1 보이스 채팅하기          |유저는 그룹 내에서 보이스로 채팅할 수 있다.                                                    |상    |                                       |   |   |
|       |4.2 보이스 채팅            |4.2.2 보이스 켜기 / 끄기       |유저는 보이스를 켤지 끌지 설정할 수 있다.                                                     |상    |                                       |   |   |
|       |4.3 전략수립              |4.3.1 전략 수립화면 맵 확인하기    |유저는 전략 수립을 위해 전체 맵을 확인할 수 있다.                                                |상    |                                       |   |   |
|       |4.3 전략수립              |4.3.2 전략 수립화면 펜으로 작성하기  |유저는 전략 수립을 위해 펜으로 맵에 필기할 수 있다.                                               |상    |                                       |   |   |
|       |4.3 전략수립              |4.3.3 전략 수립화면에 작성한 펜 지우기|유저는 작성한 필기를 지울 수 있다.                                                         |중    |                                       |   |   |
|       |4.4 전적분석              |4.4.1 게임 종료 후 팀 전적 확인하기 |유저는 게임 종료 후 현재까지 팀의 전적을 확인할 수 있다.                                            |중    |                                       |   |   |
|       |4.4 전적분석              |4.4.2 팀 전적 통계 확인하기      |유저는 팀 전적에 대한 통계를 확인할 수 있다.                                                   |중    |                                       |   |   |
|       |4.4 전적분석              |4.4.3 연승 / 연패에 따른 이벤트 받기|유저는 연승 및 연패에 따라 이벤트를 받을 수 있다.(ex. 승리, 패배 시 문구 변경 등)                          |중    |                                       |   |   |
|       |4.5 화면 공유             |4.5.1 본인의 화면 공유하기       |유저는 본인이 공유하고 싶은 화면을 선택하여 공유할 수 있다.                                           |상    |                                       |   |   |
|       |4.6. 평가(피드백)          |4.6.1 함께한 그룹원 평가하기      |유저는 본인과 함께한 그룹원을 평가할 수 있다.                                                   |상    |                                       |   |   |
|       |                      |                        |                                                                             |     |                                       |   |   |
|5.매치업 존|5.1  화면공유 내용 게시(사진/영상)|5.1.1 플레이 장면 이미지 파일 게시하기|유저는 이미지 파일을 업로드하고 텍스트를 작성할 수 있다.                                             |     |                                       |   |   |
|       |5.1  화면공유 내용 게시(사진/영상)|5.1.2 플레이 장면 영상 게시하기    |유저는 영상 파일을 업로드하고 텍스트를 작성할 수 있다.                                              |     |                                       |   |   |
|       |5.2 댓글 및 대댓글 작성       |5.2.1 댓글 작성하기           |유저는 특정 게시글에 댓글을 작성할 수 있다.                                                    |     |                                       |   |   |
|       |5.2 댓글 및 대댓글 작성       |5.2.2 대댓글 작성하기          |유저는 특정 댓글에 대댓글을 작성할 수 있다.                                                    |     |                                       |   |   |
|       |5.3 게시글 조회            |5.3.1 최신순 조회하기          |유저는 게시글 페이지 최초 진입 시 게시글을 최신순으로 조회할 수 있다.                                     |     |                                       |   |   |
|       |5.3 게시글 조회            |5.3.2 인기게시글(조회수) 조회하기   |유저는 게시글 페이지 최초 진입 시 게시글을 인기순으로 조회할 수 있다.                                     |     |                                       |   |   |
|       |5.4 게시글 검색            |5.4.1 작성자명으로 검색하기       |유저는 작성자명(ID)으로 특정 유저의 게시물을 검색할 수 있다.                                         |     |                                       |   |   |
|       |5.4 게시글 검색            |5.4.2 게시글제목으로 검색하기      |유저는 특정 키워드를 포함하는 게시글을 검색할 수 있다.                                              |     |                                       |   |   |
|       |5.5 게시글 및 댓글 공감       |5.5.1 게시글 공감하기          |유저는 게시글에서 어느 플레이어의 입장에 공감하는지 반응을 남길 수 있다.(ex-찬성vs.반대)                        |     |                                       |   |   |
|       |5.5 게시글 및 댓글 공감       |5.5.2 댓글 공감하기           |유저는 특정 댓글 혹은 대댓글에 좋아요 반응을 남길 수 있다.                                           |     |                                       |   |   |
|       |                      |                        |                                                                             |     |                                       |   |   |
|6.매칭   |6.1 추천 알고리즘           |6.1.1 승률 기반 추천 알고리즘     |📝 승률이 높을 것으로 예상되는 유저 추천 📝 최근 전적, 조합별 승률 등을 고려해 승률 예측                       |상    |🗒️ 듀오 기록 EDA 바탕의 승률 예측 모델 - 콘텐츠 기반 필터링|   |   |
|       |6.1 추천 알고리즘           |6.1.2 평점 기반 추천 알고리즘     |📝 내가 높은 평점을 줄 것으로 예상되는 유저 (플레이 스타일 기반) 추천 📝 사용 시간대, 마이크, 게임 시간 등을 고려해 평점 예측|중    |🗒️ 나와 비슷한 유저들이 높은 평점을 준 유저 추천 - 협업 필터링|   |   |
|       |6.2 유저 추천 토글          |6.2.1 추천 항목 선택하기        |📝 어떤 데이터 기반의 추천을 원하는지 선택                                                    |상    |                                       |   |   |
|       |6.2 유저 추천 토글          |6.2.2 추천 유저 목록 확인하기     |📝 추천 항목 기반으로 선택된 유저 출력                                                      |상    |🗒️ 추천 상위 5인의 간단한 소개와 채팅 버튼            |   |   |
|       |6.2 유저 추천 토글          |6.2.3 승률 기반 추천목록 확인하기   |📝 승률이 높을 것으로 예상되는 조합 추천                                                     |하    |                                       |   |   |
|       |6.2 유저 추천 토글          |6.2.3 평점 기반 추천목록 확인하기   |📝 평점 입력이 적다면 정확도가 낮을 수 있다는 경고 표시                                            |하    |                                       |   |   |


[맨위로](#a405-프로젝트)

## 6. 페르소나 분석
- 이상혁(27세, 게임을 좋아하는 사람)
- 같이 게임을 할 친구가 마땅치않음
- 인터넷에서 같이 할 사람을 구해봤지만 과정이 불편하고 매칭된 상대가 마음에 들지 않음
- 새로운 사람을 만날 때마다 메신저 정보를 주고받는게 귀찮음
- 플레이에 대한 피드백을 받고 싶음

[맨위로](#a405-프로젝트)
