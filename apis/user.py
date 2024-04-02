import typing
import requests


def get_user_puuid(user_id: int):
    response = requests.get(f'https://matchup.site/api/users/{user_id}').json()

    puuid = response['riotAccount']['id']

    return puuid


def get_user_tier(user_id: int):
    response = requests.get(f'https://matchup.site/api/users/{user_id}').json()

    tier = response['riotAccount']['tier']
    division = response['riotAccount']['leagueRank']

    return tier, division


def get_users(user_id: int):
    response = requests.get(f'https://matchup.site/api/users/{user_id}/tier-list').json()

    return response


def get_user_ratings(user_id: int):
    response = requests.get(f'https://matchup.site/api/feedbacks/users/{user_id}').json()

    return response['list']


def get_user_records_for_recommend(user_id: int):
    response = requests.get(f'https://matchup.site/api/statistics/summoners/records/users/{user_id}').json()

    user_record = {}
    
    user_record['profileIconId'] = response['summonerInfo']['profileIconId']
    user_record['name'] = response['summonerInfo']['name']
    user_record['top3Champions'] = response['record']['top3Champions']
    user_record['winRate'] = response['record']['winRate']

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
