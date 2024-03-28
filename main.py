import models.check as check
from fastapi import FastAPI

matchup = FastAPI()


@matchup.get("/api/recommend/1/{user_pk}")
async def recommendation_1(user_pk: int):
    return {"users": [1, 2, 3, 4, 5, check(user_pk)]}


@matchup.get("/api/recommend/2/{user_pk}")
async def recommendation_2(user_pk: int):
    return {"users": [6, 7, 8, 9, 10, user_pk]}
