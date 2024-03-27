FROM swaggerapi/swagger-ui:v5.12.0

ENV URLS = "[ { url: \"https://matchup.site/api/user/swagger.json\", name: \"user-server\"}, {url:\"https://matchup.site/api/chat/swagger.json\", name:\"chat-server\"}, {url:\"https://matchup.site/api/statistics/swagger.json\", name:\"statistics-server\"}} ]"

EXPOSE 8002