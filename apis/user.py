import requests
import pymongo
import pandas as pd

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


def get_user_indicator(user_id: int):
    # MongoDB 연결
    client = pymongo.MongoClient("mongodb://mongodb-statistics-service:3311/")

    # DB 불러오기
    matchup_statistics_db = client["matchup_statistics_db"]

    # 해당하는 유저 정보만 불러오기
    document = matchup_statistics_db["indicators"].find_one({"_id": get_user_puuid(user_id)})

    match_indicator = document["matchIndicatorStatistics"]

    # 유저 지표 불러오기

    user_indicator = {}

    user_indicator["csDiffer"] = match_indicator["laneIndicatorAvg"]["basicWeight"]["csDiffer"]
    user_indicator["expDiffer"] = match_indicator["laneIndicatorAvg"]["basicWeight"]["expDiffer"]
    user_indicator["turretPlateDestroyDiffer"] = match_indicator["laneIndicatorAvg"]["basicWeight"]["turretPlateDestroyDiffer"]
    user_indicator["dealDiffer"] = match_indicator["laneIndicatorAvg"]["aggresiveLaneAbility"]["dealDiffer"]

    user_indicator["turretKillsPerDeaths"] = match_indicator["macroIndicatorAvg"]["splitPoint"]["turretKillsPerDeaths"]
    user_indicator["damageDealtToTurretsPerTeamTotalTowerDamageDone"] = match_indicator["macroIndicatorAvg"]["splitPoint"]["damageDealtToTurretsPerTeamTotalTowerDamageDone"]

    user_indicator["totalTimeCCingOthersPerTotalDamageTaken"] = match_indicator["macroIndicatorAvg"]["initiatingPoint"]["totalTimeCCingOthersPerTotalDamageTaken"]
    user_indicator["totalDamageTakenPerTeamTotalDamageTaken"] = match_indicator["macroIndicatorAvg"]["initiatingPoint"]["totalDamageTakenPerTeamTotalDamageTaken"]
    user_indicator["damageSelfMitigatedPerTotalDamageTaken"] = match_indicator["macroIndicatorAvg"]["initiatingPoint"]["damageSelfMitigatedPerTotalDamageTaken"]

    user_indicator["visionScorePerDeath"] = match_indicator["macroIndicatorAvg"]["visionPoint"]["visionScorePerDeath"]
    user_indicator["totalJungleObjectivePerGameDuration"] = match_indicator["macroIndicatorAvg"]["jungleHoldPoint"]["totalJungleObjectivePerGameDuration"]

    user_indicator["getObjectiveDifferPerGameDuration"] = match_indicator["macroIndicatorAvg"]["objectivePoint"]["getObjectiveDifferPerGameDuration"]

    user_indicator["damagePerMinute"] = match_indicator["macroIndicatorAvg"]["totalDealPoint"]["damagePerMinute"]
    user_indicator["dealPerGold"] = match_indicator["macroIndicatorAvg"]["totalDealPoint"]["dealPerGold"]
    user_indicator["teamDamagePercentage"] = match_indicator["macroIndicatorAvg"]["totalDealPoint"]["teamDamagePercentage"]

    user_indicator_df = pd.DataFrame([user_indicator], index=[0])

    return user_indicator
