from fastapi import FastAPI

import heapq
import joblib
import pandas as pd

from apis.user import get_user_tier

matchup = FastAPI()

# 승률 기반의 유저 추천
@matchup.get("/api/recommends/winning/{user_id}")
async def winning(user_id: int):
    # 유저 정보 가져오기
    tier, division = get_user_tier(user_id)

    # 적당한 모델 불러오기
    mlp = joblib.load(f'models/winning_models/{tier.lower()}_model.pkl')

    # 승률 상위 10명 불러오기
    probabilities = mlp.predict_proba()

    recommendations = []

    for i, probability in enumerate(probabilities):
        heapq.heappush(recommendations, (-probability[1], i))

    top_10 = []

    for _ in range(10):
        top_10.append(heapq.heappop(recommendations))

    return [1, 2, 3, 4, 5]

# 즐거움 기반의 유저 추천
@matchup.get("/api/recommends/2/{user_id}")
async def enjoying(user_id: int):
    # 유저 정보 가져오기

    # 유사 유저 10명 불러오기

    return [6, 7, 8, 9, 10]

# 유저 상세 정보
@matchup.get("/api/recommends/statistics/{user_id}")
async def user_info(user_id: int):
    # 유저 데이터 불러오기
    user_df = pd.DataFrame()

    tier = 'gold'

    # scaler 불러오기
    scaler = joblib.load(f"statistics/scalers/{tier}.joblib")

    return {}
