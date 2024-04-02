import pymongo


def top_ten(user_list):
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
        for match_indicator in document["matchIndicatorStatistics"]:
            user_indicator = {}

        user_indicator["csDiffer"] = match_indicator["laneIndicatorAvgAvgAvg"]["basicWeight"]["csDiffer"]
        user_indicator["expDiffer"] = match_indicator["laneIndicatorAvgAvgAvg"]["basicWeight"]["expDiffer"]
        user_indicator["turretPlateDestroyDiffer"] = match_indicator["laneIndicatorAvgAvgAvg"]["basicWeight"]["turretPlateDestroyDiffer"]
        user_indicator["dealDiffer"] = match_indicator["laneIndicatorAvgAvgAvg"]["aggressiveLaneAbility"]["dealDiffer"]

        user_indicator["turretKillsPerDeaths"] = match_indicator["macroIndicatorAvgAvg"]["splitPoint"]["turretKillsPerDeaths"]
        user_indicator["damageDealtToTurretsPerTeamTotalTowerDamageDone"] = match_indicator["macroIndicatorAvgAvg"]["splitPoint"]["damageDealtToTurretsPerTeamTotalTowerDamageDone"]

        user_indicator["totalTimeCCingOthersPerTotalDamageTaken"] = match_indicator["macroIndicatorAvgAvg"]["initiatingPoint"]["totalTimeCCingOthersPerTotalDamageTaken"]
        user_indicator["totalDamageTakenPerTeamTotalDamageTaken"] = match_indicator["macroIndicatorAvgAvg"]["initiatingPoint"]["totalDamageTakenPerTeamTotalDamageTaken"]
        user_indicator["damageSelfMitigatedPerTotalDamageTaken"] = match_indicator["macroIndicatorAvgAvg"]["initiatingPoint"]["damageSelfMitigatedPerTotalDamageTaken"]

        user_indicator["visionScorePerDeath"] = match_indicator["macroIndicatorAvgAvg"]["visionPoint"]["visionScorePerDeath"]
        user_indicator["totalJungleObjectivePerGameDuration"] = match_indicator["macroIndicatorAvgAvg"]["jungleHoldPoint"]["totalJungleObjectivePerGameDuration"]

        user_indicator["getObjectiveDifferPerGameDuration"] = match_indicator["macroIndicatorAvgAvg"]["objectivePoint"]["getObjectiveDifferPerGameDuration"]

        user_indicator["damagePerMinute"] = match_indicator["macroIndicatorAvgAvg"]["totalDealPoint"]["damagePerMinute"]
        user_indicator["dealPerGold"] = match_indicator["macroIndicatorAvgAvg"]["totalDealPoint"]["dealPerGold"]
        user_indicator["teamDamagePercentage"] = match_indicator["macroIndicatorAvgAvg"]["totalDealPoint"]["teamDamagePercentage"]

        user_indicators.append(user_indicator)

    return user_indicators
