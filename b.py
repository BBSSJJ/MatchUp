import joblib
import pandas as pd
import numpy as np
import scipy.stats
import pymongo
import a
import heapq
import apis.user

scaler = joblib.load("statistics/scalers/diamond_1_scaler.joblib")

b1 = pd.DataFrame([a.a1()])

scaled_data = scaler.transform(b1)

percentiles = np.round(scipy.stats.norm.cdf(scaled_data) * 100, 2)

user_keywords = []
keywords = []

for index, percentile in enumerate(percentiles[0]):
    if 0 <= index < 4:
        user_keywords.append(apis.user.get_user_keyword(index, percentile))
    else:
        heapq.heappush(keywords, (-percentile, apis.user.get_user_keyword(index, percentile)))

for _ in range(2):
    print(heapq.heappop(keywords))
    print(heapq.heappop(keywords)[1])
