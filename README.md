# API Docs Server

OpenAPI와 SpringDoc을 활용해서 Swagger-ui API 문서를 제공하는 서버입니다.

## 버전 관리

- Swagger API Server : v5.12.0 / [Docker Image](https://hub.docker.com/r/swaggerapi/swagger-ui/tags)
- Java Gradle Dependency: springdoc-openapi-starter-webmvc-ui:2.4.0

## 사용 방법

### 1. Java Gradle에 의존성 추가 및 설정파일 추가

- 의존성 추가
  ```gradle
  dependencies {
  ...
  implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.4.0'
  ...
  }
  ```
- application.yml 설정 추가
  ```yaml
  profiles:
    include: swagger
  ```
- application-swagger.yml 설정파일 생성
  ```yaml
  springdoc:
    default-consumes-media-type: application/json
    default-produces-media-type: application/json
    api-docs:
      path: /swagger.json
      groups:
      enabled: true
    swagger-ui:
      operations-sorter: alpha # alpha(알파벳 오름차순), method(HTTP메소드순)
      tags-sorter: alpha # 태그 정렬 기준
      path: /swagger # html 문서 접속 경로
      disable-swagger-default-url: true
      display-query-params-without-oauth2: true
      doc-expansion: none # tag, operation 펼치는 방식
  ```

### 2. Controller 생성하기

- 사용방법 : [MR 참고](https://lab.ssafy.com/s10-bigdata-recom-sub2/S10P22A405/-/merge_requests/11)

### 3. Swagger API Server 생성하기

- Docker 이미지 생성

  ```py
  # 환경변수 설정 시 Docker 내부 호스트 + Port 주소를 정확히 적어주어야 해당 json파일을 자동으로 불러옵니다!
  # 내부에 큰따옴표 쓸때 escape문자(\) 잘 적어야 합니다!
  docker run -it --name api_docs_server -p 8002:8080 -e URLS="[ { url: \"http://{Docker 내부 IP주소:포트주소}/swagger.json\", name: \"user-server\"}, {url:\"{주소}\", name:\"{이름}\"}, ... } ]" -d swaggerapi/swagger-ui:v5.12.0
  ```
