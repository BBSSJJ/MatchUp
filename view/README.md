# File View Server

- 파일서버 내부 확인을 위한 뷰 서버입니다.

## Docker 포팅 메뉴얼

```docker
# 현재 디렉터리로 이동
docker pull node
docker build . -t node
docker run -it --name file-view-server -p 8000:8000 -d node
```
