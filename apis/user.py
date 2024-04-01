import typing
import requests


def get_user_tier(user_id: int):
    response = requests.get(f'https://matchup.site/api/users/{user_id}').json()

    tier = response['riotAccount']['tier']
    division = response['riotAccount']['leagueRank']

    return tier, division


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
