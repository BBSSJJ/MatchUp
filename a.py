import joblib
import pandas as pd
import numpy as np
import scipy.stats
import pymongo

def a1():
    client = pymongo.MongoClient("mongodb://localhost:3999/")

    # DB 불러오기
    matchup_statistics_db = client["matchup_statistics_db"]

    # 해당하는 유저 정보만 불러오기
    document = matchup_statistics_db["indicators"].find_one({"_id": "t3C2JzNt9v8ypQ3PtL7v4c6VNvn-QdDDnm8-Pcnllyd65xPT"})

    match_indicator = document["matchIndicatorStatistics"]

    # 유저 지표 불러오기

    user_indicator = {}

    user_indicator["csDiffer"] = match_indicator["laneIndicatorAvg"]["basicWeight"]["csDiffer"]
    user_indicator["expDiffer"] = match_indicator["laneIndicatorAvg"]["basicWeight"]["expDiffer"]
    user_indicator["turretPlateDestroyDiffer"] = match_indicator["laneIndicatorAvg"]["basicWeight"]["turretPlateDestroyDiffer"]
    user_indicator["dealDiffer"] = match_indicator["laneIndicatorAvg"]["aggresiveLaneAbilility"]["dealDiffer"]

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

    user_indicator_df = pd.DataFrame([user_indicator])

    return user_indicator_df

# scaler = joblib.load("statistics/scalers/gold_scaler.joblib")

# scaled_data = scaler.transform(a1())

# percentiles = np.round(scipy.stats.norm.cdf(scaled_data) * 100, 2)

# print(percentiles)
