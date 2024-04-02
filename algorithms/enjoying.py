import faiss
import pymongo

def ten_neighbors(user_list, user_id):
    puuid_list = []

    for user in user_list:
        puuid_list.append(user["riotId"])

    # MongoDB 연결
    client = pymongo.MongoClient("mongodb://mongodb-statistics-service:3311/")

    # DB 불러오기
    matchup_statistics_db = client["matchup_statistics_db"]
    
    # 해당하는 유저 정보만 불러오기
    documents = matchup_statistics_db["indicators"].find({"_id": {"$in": puuid_list}})

    # 유저 벡터 만들기
    user_vectors = []

    for document in documents:
        total_count = document["metadata"]["totalCount"]
        time_duration = document["metadata"]["timeDurationAvg"]
        early_surrender = document["metadata"]["isOurTeamEarlySurrenderedCount"]
        ping_count = document["metadata"]["pingCountAvg"]

        user_vector = [time_duration, early_surrender / total_count, ping_count]

        user_vectors.append(user_vector)

    # 인덱스 생성
    index = faiss.IndexFlatL2(3)
    index.add(user_vectors)

    # 본인 벡터 만들기

    my_document = matchup_statistics_db["indicators"].find_one({"_id": user_id})
    
    my_total_count = my_document["metadata"]["totalCount"]
    my_time_duration = my_document["metadata"]["timeDurationAvg"]
    my_early_surrender = my_document["metadata"]["isOurTeamEarlySurrenderedCount"]
    my_ping_count = my_document["metadata"]["pingCountAvg"]

    my_vector = [[my_time_duration, my_early_surrender / my_total_count, my_ping_count]]

    distance_list, index_list = index.search(my_vector, 10)

    user_ids = []

    for index in index_list:
        user_ids.append(user_list[index]["userId"])

    return user_ids
