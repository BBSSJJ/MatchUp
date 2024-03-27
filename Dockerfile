FROM swaggerapi/swagger-ui:v5.12.0

ENV URLS = "[ { url: \"https://matchup.site/api/user-server/swagger.json\", name: \"user-server\"}, {url:\"https://matchup.site/api/statistics-server/swagger.json\", name:\"statistics-server\"}, {url:\"https://matchup.site/api/chat-server/swagger.json\", name:\"chat-server\"}} ]"

EXPOSE 8002