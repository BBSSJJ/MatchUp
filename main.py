import fastapi

import heapq
import joblib
import numpy as np
import pandas as pd
import scipy.stats

import apis.user
import algorithms.enjoying
import algorithms.models.winning.winning

matchup = fastapi.FastAPI()

# 승률 기반의 유저 추천
@matchup.get("/api/recommends/winning/{user_id}")
async def winning(user_id: int, mic: bool, my_lane: str, partner_lane: str):  # lane = ["top", "jungle", "mid", "bottom", "support"]
    # 내 티어 정보 가져오기
    tier, division = apis.user.get_user_tier(user_id)
    divisions = {"I": 1, "II": 2, "III": 3, "IV": 4}

    # 승률 예측 모델 불러오기
    if tier == "DIAMOND" or (tier == "EMERALD" and divisions[division] <= 2):
        mlp = joblib.load(f'algorithms/models/winning/{tier.lower()}_{divisions[division]}_model.pkl')
    else:
        mlp = joblib.load(f'algorithms/models/winning/{tier.lower()}_model.pkl')

    # 유저 목록 불러오기
    user_list = apis.user.get_users(user_id, mic)

    # 추천 불가 유저 처리
    if user_list is False:
        return []
    
    # 유저 지표 불러오기
    user_indicators = pd.DataFrame(algorithms.models.winning.winning.top_ten(user_list))

    # 유저 지표 정규화
    normalized_user_indicators = (user_indicators - user_indicators.min()) / (user_indicators.max() - user_indicators.min())

    # 승률 상위 10명 불러오기
    probabilities = mlp.predict_proba(normalized_user_indicators)

    recommendations = []

    for i, probability in enumerate(probabilities):
        heapq.heappush(recommendations, (-probability[1], i))

    # 유저 정보 반환하기
    user_infos = []

    for _ in range(10):
        probability, user_index = heapq.heappop(recommendations)

        user_infos.append(apis.user.get_user_profile(user_list[user_index]["userId"]))

    return user_infos

# 즐거움 기반의 유저 추천
@matchup.get("/api/recommends/enjoying/{user_id}")
async def enjoying(user_id: int, mic: bool, my_lane: str, partner_lane: str):
    # 유저 목록 불러오기
    user_list = apis.user.get_users(user_id, mic)

    # 추천 불가 유저 처리
    if user_list is False:
        return []

    # 유저 평가 데이터 확인
    my_ratings = apis.user.get_user_ratings(user_id)

    # 추천 리스트
    recommendations = []

    if len(my_ratings) < 10:
        nearest_neighbors = algorithms.enjoying.ten_neighbors(user_list, apis.user.get_user_puuid(user_id))

        weight = 1
        neighbor_weight = 0

        for neighbor in nearest_neighbors:
            heapq.heappush(recommendations, (weight, neighbor))

            neighbor_ratings = apis.user.get_user_ratings(neighbor)

            for neighbor_rating in neighbor_ratings:
                score = neighbor_rating["score"]

                if score == 5:
                    heapq.heappush(recommendations, (neighbor_weight, neighbor_rating["feedbackedUserId"]))
                    neighbor_weight += 0.1

            if len(recommendations) >= 10 + weight:
                break
            else:
                weight += 1

    else:
        pass

    # 유저 정보 반환하기
    user_infos = []

    for _ in range(10):
        w, recommendation_user_id = heapq.heappop(recommendations)

        user_infos.append(apis.user.get_user_profile(recommendation_user_id))

    return user_infos

# 유저 상세 정보
@matchup.get("/api/recommends/statistics/{user_id}")
async def user_info(user_id: int):
    # 유저 티어 정보 가져오기
    divisions = {"I": 1, "II": 2, "III": 3, "IV": 4}

    tier, division = apis.user.get_user_tier(user_id)

    # scaler 불러오기
    if tier == "DIAMOND" or (tier == "EMERALD" and divisions[division] <= 2):
        scaler = joblib.load(f"statistics/scalers/{tier.lower()}_{divisions[division]}_scaler.joblib")
    else:
        scaler = joblib.load(f"statistics/scalers/{tier.lower()}_scaler.joblib")

    # 유저 지표 불러오기
    user_indicator = apis.user.get_user_indicator(user_id)
    user_indicator_df = pd.DataFrame([user_indicator])

    # 유저 상세 정보 확인
    scaled_data = scaler.transform(user_indicator_df)
    percentiles = np.round(scipy.stats.norm.cdf(scaled_data) * 100, 2)

    return user_indicator
