from fastapi import FastAPI

matchup = FastAPI()


@matchup.get("/recommendation_1/")
async def recommendation_1():
    return {"users": [1, 2, 3, 4, 5]}


@matchup.get("/recommendation_2/")
async def recommendation_2():
    return {"users": [6, 7, 8, 9, 10]}
