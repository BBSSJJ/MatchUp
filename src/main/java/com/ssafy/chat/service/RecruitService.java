package com.ssafy.chat.service;

import com.ssafy.chat.dto.RecruitDto;
import com.ssafy.chat.entity.Recruit;
import com.ssafy.chat.mapper.RecruitMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecruitService {

    private final MongoTemplate mongoTemplate;
    private final KafkaTemplate<String, RecruitDto> kafkaTemplate;

    public List<RecruitDto> findRecruits() {
        return RecruitMapper.instance.convertListRecruitDto(mongoTemplate.findAll(Recruit.class));
    }

    public void insertRecruit(RecruitDto recruitDto) {

        Recruit recruit = new Recruit();
        recruit.setValues(recruitDto);

        recruitDto.setObjectId(mongoTemplate.save(recruit).getObjectId().toString());

        kafkaTemplate.send("recruit", recruitDto);
    }

    public void deleteRecruit(RecruitDto recruitDto) {

        ObjectId objectId = new ObjectId(recruitDto.getObjectId());

        mongoTemplate.findAndRemove(Query.query(Criteria.where("_id").is(objectId)), Recruit.class);

        kafkaTemplate.send("recruit", recruitDto);
    }

    public void updateRecruit(RecruitDto recruitDto) {

        ObjectId objectId = new ObjectId(recruitDto.getObjectId());

        Recruit recruit = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(objectId)), Recruit.class);
        if (recruit != null) {
            recruit.setValues(recruitDto);

            mongoTemplate.save(recruit);

            kafkaTemplate.send("recruit", recruitDto);
        }
    }
}
