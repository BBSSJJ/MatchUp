# Statistics Server Branch

통계처리를 하는 서버입니다. 해당 서버에서 처리 및 저장한 데이터를 기반으로 빅데이터 추천 서버가 작동합니다.

이 브랜치는 develop 브랜치에서 분기되었습니다.

## DB 생성(Docker)

```py
# 로컬과 도커 내부 볼륨 연결하여 데이터 백업
docker run --name matchup_statistics_db -v ~/data:/data/db -d -p 27017:27017 mongo
```

## DB 생성

```py
# Mongosh 접속
mongosh

# DB 생성
use matchup_statistics_db
```
