package com.ssafy.matchup_statistics.indicator.util;

import java.util.LinkedHashMap;

public class QueueTypeMap {
    public LinkedHashMap<Integer, String> queueTypeMap = new LinkedHashMap<>();

    public QueueTypeMap() {
        queueTypeMap.put(400, "일반");
        queueTypeMap.put(420, "솔로랭크");
        queueTypeMap.put(430, "일반");
        queueTypeMap.put(440, "자유랭크");
        queueTypeMap.put(450, "칼바람");
        queueTypeMap.put(700, "격전");
        queueTypeMap.put(800, "ai");
        queueTypeMap.put(810, "ai");
        queueTypeMap.put(820, "ai");
        queueTypeMap.put(830, "ai");
        queueTypeMap.put(840, "ai");
        queueTypeMap.put(850, "ai");
        queueTypeMap.put(900, "URF");
        queueTypeMap.put(920, "포로왕");
        queueTypeMap.put(1020, "단일모드");
        queueTypeMap.put(1300, "돌격! 넥서스");
        queueTypeMap.put(1400, "궁 주문서");
        queueTypeMap.put(2000, "튜토리얼");
        queueTypeMap.put(2010, "튜토리얼");
        queueTypeMap.put(2020, "튜토리얼");
    }

    public String getQueueType(Integer queueType) {
        return queueTypeMap.get(queueType);
    }
}
