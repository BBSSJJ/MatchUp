# 405 File Server

정적 파일(이미지, 동영상 등)을 업로드 및 관리하기 위한 서버입니다.

## [파일서버](http://70.12.246.246:1234) 로컬 실행방법

### 1. Vue.js 실행하기 : local view server

#### - 실행

##### 1) 현재 Repository clone

- if) 현재 레포지토리 clone 시

```
git clone https://lab.ssafy.com/s10-bigdata-recom-sub1/S10P21A405.git
```

- else if) origin 설정 후 fetch 시

```
$ git remote add origin https://lab.ssafy.com/s10-bigdata-recom-sub1/S10P21A405.git
$ git fetch origin main
$ git checkout main
```

- fork 시

```
fork 후 본인 레포지토리에서 위 절차 동일하게 수행(레포지토리 경로만 본인 경로로 수정)
```

##### 2) 로컬 레포지토리에서 vue메인경로(view)로 이동 후 vite server 실행

```
~> cd {Repository root directory}
{Repository root directory}> cd view
{Repository root directory}/docs> npm install
{Repository root directory}/docs> npm run dev
```

#### 2. node.js 서버 실행하기 : local file server

##### \* 1-2)에서 실행된 vite는 종료하지 않고, 새 shell에서 실행해야 합니다.

##### 1) 로컬 레포지토리에서 file server 메인경로로 이동 후 node.js server 실행

```
~> cd {Repository root directory}
{Repository root directory}> cd server
{Repository root directory}/server> node server.js
```

#### 3. file server 접속 : 웹 브라우저에서 http://localhost:1234 접속

##### \* 로컬로 실행할 경우 axios 요청에서 파일서버 경로를 변경해야 합니다. 서버를 직접 사용해보시려면 [파일서버](http://70.12.246.246:1234)에 접속해보세요!
