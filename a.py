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

    a2 = {
        "csDiffer": 5,
        "expDiffer": 337,
        "turretPlateDestroyDiffer": -0.583333333333333,
        "dealDiffer": 1030,
        "turretKillsPerDeaths": 18416,
        "damageDealtToTurretsPerTeamTotalTowerDamageDone": 15913,
        "totalTimeCCingOthersPerTotalDamageTaken": 78,
        "totalDamageTakenPerTeamTotalDamageTaken": 21372,
        "damageSelfMitigatedPerTotalDamageTaken": 65421,
        "visionScorePerDeath": 531569,
        "totalJungleObjectivePerGameDuration": 408,
        "getObjectiveDifferPerGameDuration": 46,
        "damagePerMinute": 66460000,
        "dealPerGold": 157810,
        "teamDamagePercentage": 0.201954810923512
    }

    return a2

# scaler = joblib.load("statistics/scalers/gold_scaler.joblib")

# scaled_data = scaler.transform(a1())

# percentiles = np.round(scipy.stats.norm.cdf(scaled_data) * 100, 2)

# print(percentiles)

# A는 하나의 행만 존재
A = pd.DataFrame({
    'Feature1': [3],
    'Feature2': [5],
    'Feature3': [7]
})

# B는 여러 개의 행이 존재
B = pd.DataFrame({
    'Feature1': [2, 4, 6],
    'Feature2': [3, 5, 7],
    'Feature3': [4, 6, 8]
})

# B의 각 행과 A의 평균을 구함
updated_B = (B + A.values) / 2

print(updated_B)

partner_lane = "jungle"
lanes = {"top": "TOP", "jungle": "JUNGLE", "mid": "MIDDLE", "bottom": "BOTTOM", "support": "UTILITY"}
print(lanes[partner_lane])