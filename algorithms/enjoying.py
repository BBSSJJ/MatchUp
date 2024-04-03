import faiss
import pymongo
import numpy as np

def ten_neighbors(user_list, user_puuid, user_id, my_lane, partner_lane):
    puuid_list = []

    for user in user_list:
        puuid_list.append(user["riotId"])

    # MongoDB 연결
    client = pymongo.MongoClient("mongodb://mongodb-statistics-service:3311/")

    # DB 불러오기
    matchup_statistics_db = client["matchup_statistics_db"]
    
    # 해당하는 유저 정보만 불러오기
    documents = matchup_statistics_db["indicators"].find({"_id": {"$in": puuid_list}})

    # 유저 벡터 만들기
    user_vectors = []

    if user_id % 2 == 0:
        for document in documents:
            total_count = document["matchIndicatorStatistics"]["metadata"]["totalCount"]
            time_duration = document["matchIndicatorStatistics"]["metadata"]["timeDurationAvg"]
            early_surrender = document["matchIndicatorStatistics"]["metadata"]["isOurTeamEarlySurrenderedCount"]
            ping_count = document["matchIndicatorStatistics"]["metadata"]["pingCountAvg"]

            user_vector = [time_duration, early_surrender / total_count, ping_count]

            user_vectors.append(user_vector)
    
    else:
        for document in documents:
            if document["matchIndicatorStatistics"]["metadata"]["teamPositionCount"].get(partner_lane, -1) == -1:
                user_vectors.append([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0])
                continue

            match_indicators = document["matchIndicators"]

            count = 0

            csDiffer = 0
            expDiffer = 0
            turretPlateDestroyDiffer = 0
            dealDiffer = 0
            turretKillsPerDeaths = 0
            damageDealtToTurretsPerTeamTotalTowerDamageDone = 0
            totalTimeCCingOthersPerTotalDamageTaken = 0
            totalDamageTakenPerTeamTotalDamageTaken = 0
            damageSelfMitigatedPerTotalDamageTaken = 0
            visionScorePerDeath = 0
            totalJungleObjectivePerGameDuration = 0
            getObjectiveDifferPerGameDuration = 0
            damagePerMinute = 0
            dealPerGold = 0
            teamDamagePercentage = 0

            for match_indicator in match_indicators:
                if match_indicator["metadata"]["laneInfo"].get("teamPosition") != partner_lane:
                    continue
                
                count += 1

                csDiffer += match_indicator["laneIndicator"]["basicWeight"]["csDiffer"]
                expDiffer += match_indicator["laneIndicator"]["basicWeight"]["expDiffer"]
                turretPlateDestroyDiffer += match_indicator["laneIndicator"]["basicWeight"]["turretPlateDestroyDiffer"]
                dealDiffer += match_indicator["laneIndicator"]["aggresiveLaneAbility"]["dealDiffer"]

                turretKillsPerDeaths += match_indicator["macroIndicator"]["splitPoint"]["turretKillsPerDeaths"]
                damageDealtToTurretsPerTeamTotalTowerDamageDone += match_indicator["macroIndicator"]["splitPoint"]["damageDealtToTurretsPerTeamTotalTowerDamageDone"]

                totalTimeCCingOthersPerTotalDamageTaken += match_indicator["macroIndicator"]["initiatingPoint"]["totalTimeCCingOthersPerTotalDamageTaken"]
                totalDamageTakenPerTeamTotalDamageTaken += match_indicator["macroIndicator"]["initiatingPoint"]["totalDamageTakenPerTeamTotalDamageTaken"]
                damageSelfMitigatedPerTotalDamageTaken += match_indicator["macroIndicator"]["initiatingPoint"]["damageSelfMitigatedPerTotalDamageTaken"]

                visionScorePerDeath += match_indicator["macroIndicator"]["visionPoint"]["visionScorePerDeath"]
                totalJungleObjectivePerGameDuration += match_indicator["macroIndicator"]["jungleHoldPoint"]["totalJungleObjectivePerGameDuration"]

                getObjectiveDifferPerGameDuration += match_indicator["macroIndicator"]["objectivePoint"]["getObjectiveDifferPerGameDuration"]

                damagePerMinute += match_indicator["macroIndicator"]["totalDealPoint"]["damagePerMinute"]
                dealPerGold += match_indicator["macroIndicator"]["totalDealPoint"]["dealPerGold"]
                teamDamagePercentage += match_indicator["macroIndicator"]["totalDealPoint"]["teamDamagePercentage"]

            user_vector = [
                csDiffer / count,
                expDiffer / count,
                turretPlateDestroyDiffer / count,
                dealDiffer / count,
                turretKillsPerDeaths / count,
                damageDealtToTurretsPerTeamTotalTowerDamageDone / count,
                totalTimeCCingOthersPerTotalDamageTaken / count,
                totalDamageTakenPerTeamTotalDamageTaken / count,
                damageSelfMitigatedPerTotalDamageTaken / count,
                visionScorePerDeath / count,
                totalJungleObjectivePerGameDuration / count,
                getObjectiveDifferPerGameDuration / count,
                damagePerMinute / count,
                dealPerGold / count,
                teamDamagePercentage / count
            ]

            user_vectors.append(user_vector)

    # 인덱스 생성
    index = faiss.IndexFlatL2(3)
    index.add(np.array(user_vectors).astype('float32'))

    # 본인 벡터 만들기

    # 본인 puuid 불러오기
    my_document = matchup_statistics_db["indicators"].find_one({"_id": user_puuid})
    
    if user_id % 2 == 0:
        my_total_count = my_document["matchIndicatorStatistics"]["metadata"]["totalCount"]
        my_time_duration = my_document["matchIndicatorStatistics"]["metadata"]["timeDurationAvg"]
        my_early_surrender = my_document["matchIndicatorStatistics"]["metadata"]["isOurTeamEarlySurrenderedCount"]
        my_ping_count = my_document["matchIndicatorStatistics"]["metadata"]["pingCountAvg"]

        my_vector = [my_time_duration, my_early_surrender / my_total_count, my_ping_count]
    
    else:
        if document["matchIndicatorStatistics"]["metadata"]["teamPositionCount"].get(my_lane, -1) == -1:
            match_indicator = document["matchIndicatorStatistics"]

            my_vector = [
                match_indicator["laneIndicatorAvg"]["basicWeight"]["csDiffer"],
                match_indicator["laneIndicatorAvg"]["basicWeight"]["expDiffer"],
                match_indicator["laneIndicatorAvg"]["basicWeight"]["turretPlateDestroyDiffer"],
                match_indicator["laneIndicatorAvg"]["aggresiveLaneAbility"]["dealDiffer"],
                match_indicator["macroIndicatorAvg"]["splitPoint"]["turretKillsPerDeaths"],
                match_indicator["macroIndicatorAvg"]["splitPoint"]["damageDealtToTurretsPerTeamTotalTowerDamageDone"],
                match_indicator["macroIndicatorAvg"]["initiatingPoint"]["totalTimeCCingOthersPerTotalDamageTaken"],
                match_indicator["macroIndicatorAvg"]["initiatingPoint"]["totalDamageTakenPerTeamTotalDamageTaken"],
                match_indicator["macroIndicatorAvg"]["initiatingPoint"]["damageSelfMitigatedPerTotalDamageTaken"],
                match_indicator["macroIndicatorAvg"]["visionPoint"]["visionScorePerDeath"],
                match_indicator["macroIndicatorAvg"]["jungleHoldPoint"]["totalJungleObjectivePerGameDuration"],
                match_indicator["macroIndicatorAvg"]["objectivePoint"]["getObjectiveDifferPerGameDuration"],
                match_indicator["macroIndicatorAvg"]["totalDealPoint"]["damagePerMinute"],
                match_indicator["macroIndicatorAvg"]["totalDealPoint"]["dealPerGold"],
                match_indicator["macroIndicatorAvg"]["totalDealPoint"]["teamDamagePercentage"]
            ]

        else:
            match_indicators = document["matchIndicators"]

            count = 0

            csDiffer = 0
            expDiffer = 0
            turretPlateDestroyDiffer = 0
            dealDiffer = 0
            turretKillsPerDeaths = 0
            damageDealtToTurretsPerTeamTotalTowerDamageDone = 0
            totalTimeCCingOthersPerTotalDamageTaken = 0
            totalDamageTakenPerTeamTotalDamageTaken = 0
            damageSelfMitigatedPerTotalDamageTaken = 0
            visionScorePerDeath = 0
            totalJungleObjectivePerGameDuration = 0
            getObjectiveDifferPerGameDuration = 0
            damagePerMinute = 0
            dealPerGold = 0
            teamDamagePercentage = 0

            for match_indicator in match_indicators:
                if match_indicator["metadata"]["laneInfo"].get("teamPosition") != my_lane:
                    continue
                
                count += 1

                csDiffer += match_indicator["laneIndicator"]["basicWeight"]["csDiffer"]
                expDiffer += match_indicator["laneIndicator"]["basicWeight"]["expDiffer"]
                turretPlateDestroyDiffer += match_indicator["laneIndicator"]["basicWeight"]["turretPlateDestroyDiffer"]
                dealDiffer += match_indicator["laneIndicator"]["aggresiveLaneAbility"]["dealDiffer"]

                turretKillsPerDeaths += match_indicator["macroIndicator"]["splitPoint"]["turretKillsPerDeaths"]
                damageDealtToTurretsPerTeamTotalTowerDamageDone += match_indicator["macroIndicator"]["splitPoint"]["damageDealtToTurretsPerTeamTotalTowerDamageDone"]

                totalTimeCCingOthersPerTotalDamageTaken += match_indicator["macroIndicator"]["initiatingPoint"]["totalTimeCCingOthersPerTotalDamageTaken"]
                totalDamageTakenPerTeamTotalDamageTaken += match_indicator["macroIndicator"]["initiatingPoint"]["totalDamageTakenPerTeamTotalDamageTaken"]
                damageSelfMitigatedPerTotalDamageTaken += match_indicator["macroIndicator"]["initiatingPoint"]["damageSelfMitigatedPerTotalDamageTaken"]

                visionScorePerDeath += match_indicator["macroIndicator"]["visionPoint"]["visionScorePerDeath"]
                totalJungleObjectivePerGameDuration += match_indicator["macroIndicator"]["jungleHoldPoint"]["totalJungleObjectivePerGameDuration"]

                getObjectiveDifferPerGameDuration += match_indicator["macroIndicator"]["objectivePoint"]["getObjectiveDifferPerGameDuration"]

                damagePerMinute += match_indicator["macroIndicator"]["totalDealPoint"]["damagePerMinute"]
                dealPerGold += match_indicator["macroIndicator"]["totalDealPoint"]["dealPerGold"]
                teamDamagePercentage += match_indicator["macroIndicator"]["totalDealPoint"]["teamDamagePercentage"]

            my_vector = [
                csDiffer / count,
                expDiffer / count,
                turretPlateDestroyDiffer / count,
                dealDiffer / count,
                turretKillsPerDeaths / count,
                damageDealtToTurretsPerTeamTotalTowerDamageDone / count,
                totalTimeCCingOthersPerTotalDamageTaken / count,
                totalDamageTakenPerTeamTotalDamageTaken / count,
                damageSelfMitigatedPerTotalDamageTaken / count,
                visionScorePerDeath / count,
                totalJungleObjectivePerGameDuration / count,
                getObjectiveDifferPerGameDuration / count,
                damagePerMinute / count,
                dealPerGold / count,
                teamDamagePercentage / count
            ]

    distance_list, index_list = index.search(np.array([my_vector]).astype('float32'), 10)

    user_ids = []

    for index in index_list[0]:
        user_ids.append(user_list[index]["userId"])

    return user_ids
