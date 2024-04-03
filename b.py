import joblib
import pandas as pd
import numpy as np
import scipy.stats
import pymongo
import a

scaler = joblib.load("statistics/scalers/gold_scaler.joblib")

scaled_data = scaler.transform(a.a1())

percentiles = np.round(scipy.stats.norm.cdf(scaled_data) * 100, 2)

print(percentiles[0])
