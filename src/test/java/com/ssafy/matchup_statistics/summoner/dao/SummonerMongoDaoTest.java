package com.ssafy.matchup_statistics.summoner.dao;

import com.ssafy.matchup_statistics.account.entity.Account;
import com.ssafy.matchup_statistics.global.config.TestMongoAutoConfiguration;
import com.ssafy.matchup_statistics.indicator.entity.Indicator;
import com.ssafy.matchup_statistics.summoner.entity.Summoner;
import com.ssafy.matchup_statistics.summoner.entity.SummonerDetail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class SummonerMongoDaoTest {

    @Autowired
    MongoTemplate mongoTemplate;

    Summoner testSummoner;

    Account testAccount;

    String testId = "대충테스트아이디";
    String gameName = "아진잉";
    String tagLine = "KR1";
    String tagLineLowerCase = "kr1";

    @BeforeEach
    void setup() {
        testAccount = Account.builder()
                .gameName(gameName)
                .tagLine(tagLine)
                .build();
        testSummoner = Summoner.builder()
                .id(testId)
                .account(testAccount)
                .build();

        mongoTemplate.save(testSummoner);
    }

    @Test
    @DisplayName("ID로 회원정보 조회")
    void getSummonerByIdTest() {
        // given
        String findId = testId;

        // when
        Query query = Query.query(
                Criteria.where("_id").is(findId));

        Summoner summoner = mongoTemplate.findOne(query, Summoner.class, "summoners");

        // then
        assertThat(summoner).isNotNull();
        assertThat(summoner.getId()).isEqualTo(testSummoner.getId());
    }

    @Test
    @DisplayName("Name + Tag로 회원정보 조회")
    void getSummonerByNameAngTag() {
        // given
        String findId = testId;

        // when
        Query query = Query.query(
                Criteria.where("account.gameName").is(gameName)
                        .and("account.tagLine").is(tagLine));

        Summoner summoner = mongoTemplate.findOne(query, Summoner.class, "summoners");

        // then
        assertThat(summoner).isNotNull();
        assertThat(summoner.getId()).isEqualTo(testSummoner.getId());
    }

    @Test
    @DisplayName("Tag lower case로 조회되는지 확인")
    void getSummonerByNameAngTagLowerCase() {
        // given
        String findId = testId;

        // when
        Query query = Query.query(
                Criteria.where("account.gameName").is(gameName)
                        .and("account.tagLine").regex(Pattern.compile(tagLineLowerCase, Pattern.CASE_INSENSITIVE)));


        Summoner summoner = mongoTemplate.findOne(query, Summoner.class, "summoners");

        // then
        assertThat(summoner).isNotNull();
        assertThat(summoner.getId()).isEqualTo(testSummoner.getId());
    }
}