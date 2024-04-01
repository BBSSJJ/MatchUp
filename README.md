## 서버 빌드순서

1. 변경사항 커밋 후 빌드
2. ```docker
   docker build -t 1eaf/statistics_for_indicator .
   ```
3. ```docker
   docker run -it -d -p 9004:8080 --name statistics_for_indicator 1eaf/statistics_for_indicator
   ```
4. db 정상 연결되는지 확인

## Docker Hub에서 빌드

1. docker image 다운로드

   ```docker
   docker pull 1eaf/statistics_for_indicator
   ```

2. 이미지 사용해서 빌드

   ```docker
      docker run -it -d -p 9004:8080 --name statistics_for_indicator 1eaf/statistics_for_indicator
   ```

## DB 빌드순서

1.  ```
    docker run --name matchup_statistics_db -v C:\ssafy\mongo\data\{티어명}:/data/db -d -p 3312:27017 mongo
    ```
2.  도커 볼륨 정상적으로 생겼는지 확인

## DB 덤프순서

1. 덤프파일 생성

   ```
   mongodump --out ~/backup --host 127.0.0.1 --port 27017 --db matchup_statistics_db
   ```

2. 덤프파일 깃에 올려두기(dump branch에 생성된 파일 복사 후 푸시)

## DB 복구하기

1. 덤프파일 복사

   ```
   docker cp {파일경로} matchup_statistics_db:/
   ```

2. 복사파일로 복구

   ```
   mongorestore --db matchup_statistics_db {파일경로}
   ```

## 다이아 완료

### 다이아 1 완료

1. page 1 205 완료
2. page 2 243 완료(오류)
3. page 3 448 완료
4. page 4 649 완료
5. page 5 850 완료
6. page 6 1052 완료
7. DB Dump 완료

---

ping count방식 변경되어 재실행
4035 -> 4348 완료(1544911ms)

### 다이아 2 완료

1. page 1 1255 완료
2. page 2 1341 완료(오류)
3. page 3 1546 완료
4. page 4 1744 완료
5. page 5 1942 완료
6. page 6 2147 완료
7. DB Dump 완료

---

ping count방식 변경되어 재실행
4348 -> 4573 완료(2130809ms)

### 다이아 3 완료

1. page 1 2346 완료
2. page 2 2551 완료
3. page 3 2624 완료(오류)
4. page 4 2756 완료(오류)
5. page 5 2963 완료
6. page 6 3149 완료
7. DB Dump 완료

4573 -> 4889 완료(2570638ms)

### 다이아 4 진행

1. page 1 3260 완료(오류)
2. page 2 3268 완료(오류)
3. page 3 3474 완료
4. page 4 3515 완료(오류)
5. page 5 3612 완료(오류)
6. page 6 3706 완료(오류)
7. page 7 3913 완료
8. page 8 4035 완료
9. DB Dump 완료

4889 -> 5580 완료(1509512ms)

## 에메랄드 완료

### 에메랄드 1 완료

1. page 1 144 오류
2. page 2 349 완료(501193ms)
3. page 3 384 오류
4. page 4 466 오류
5. page 5 555 오류
6. page 6 743 오류
7. page 7 759 오류
8. page 8 912 오류
9. page 9 1045 오류
10. DB Dump 완료

1205 완료(1529890ms)

### 에메랄드 2 완료

1. page 1 1249 완료(527253ms)
2. page 2 1435 오류
3. page 3 1467 오류
4. page 4 1563 오류
5. page 5 1588 오류
6. page 6 1782 오류
7. page 7 1894 오류
8. page 8 2099 오류
9. DB Dump 완료

2410 완료(1504820ms)

### 에메랄드 3 완료

1. page 1 2303 완료(244198ms)
2. page 2 2504 완료(531241ms)
3. page 3 2704 완료(775802ms)
4. page 4 2903 완료(1094876ms)
5. page 5 3104 완료(1456033ms)
6. DB Dump 완료

3615 완료(1566624ms)

### 에메랄드 4 완료

1. page 1 3309 완료(339407ms)
2. page 2 3445 오류(소환사 1명 삭제됨)
3. page 3 3650 완료(911218ms)
4. page 4 3853 완료(971387ms)
5. page 5 4052 완료(1200968ms)
6. page 6 4250 완료(1578724ms)
7. DB Dump 완료

4820 완료(1397813ms)

## 플레티넘 진행

### 플레티넘 1 진행

1. page 1 207 완료(254103ms)
2. page 2 407 완료(575786ms)
3. page 3 607 완료(882980ms)
4. page 4 806 완료(1155017ms)
5. page 5 1008 오류
6. DB Dump 완료

1205 완료(1724364ms)

### 플레티넘 2

2341 오류( 503 SERVICE_UNAVAILABLE)

### 플레티넘 3

3544 완료(1692509ms)

### 플레티넘 4

4748 완료(1772202ms)

## 골드

### 골드 1

1205 완료(3581025ms)

### 골드 2

1651 오류(no summoner name error)

### 골드 3

2856 완료(3469582ms)

### 골드 4

4058 완료(3527433ms)

## 실버

### 실버 1

1205 완료(3572322ms)

### 실버 2

2453 완료(3383949ms)

### 실버 3

3605 완료(3429508ms)

### 실버 4

4321 오류( 503 Service Unavailable from GET)

## 브론즈

### 브론즈 1

1206 완료(3528601m)

### 브론즈 2

2404 완료(3531313ms)

### 브론즈 3

3072 오류(404 Not Found from GET https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/10031917_DEL
)

### 브론즈 4

4277 완료(3681450ms)

## 아이언

### 아이언 1

1021 오류(네트워크 변경)

### 아이언 2

2218 완료(3468181ms)

### 아이언 3

3418 완료(3563492ms)

### 아이언 4

4619 완료(3310361ms)

## 상위 티어

### 챌린저

300 완료(350034ms)

### 그랜드마스터

588 오류
"message": "404 Not Found from GET https://asia.api.riotgames.com/riot/account/v1/accounts/by-puuid/DRHWxBpw4IuICrql6k72lNlWiwzqi1kyF9yV28mc_UM_ahJm2CaD3Zy1j511YVmp-iOpbcm8l4yvkg"
아마 회원탈퇴한듯

### 마스터

1629 오류(너무많아서 취소함)
