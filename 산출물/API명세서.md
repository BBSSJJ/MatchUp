# API Docs

# 유저(user)

---

- `**[POST]` 회원가입**
    
    
    | Description | 입력받은 소환사명을 서버로부터 받은 snsId, snsType과 함께 전송한다. |
    | --- | --- |
    | URL | https://jsonplaceholder.typicode.com/postsapi/users/signup |
    | Auth Required | No |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | body params  |  |  |  |  |
    | summonerName | String | true | 소환사명 |  |
    - **✅ Response 200**
        
        ```jsx
        {
        "id": 1,
        "riotTokenValue": "asdasdasdasd",
        "name": "갓상엽 #갓갓",
        "iconUrl": "thisisIconUrl",
        "level": "341",
        "tier": "",
        "leagueRank": "",
        "leaguePoint": "",
        }
        ```
        
    - **✅ Response 400**
        
        ```jsx
        { message: '사용 불가능한 소환사명' }
        ```
        
    

---

- `**[GET]` 유저 기본 정보 요청**
    
    
    | Description | 유저 기본정보 요청한다. |
    | --- | --- |
    | URL | /api/users/{userId}/infos |
    | Auth Required | No |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | url params userId | number | true | 유저 id |  |
    - **✅ Response 200**
        
        ```jsx
        {
        "id": 1,
        "riotTokenValue": "asdasdasdasd",
        "name": "갓상엽 #갓갓",
        "iconUrl": "thisisIconUrl",
        "level": "341",
        "tier": "",
        "leagueRank": "",
        "leaguePoint": "",
        }
        ```
        
    - **✅ Response 404**
        
        ```jsx
        { message: 'User is not found' }
        ```
        
    

---

# 친구(friend)

---

- `**[POST]` 친구 요청**
    
    
    | Description | 친구 요청을 보낸다. |
    | --- | --- |
    | URL | https://jsonplaceholder.typicode.com/postsapi/friends/{myId} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | url params  |  |  |  |  |
    | myId | number | true | 내 id |  |
    | body params  |  |  |  |  |
    | friendId | number | true | 상대 id |  |
    - **✅ Response 200**
        
        ```jsx
        {
        "id": 1,
        "riotTokenValue": "asdasdasdasd",
        "name": "갓상엽 #갓갓",
        "iconUrl": "thisisIconUrl",
        "level": "341",
        "tier": "",
        "leagueRank": "",
        "leaguePoint": "",
        }
        ```
        
    

---

- `**[GET]` 친구 목록 조회**
    
    
    | Description | 내 친구 목록을 요청한다. |
    | --- | --- |
    | URL | /api/friends/{myId} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | url params |  |  |  |  |
    | myId | number | true | 유저 id |  |
    - **✅ Response 200**
        
        ```jsx
        {
        "id": 1,
        "riotTokenValue": "asdasdasdasd",
        "name": "갓상엽 #갓갓",
        "iconUrl": "thisisIconUrl",
        "level": "341",
        "tier": "",
        "leagueRank": "",
        "leaguePoint": "",
        }
        ```
        
    - **✅ Response 404**
        
        ```jsx
        { message: 'User is not found' }
        ```
        
    

---

- `**[DELETE]` 친구 삭제**
    
    
    | Description | 친구를 삭제한다. |
    | --- | --- |
    | URL | https://jsonplaceholder.typicode.com/postsapi/friends/{friendId} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | url params  |  |  |  |  |
    | friendId | number | true | 상대 id |  |
    - **✅ Response 200**
        
        ```jsx
        {
        "id": 1,
        "riotTokenValue": "asdasdasdasd",
        "name": "갓상엽 #갓갓",
        "iconUrl": "thisisIconUrl",
        "level": "341",
        "tier": "",
        "leagueRank": "",
        "leaguePoint": "",
        }
        ```
        

---

# 매치업 존(MZ)

---

- `**[GET]` MZ 글 목록 조회**
    
    
    | Description | MZ글 목록을 가져온다. |
    | --- | --- |
    | URL | https://jsonplaceholder.typicode.com/posts/1/commentsapi/mz/articles |
    | Auth Required | No |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    |  |  |  |  |
    - **✅ Response 200**
        
        ```jsx
        [
        	{
        	"postId": 1,
        	"id": 1,
        	"name": "id labore ex et quam laborum",
        	"email": "Eliseo@gardner.biz",
        	"body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
        	},
        	{
        	"postId": 1,
        	"id": 2,
        	"name": "quo vero reiciendis velit similique earum",
        	"email": "Jayne_Kuhic@sydney.com",
        	"body": "est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"
        	},
        	{
        	"postId": 1,
        	"id": 3,
        	"name": "odio adipisci rerum aut animi",
        	"email": "Nikita@garfield.biz",
        	"body": "quia molestiae reprehenderit quasi aspernatur\naut expedita occaecati aliquam eveniet laudantium\nomnis quibusdam delectus saepe quia accusamus maiores nam est\ncum et ducimus et vero voluptates excepturi deleniti ratione"
        	},
        	{
        	"postId": 1,
        	"id": 4,
        	"name": "alias odio sit",
        	"email": "Lew@alysha.tv",
        	"body": "non et atque\noccaecati deserunt quas accusantium unde odit nobis qui voluptatem\nquia voluptas consequuntur itaque dolor\net qui rerum deleniti ut occaecati"
        	},
        	{
        	"postId": 1,
        	"id": 5,
        	"name": "vero eaque aliquid doloribus et culpa",
        	"email": "Hayden@althea.biz",
        	"body": "harum non quasi et ratione\ntempore iure ex voluptates in ratione\nharum architecto fugit inventore cupiditate\nvoluptates magni quo et"
        	}
        ]
        ```
        
    - **✅ Response 404**

---

- `**[GET]` MZ 글 상세 조회**
    
    
    | Description | MZ 글 상세정보를 가져온다 |
    | --- | --- |
    | URL | /api/mz/articles/{id} |
    | Auth Required | No |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | url params |  |  |  |
    | id | number | yes |  |
    - **✅ Response 200**
        
        ```jsx
        {
        "userId": 1,
        "id": 2,
        "title": "qui est esse",
        "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
        }
        ```
        
    - **✅ Response 404**

---

- `**[POST]` MZ 글 등록**
    
    
    | Description | MZ 글 등록 |
    | --- | --- |
    | URL | /api/mz/articles |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | body params |  |  |  |
    | title | string | yes | 제목 |
    | content | string | yes | 내용(통 HTML) |
    - **✅ Response 200**
        
        ```jsx
        {
        "userId": 1,
        "id": 2,
        "title": "qui est esse",
        "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
        }
        ```
        
    - **✅ Response 404**

---

- `**[PATCH]` MZ 글 수정**
    
    
    | Description | MZ 글 수정 |
    | --- | --- |
    | URL | /api/mz/articles/{id} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | url params |  |  |  |
    | id | number | yes | 글 id |
    | body params |  |  |  |
    | title | string | yes | 제목 |
    | content | string | yes | 내용(통 HTML) |
    - **✅ Response 200**
        
        ```jsx
        {
        "userId": 1,
        "id": 2,
        "title": "qui est esse",
        "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
        }
        ```
        
    - **✅ Response 404**

---

- `**[DELETE]` MZ 글 삭제**
    
    
    | Description | MZ 글 삭제한다. |
    | --- | --- |
    | URL | /api/mz/articles/{id} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | url params |  |  |  |
    | id | number | yes |  |
    - **✅ Response 200**
        
        ```jsx
        {
        "userId": 1,
        "id": 2,
        "title": "qui est esse",
        "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
        }
        ```
        
    - **✅ Response 404**

---

- `**[POST]` MZ 댓글 등록**
    
    
    | Description | MZ글의 댓글 등록한다. |
    | --- | --- |
    | URL | https://jsonplaceholder.typicode.com/posts/1/commentsapi/mz/comments |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | body params |  |  |  |
    | content | String | yes |  |
    - **✅ Response 200**
        
        ```jsx
        [
        	{
        	"postId": 1,
        	"id": 1,
        	"name": "id labore ex et quam laborum",
        	"email": "Eliseo@gardner.biz",
        	"body": "laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"
        	},
        	{
        	"postId": 1,
        	"id": 2,
        	"name": "quo vero reiciendis velit similique earum",
        	"email": "Jayne_Kuhic@sydney.com",
        	"body": "est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et"
        	},
        	{
        	"postId": 1,
        	"id": 3,
        	"name": "odio adipisci rerum aut animi",
        	"email": "Nikita@garfield.biz",
        	"body": "quia molestiae reprehenderit quasi aspernatur\naut expedita occaecati aliquam eveniet laudantium\nomnis quibusdam delectus saepe quia accusamus maiores nam est\ncum et ducimus et vero voluptates excepturi deleniti ratione"
        	},
        	{
        	"postId": 1,
        	"id": 4,
        	"name": "alias odio sit",
        	"email": "Lew@alysha.tv",
        	"body": "non et atque\noccaecati deserunt quas accusantium unde odit nobis qui voluptatem\nquia voluptas consequuntur itaque dolor\net qui rerum deleniti ut occaecati"
        	},
        	{
        	"postId": 1,
        	"id": 5,
        	"name": "vero eaque aliquid doloribus et culpa",
        	"email": "Hayden@althea.biz",
        	"body": "harum non quasi et ratione\ntempore iure ex voluptates in ratione\nharum architecto fugit inventore cupiditate\nvoluptates magni quo et"
        	}
        ]
        ```
        
    - **✅ Response 404**

---

- `**[PATCH]` MZ 댓글 수정**
    
    
    | Description | MZ글의 댓글 수정한다. |
    | --- | --- |
    | URL | /api/mz/comments/{id} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | url params |  |  |  |
    | id | number | yes | 댓글 id |
    | body params |  |  |  |
    | content | String | yes |  |
    - **✅ Response 200**
        
        ```jsx
        {
        "userId": 1,
        "id": 2,
        "title": "qui est esse",
        "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
        }
        ```
        
    - **✅ Response 404**

---

- `**[DELETE]` MZ 댓글 삭제**
    
    
    | Description | MZ글의 댓글 삭제한다. |
    | --- | --- |
    | URL | /api/mz/comments/{id} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | url params |  |  |  |
    | id | number | yes | 댓글 id |
    - **✅ Response 200**
        
        ```jsx
        {
        "userId": 1,
        "id": 2,
        "title": "qui est esse",
        "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
        }
        ```
        
    - **✅ Response 404**

---

- `**[POST]` MZ 공감**
    
    
    | Description | MZ글에 투표한다. 기존에 공감한건 삭제한다. |
    | --- | --- |
    | URL | /api/mz/articles/{articleId}/sympathies/ |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | url params |  |  |  |
    | articleId | number | yes |  |
    | body params |  |  |  |
    | userId | number | yes |  |
    | sympathyType | String | yes | right or left |
    - **✅ Response 200**
        
        ```jsx
        {
        "userId": 1,
        "id": 2,
        "title": "qui est esse",
        "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
        }
        ```
        
    - **✅ Response 404**

# 채팅(chat)

- `**[GET]` 채팅방 목록 조회**
    
    
    | Description | 유저 ID를 전달 받아 해당 유저의 채팅방 목록을 반환 |
    | --- | --- |
    | URL | /api/chats/users/{userId}/rooms |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | url params |  |  |  |
    | userId | Long | true | 유저 ID |
    - **✅ Response 200**
        
        ```jsx
        {
            "list": [
                {
                    "objectId": String,
                    "participants": [
                        Long
                    ]
                }
            ]
        }
        ```
        

---

- `**[POST]` 채팅방 생성**
    
    
    | Description | 채팅방을 만들 유저들의 정보를 받아 채팅방 생성 |
    | --- | --- |
    | URL | /api/chats/users/{userId}/rooms |
    | Auth Required |  |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | body params |  |  |  |
    | participants | List | true | 채팅방 참가 유저 ID |
    | url params |  |  |  |
    | userId | Long | true | 유저 ID |
    - **✅ Response 200**
        
        ```jsx
        {
        		"message": "created"
        }
        ```
        

---

- `**[DELETE]` 채팅방 삭제 (채팅방 나가기)**
    
    
    | Description | 채팅방 ID를 받아 채팅방을 삭제 |
    | --- | --- |
    | URL | /api/chats/users/{userId}/rooms/{roomId} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | url params |  |  |  |
    | userId | Long | true | 유저 ID |
    | roomId | String | true | 채팅방 ID |
    - **✅ Response 200**
        
        ```jsx
        {
        		"message": "deleted"
        }
        ```
        

---

- `**[GET]` 채팅 내역 불러오기**
    
    
    | Description | 채팅방 ID를 받아 해당 채팅방의 채팅 내역을 조회 |
    | --- | --- |
    | URL | /api/chats/users/{userId}/rooms/{roomId} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | url params |  |  |  |
    | userId | Long | true | 유저 ID |
    | roomId | String | true | 채팅방 ID |
    - **✅ Response 200**
        
        ```jsx
        {
            "list": [
                {
                    "sender": String,
                    "content": String,
                    "timestamp": LocalDateTime
                }
            ]
        }
        ```
        

---

- `**[GET]` 구인 게시판 목록 조회**
    
    
    | Description | 구인 게시판 목록 조회 |
    | --- | --- |
    | URL | /api/recruits |
    | Auth Required | No |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | - | - | - | - |
    - **✅ Response 200**
        
        ```jsx
        {
            "list": [
                {
                    "objectId": String,
                    "name": String,
                    "url": String,
                    "tier": String,
                    "line": String,
                    "wishLine": String,
                    "gameType": String,
                    "content": String
                }
            ]
        }
        ```
        

---

- `**[WS]` 채팅 전송**
    
    
    | Description | 채팅 전송 |
    | --- | --- |
    | URL | /app/chat/{roomId} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | sender | String | true | 보낸 사람 |
    | content | String | true | 채팅 내용 |
    | timestamp | LocalDateTime | true | 보낸 시간 |

---

- `**[WS]` 구인 게시판 등록/수정/삭제**
    
    
    | Description | 구인 게시판 등록/수정/삭제 |
    | --- | --- |
    | URL | /app/recruit |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | method | String(”create”,”update”,”delete”) | true | 등록/수정/삭제를 구분하는 파라미터 |
    | objectId | String | false | 수정/삭제 시 MongoDB에서 필요한 도큐먼트 ID (등록 시 null) |
    | name | String | true | 라이엇 ID |
    | url | String | true | 프로필이미지 URL |
    | tier | String | true | 티어 |
    | line | String | true | 본인의 라인 |
    | wishLine | String | true | 구인하는 라인 |
    | gameType | String | true | 게임 타입 |
    | content | String | true | 내용 |
    | win | Long | true | 승수 |
    | lose | Long | true | 패수 |
    | kill | Double | true | 킬수 |
    | death | Double | true | 데스수 |
    | assist | Double | true | 어시수 |

---

# 매치업(matchup)

- `**[POST]` 매치업 생성**
    
    
    | Description | 함께할 유저들의 목록을 통해 매치업을 생성합니다. |
    | --- | --- |
    | URL | /api/matchups |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | body params  |  |  |  |  |
    | users | List<number> | true | 함께할 유저명 | - |
    - **✅ Response 200**
        
        ```jsx
        { "message": "전적정보 및 통계지표 생성 완료" }
        ```
        

---

- `**[DELETE]` 매치업 종료**
    
    
    | Description | 생성된 매치업을 삭제합니다. |
    | --- | --- |
    | URL | /api/matchups/{matchupId} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | url params  |  |  |  |  |
    | matchupId | number | true | 삭제할 매치업명 | - |
    - **✅ Response 200**
        
        ```jsx
        {
        "summonerIndicator": {
        		"leagueIndicator": {...},
        		"macroIndicator": {...},
        		"etcIndicator": {...},
        		"landIndicator": {...},
        		"userIndicator": {...}
        	}
        }
        ```
        
    

---

- `**[PATCH]` 매치업 시작**
    
    
    | Description | 매치업을 시작합니다. |
    | --- | --- |
    | URL | /api/matchups/{matchupId}/start |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | url params  |  |  |  |  |
    | matchupId | number | true | 시작할 매치업명 | - |
    - **✅ Response 200**
        
        ```jsx
        {
            "metadata": {
                "dataVersion": "2",
                "matchId": "KR_매치ID",
                "participants": [
                    ...
                ]
            },
            "info": {
            ...
            }
        }
        ```
        
    

---

- `**[WS]` 전략 그림 그리기**
    
    
    | Description | 그림 경로 전송 |
    | --- | --- |
    | URL | /app/path/{roomId} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description |
    | --- | --- | --- | --- |
    | sender | String | true | 보낸 사람 |
    | path | List<int[]> | true | 그림 경로 |
    | color | String | true | 경로 색깔 |
    | timestamp | LocalDateTime | true | 보낸 시간 |

---

# 통계(statistics)

- `**[POST]` 통계지표 및 전적정보 생성**
    
    
    | Description | 소환사명으로 전적정보 및 통계지표를 생성합니다. |
    | --- | --- |
    | URL | /api/statistics/summoners/records/indicators |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | body params  |  |  |  |  |
    | summonerName | String | true | 소환사명 | - |
    - **✅ Response 200**
        
        ```jsx
        { "message": "전적정보 및 통계지표 생성 완료" }
        ```
        

---

- `**[GET]` 통계지표 조회**
    
    
    | Description | 소환사명으로 통계지표를 조회합니다. |
    | --- | --- |
    | URL | /api/statistics/summoners/{summonerName}/indicators |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | url params  |  |  |  |  |
    | summonerName | String | true | 소환사명 | - |
    - **✅ Response 200**
        
        ```jsx
        {
        "summonerIndicator": {
        		"leagueIndicator": {...},
        		"macroIndicator": {...},
        		"etcIndicator": {...},
        		"landIndicator": {...},
        		"userIndicator": {...}
        	}
        }
        ```
        
    

---

- `**[GET]` 전적정보 조회**
    
    
    | Description | 소환사명으로 전적정보를 조회합니다. |
    | --- | --- |
    | URL | /api/statistics/summoners/{summonerName}/records |
    | Auth Required | No |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | url params  |  |  |  |  |
    | summonerName | String | true | 소환사명 | - |
    - **✅ Response 200**
        
        ```jsx
        {
            "metadata": {
                "dataVersion": "2",
                "matchId": "KR_매치ID",
                "participants": [
                    ...
                ]
            },
            "info": {
            ...
            }
        }
        ```
        
    

---

- `**[POST]` 통계지표 생성**
    
    
    | Description | 소환사명으로 통계지표를 생성합니다. |
    | --- | --- |
    | URL | /api/statistics/summoners/indicators |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | body params summonerName | String | true | 소환사명 | - |
    - **✅ Response 200**
        
        ```jsx
        { "message": "통계지표 생성 완료" }
        ```
        

---

- `**[POST]` 전적정보 생성**
    
    
    | Description | 소환사명으로 전적정보를 생성합니다. |
    | --- | --- |
    | URL | /api/statistics/summoners/records |
    | Auth Required | No |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | body params  |  |  |  |  |
    | summonerName | String | true | 소환사명 | - |
    - **✅ Response 200**
        
        ```jsx
        { "message": "전적정보 생성 완료" }
        ```
        

---

# 추천(recommand)

- `**[GET]` 유저 추천받기**
    
    
    | Description | 소환사명으로 유저 추천을 요청합니다. |
    | --- | --- |
    | URL | /api/recommands/{summonerName} |
    | Auth Required | Yes |
    
    | Paramater | Type  | Required | Description | Default |
    | --- | --- | --- | --- | --- |
    | url params  |  |  |  |  |
    | summonerName | String | true | 소환사명 | - |
    - **✅ Response 200**
        
        ```jsx
        {
        "recommandUsers": [
        		1: {
        			"summonerName": // 유저 이름
        			"profileImage": // 사진경로
        			"predicted": 60.3 // 예측 승률
        		}
        		...
        	]
        }
        ```
        
    

---