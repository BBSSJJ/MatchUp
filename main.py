from fastapi import FastAPI

import heapq
import joblib
import pandas as pd

import apis.user
import algorithms.enjoying

matchup = FastAPI()

# 승률 기반의 유저 추천
@matchup.get("/api/recommends/winning/{user_id}")
async def winning(user_id: int):
    # 유저 정보 가져오기
    tier, division = apis.user.get_user_tier(user_id)
    divisions = {"I": 1, "II": 2, "III": 3, "IV": 4}

    if tier == "DIAMOND":
        mlp = joblib.load(f'models/winning_models/{tier.lower()}_{divisions[division]}_model.pkl')
    else:
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
@matchup.get("/api/recommends/enjoying/{user_id}")
async def enjoying(user_id: int):
    # 유저 목록 불러오기
    user_list = apis.user.get_users(user_id)

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

        user_infos.append(apis.user.get_user_records_for_recommend(recommendation_user_id))

    return user_infos

# 유저 상세 정보
@matchup.get("/api/recommends/statistics/{user_id}")
async def user_info(user_id: int):
    # 유저 데이터 불러오기
    user_df = pd.DataFrame()

    tier = 'gold'

    # scaler 불러오기
    scaler = joblib.load(f"statistics/scalers/{tier}.joblib")

    return {}
