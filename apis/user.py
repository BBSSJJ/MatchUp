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

    return user_indicator


def get_user_lane_indicator(user_id: int, lane: str):
    # MongoDB 연결
    client = pymongo.MongoClient("mongodb://mongodb-statistics-service:3311/")

    # DB 불러오기
    matchup_statistics_db = client["matchup_statistics_db"]

    # 해당하는 유저 정보만 불러오기
    document = matchup_statistics_db["indicators"].find_one({"_id": get_user_puuid(user_id)})

    if document["matchIndicatorStatistics"]["metadata"]["teamPositionCount"].get(lane, -1) == -1:
        return get_user_indicator(user_id)

    match_indicators = document["matchIndicators"]

    # 유저 지표 불러오기
    user_indicator = {
        "csDiffer": 0,
        "expDiffer": 0,
        "turretPlateDestroyDiffer": 0,
        "dealDiffer": 0,
        "turretKillsPerDeaths": 0,
        "damageDealtToTurretsPerTeamTotalTowerDamageDone": 0,
        "totalTimeCCingOthersPerTotalDamageTaken": 0,
        "totalDamageTakenPerTeamTotalDamageTaken": 0,
        "damageSelfMitigatedPerTotalDamageTaken": 0,
        "visionScorePerDeath": 0,
        "totalJungleObjectivePerGameDuration": 0,
        "getObjectiveDifferPerGameDuration": 0,
        "damagePerMinute": 0,
        "dealPerGold": 0,
        "teamDamagePercentage": 0,
    }

    count = 0

    for match_indicator in match_indicators:
        if match_indicator["metadata"]["laneInfo"].get("teamPosition") != lane:
            continue

        count += 1

        user_indicator["csDiffer"] = match_indicator["laneIndicator"]["basicWeight"]["csDiffer"]
        user_indicator["expDiffer"] = match_indicator["laneIndicator"]["basicWeight"]["expDiffer"]
        user_indicator["turretPlateDestroyDiffer"] = match_indicator["laneIndicator"]["basicWeight"]["turretPlateDestroyDiffer"]
        user_indicator["dealDiffer"] = match_indicator["laneIndicator"]["aggresiveLaneAbility"]["dealDiffer"]

        user_indicator["turretKillsPerDeaths"] = match_indicator["macroIndicator"]["splitPoint"]["turretKillsPerDeaths"]
        user_indicator["damageDealtToTurretsPerTeamTotalTowerDamageDone"] = match_indicator["macroIndicator"]["splitPoint"]["damageDealtToTurretsPerTeamTotalTowerDamageDone"]

        user_indicator["totalTimeCCingOthersPerTotalDamageTaken"] = match_indicator["macroIndicator"]["initiatingPoint"]["totalTimeCCingOthersPerTotalDamageTaken"]
        user_indicator["totalDamageTakenPerTeamTotalDamageTaken"] = match_indicator["macroIndicator"]["initiatingPoint"]["totalDamageTakenPerTeamTotalDamageTaken"]
        user_indicator["damageSelfMitigatedPerTotalDamageTaken"] = match_indicator["macroIndicator"]["initiatingPoint"]["damageSelfMitigatedPerTotalDamageTaken"]

        user_indicator["visionScorePerDeath"] = match_indicator["macroIndicator"]["visionPoint"]["visionScorePerDeath"]
        user_indicator["totalJungleObjectivePerGameDuration"] = match_indicator["macroIndicator"]["jungleHoldPoint"]["totalJungleObjectivePerGameDuration"]

        user_indicator["getObjectiveDifferPerGameDuration"] = match_indicator["macroIndicator"]["objectivePoint"]["getObjectiveDifferPerGameDuration"]

        user_indicator["damagePerMinute"] = match_indicator["macroIndicator"]["totalDealPoint"]["damagePerMinute"]
        user_indicator["dealPerGold"] = match_indicator["macroIndicator"]["totalDealPoint"]["dealPerGold"]
        user_indicator["teamDamagePercentage"] = match_indicator["macroIndicator"]["totalDealPoint"]["teamDamagePercentage"]

    for indicator in user_indicator:
        if user_indicator[indicator] != 0:
            user_indicator[indicator] = user_indicator[indicator] / count

    return user_indicator


def get_user_keyword(index: int, percentile: float):
    if index == -4:
        return [{
            "keyword": "MASTER",
            "content": "마스터 티어 유저",
            "rank": 1
        }]

    elif index == -3:
        return [{
            "keyword": "GRANDMASTER",
            "content": "그랜드 마스터 티어 유저",
            "rank": 1,
        }]

    elif index == -2:
        return [{
            "keyword": "CHALLENGER",
            "content": "챌린저 티어 유저",
            "rank": 1,
        }]
    
    elif index == -1:
        return [{
            "keyword": "롤린이",
            "content": "랭크 게임 플레이 경험이 없는 유저",
            "rank": 3,
        }]

    elif index == 0:
        if percentile > 0.66:
            return {
                "keyword": "농사 대박 나서스",
                "content": "라인전 CS 차이 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "나서스",
                "content": "라인전 CS 차이 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "베인한테 맞는 나서스",
                "content": "라인전 CS 차이 하위 유저",
                "rank": 3
            }

    elif index == 1:
        if percentile > 0.66:
            return {
                "keyword": "닐라, 질리언 듀오",
                "content": "라인전 경험치 차이 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "1렙차",
                "content": "라인전 경험치 차이 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "흑백화면 장인",
                "content": "라인전 경험치 차이 하위 유저",
                "rank": 3
            }
        
    elif index == 2:
        if percentile > 0.66:
            return {
                "keyword": "니 포탑 쩔더라",
                "content": "라인전 채굴 차이 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "갱오면 타워 나감",
                "content": "라인전 채굴 차이 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "갱 안오면 우리 타워 나감",
                "content": "라인전 채굴 차이 하위 유저",
                "rank": 3
            }
        
    elif index == 3:
        if percentile > 0.66:
            return {
                "keyword": "사디스트",
                "content": "라인전 딜량 차이 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "자강두천",
                "content": "라인전 딜량 차이 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "마조히스트",
                "content": "라인전 딜량 차이 하위 유저",
                "rank": 3
            }
    
    elif index == 4:
        if percentile > 0.66:
            return {
                "keyword": "선체파괴자",
                "content": "타워 철거 차이 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "앉은체유지자",
                "content": "타워 철거 차이 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "누운체생성자",
                "content": "타워 철거 차이 하위 유저",
                "rank": 3
            }
    
    elif index == 5:
        if percentile > 0.66:
            return {
                "keyword": "롤로노아 김동현",
                "content": "팀 타워 철거 비중 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "3반 반장 김동현",
                "content": "팀 타워 철거 비중 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "싹토리 심동현",
                "content": "팀 타워 철거 비중 하위 유저",
                "rank": 3
            }

    elif index == 6:
        if percentile > 0.66:
            return {
                "keyword": "대상혁",
                "content": "이니시 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "중상혁",
                "content": "이니시 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "소상혁",
                "content": "이니시 하위 유저",
                "rank": 3
            }
    
    elif index == 7:
        if percentile > 0.66:
            return {
                "keyword": "매 맞는 일라오이",
                "content": "팀 받은 데미지 비중 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "매 맞는 티모",
                "content": "팀 받은 데미지 비중 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "매 맞는 유미",
                "content": "팀 받은 데미지 비중 하위 유저",
                "rank": 3
            }

    elif index == 8:
        if percentile > 0.66:
            return {
                "keyword": "텟카이",
                "content": "감소시킨 데미지 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "몸 말아 웅크리기",
                "content": "감소시킨 데미지 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "말랑카우",
                "content": "감소시킨 데미지 하위 유저",
                "rank": 3
            }
    
    elif index == 9:
        if percentile > 0.66:
            return {
                "keyword": "맵핵",
                "content": "시야점수 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "인간 와드",
                "content": "시야점수 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "리신",
                "content": "시야점수 중위 유저",
                "rank": 3
            }
        
    elif index == 10:
        if percentile > 0.66:
            return {
                "keyword": "게임 ㅈ 같이 하네",
                "content": "상대 정글 장악 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "날 선택해줄 줄은 정말 몰랐어",
                "content": "상대 정글 장악 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "우리... 영원히 친구하자",
                "content": "상대 정글 장악 하위 유저",
                "rank": 3
            }
    
    elif index == 11:
        if percentile > 0.66:
            return {
                "keyword": "돌잡이 때 마체테 잡음",
                "content": "획득한 오브젝트 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "클템",
                "content": "획득한 오브젝트 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "우리정글머함?",
                "content": "획득한 오브젝트 하위 유저",
                "rank": 3
            }
    
    elif index == 12:
        if percentile > 0.66:
            return {
                "keyword": "딜 넣을게",
                "content": "분당 데미지 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "잡았죠?",
                "content": "분당 데미지 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "딜 박힐게",
                "content": "분당 데미지 하위 유저",
                "rank": 3
            }
    
    elif index == 13:
        if percentile > 0.66:
            return {
                "keyword": "도파",
                "content": "골드당 데미지 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "압도",
                "content": "골드당 데미지 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "도구리",
                "content": "골드당 데미지 하위 유저",
                "rank": 3
            }
    
    elif index == 14:
        if percentile > 0.66:
            return {
                "keyword": "베인충",
                "content": "팀 가한 데미지 비중 상위 유저",
                "rank": 1
                }
        elif percentile > 0.33:
            return {
                "keyword": "가붕이",
                "content": "팀 가한 데미지 비중 중위 유저",
                "rank": 2
            }
        else:
            return {
                "keyword": "ㅈ냥이",
                "content": "팀 가한 데미지 비중 하위 유저",
                "rank": 3
            }
