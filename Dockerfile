# 파이썬 3.12 베이스 이미지 사용
FROM python:3.12

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 파일 복사 및 설치
COPY ./requirements.txt /app/requirements.txt
RUN pip install --no-cache-dir --upgrade -r /app/requirements.txt

# 어플리케이션 코드 복사
COPY . .

# 컨테이너 실행 명령 설정
CMD ["uvicorn", "main:matchup", "--host", "0.0.0.0", "--port", "9005"]
