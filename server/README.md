# File Server

- 프로젝트에 필요한 파일을 저장하는 서버입니다.

## Docker 포팅 메뉴얼

```docker
# 현재 디렉터리로 이동
docker pull node
docker build . -t node
docker run -it --name file-view-server -p 8001:8001 -d node
```
