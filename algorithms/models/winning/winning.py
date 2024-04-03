import pymongo


def top_ten(user_list, lane):
    puuid_list = []

    for user in user_list:
        puuid_list.append(user["riotId"])

    # MongoDB 연결
    client = pymongo.MongoClient("mongodb://mongodb-statistics-service:3311/")

    # DB 불러오기
    matchup_statistics_db = client["matchup_statistics_db"]
    
    # 해당하는 유저 정보만 불러오기
    documents = matchup_statistics_db["indicators"].find({"_id": {"$in": puuid_list}})

    # 유저 지표 저장
    user_indicators = []

    for document in documents:
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

        if document["matchIndicatorStatistics"]["metadata"]["teamPositionCount"].get(lane, -1) == -1:
            user_indicators.append(user_indicator)
            continue

        match_indicators = document["matchIndicators"]


        count = 0

        for match_indicator in match_indicators:
            if match_indicator["metadata"]["laneInfo"]["teamPosition"] != lane:
                continue
            
            count += 1

            user_indicator["csDiffer"] += match_indicator["laneIndicator"]["basicWeight"]["csDiffer"]
            user_indicator["expDiffer"] += match_indicator["laneIndicator"]["basicWeight"]["expDiffer"]
            user_indicator["turretPlateDestroyDiffer"] += match_indicator["laneIndicator"]["basicWeight"]["turretPlateDestroyDiffer"]
            user_indicator["dealDiffer"] += match_indicator["laneIndicator"]["aggresiveLaneAbility"]["dealDiffer"]

            user_indicator["turretKillsPerDeaths"] += match_indicator["macroIndicator"]["splitPoint"]["turretKillsPerDeaths"]
            user_indicator["damageDealtToTurretsPerTeamTotalTowerDamageDone"] += match_indicator["macroIndicator"]["splitPoint"]["damageDealtToTurretsPerTeamTotalTowerDamageDone"]

            user_indicator["totalTimeCCingOthersPerTotalDamageTaken"] += match_indicator["macroIndicator"]["initiatingPoint"]["totalTimeCCingOthersPerTotalDamageTaken"]
            user_indicator["totalDamageTakenPerTeamTotalDamageTaken"] += match_indicator["macroIndicator"]["initiatingPoint"]["totalDamageTakenPerTeamTotalDamageTaken"]
            user_indicator["damageSelfMitigatedPerTotalDamageTaken"] += match_indicator["macroIndicator"]["initiatingPoint"]["damageSelfMitigatedPerTotalDamageTaken"]

            user_indicator["visionScorePerDeath"] += match_indicator["macroIndicator"]["visionPoint"]["visionScorePerDeath"]
            user_indicator["totalJungleObjectivePerGameDuration"] += match_indicator["macroIndicator"]["jungleHoldPoint"]["totalJungleObjectivePerGameDuration"]

            user_indicator["getObjectiveDifferPerGameDuration"] += match_indicator["macroIndicator"]["objectivePoint"]["getObjectiveDifferPerGameDuration"]

            user_indicator["damagePerMinute"] += match_indicator["macroIndicator"]["totalDealPoint"]["damagePerMinute"]
            user_indicator["dealPerGold"] += match_indicator["macroIndicator"]["totalDealPoint"]["dealPerGold"]
            user_indicator["teamDamagePercentage"] += match_indicator["macroIndicator"]["totalDealPoint"]["teamDamagePercentage"]

        for indicator in user_indicator:
            if user_indicator[indicator] != 0:
                user_indicator[indicator] = user_indicator[indicator] / count


        user_indicators.append(user_indicator)

    return user_indicators
