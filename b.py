import joblib
import pandas as pd
import numpy as np
import scipy.stats
import pymongo
import a

scaler = joblib.load("statistics/scalers/diamond_1_scaler.joblib")

b1 = pd.DataFrame([a.a1()])

scaled_data = scaler.transform(b1)

percentiles = np.round(scipy.stats.norm.cdf(scaled_data) * 100, 2)

for index, percentile in enumerate(percentiles[0]):
    print(index, percentile)
