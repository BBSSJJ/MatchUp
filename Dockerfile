FROM swaggerapi/swagger-ui:v5.12.0

ENV URLS "[ { url: \"https://matchup.site/api/user/swagger.json\", name: \"user-server\"}, {url:\"https://matchup.site/api/chats/swagger.json\", name:\"chat-server\"}, {url:\"https://matchup.site/api/statistics/swagger.json\", name:\"statistics-server\"}, {url:\"https://matchup.site/api/alarm/swagger.json\", name:\"alarm-server\"} ]"

EXPOSE 8002