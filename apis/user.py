import typing
import requests
import pymongo

def get_user_puuid(user_id: int):
    response = requests.get(f'https://matchup.site/api/users/{user_id}').json()

    puuid = response['riotAccount']['id']

    return puuid


def get_user_tier(user_id: int):
    response = requests.get(f'https://matchup.site/api/users/{user_id}').json()

    tier = response['riotAccount']['tier']
    division = response['riotAccount']['leagueRank']

    return tier, division


def get_users(user_id: int, mic: bool):
    response = requests.get(f'https://matchup.site/api/users/{user_id}/tier-list', params={'mic': mic})

    if response.status_code == 200:
        return response.json()
    else:
        return False


def get_user_ratings(user_id: int):
    response = requests.get(f'https://matchup.site/api/feedbacks/users/{user_id}').json()

    return response['list']


def get_user_profile(user_id: int):
    # MongoDB 연결
    client = pymongo.MongoClient("mongodb://mongodb-statistics-service:3311/")

    # DB 불러오기
    matchup_statistics_db = client["matchup_statistics_db"]
    
    # 해당하는 유저 정보만 불러오기
    summoners_document = matchup_statistics_db["summoners"].find_one({"_id": get_user_puuid(user_id)})

    user_record = {}
    
    user_record["profile"] = summoners_document["summonerDetail"]["profileIconId"]
    user_record["name"] = summoners_document["account"]["gameName"]
    user_record["tag"] = summoners_document["account"]["tagLine"]
    user_record["tier"] = summoners_document["league"]["tier"]
    user_record["rank"] = summoners_document["league"]["rank"]
    user_record["wins"] = summoners_document["league"]["wins"]
    user_record["losses"] = summoners_document["league"]["losses"]

    return user_record


def get_user_infos(users: typing.List[int]):
    user_infos = {}
    user_num = 1

    for user in users:
        # 프로필 이미지

        # 닉네임

        # 최근 가장 많이 한 챔프 3개

        # 평균 평점

        user_infos[user_num] = user
        user_num += 1

    return
