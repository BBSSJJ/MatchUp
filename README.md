# Develop Branch

이 브랜치에서 각 server별로 작업하는 브랜치를 분기 및 통합하여 master 브랜치로 연동합니다.

## 상위 브랜치

- master : 제품의 버전 변경시 작업하기 위한 브랜치

## 하위 브랜치

- alarm-server : 친구요청, 채팅 등 알람 전송을 위한 서버
- api-docs-server : API 명세를 확인하고 기능을 테스트하기 위한 서버
- api-gateway : 다른 WAS(Web Application Server)로 API 요청을 전송하는 Gateway
- bigdata-server : 빅데이터 추천 기능을 제공하는 WAS
- chat-server : 채팅과 관련된 비즈니스 로직을 수행하는 WAS
- file-server : 정적 파일(이미지, 동영상 등)을 업로드 및 관리하기 위한 서버입니다.
- statistics-server : 통계처리를 하는 서버로 차후 해당 서버에서 처리 및 저장한 데이터를 기반으로 빅데이터 추천 서버가 작동함
- user-server : 유저 비즈니스 로직을 수행하는 WAS(Web Application Server)
- web-server : 프론트엔드 웹서버

## 업데이트 이력

- 전체 서버 업데이트 : 240322

## 서브모듈 사용방법

- 새로운 브랜치 서브모듈로 등록하기

```git
git submodule add https://github.com/{사용자명}/{저장소명}.git {폴더명}
```

> 이후 .gitmodules 파일 내부에 해당 브랜치명 추가

- 서브모듈 업데이트

```git
git submodule update --init --recursive
```

> 첫 업데이트가 아니면 --init 불필요

```git
cd {각 서브모듈 경로}
git pull origin {각 서브모듈 브랜치명}
```

> 모든 브랜치에서 수행

> 이후 업데이트 커밋, 푸시
