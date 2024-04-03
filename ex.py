import joblib
import pandas as pd
import numpy as np
import scipy.stats

scaler = joblib.load("statistics/scalers/gold_scaler.joblib")

user_indicator = {}

user_indicator["csDiffer"] = -10
user_indicator["expDiffer"] = 0
user_indicator["turretPlateDestroyDiffer"] = 0
user_indicator["dealDiffer"] = 0

user_indicator["turretKillsPerDeaths"] = 0
user_indicator["damageDealtToTurretsPerTeamTotalTowerDamageDone"] = 0

user_indicator["totalTimeCCingOthersPerTotalDamageTaken"] = 0
user_indicator["totalDamageTakenPerTeamTotalDamageTaken"] = 0
user_indicator["damageSelfMitigatedPerTotalDamageTaken"] = 0

user_indicator["visionScorePerDeath"] = 0
user_indicator["totalJungleObjectivePerGameDuration"] = 0

user_indicator["getObjectiveDifferPerGameDuration"] = 0

user_indicator["damagePerMinute"] = 10000000
user_indicator["dealPerGold"] = 0
user_indicator["teamDamagePercentage"] = 0

user_indicator_df = pd.DataFrame([user_indicator])

scaled_data = scaler.transform(user_indicator_df)

percentiles = np.round(scipy.stats.norm.cdf(scaled_data) * 100, 2)

print(percentiles)
