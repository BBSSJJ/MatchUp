package com.ssafy.chat.service;

import com.ssafy.chat.dto.RecruitDto;
import com.ssafy.chat.entity.Recruit;
import com.ssafy.chat.mapper.RecruitMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitService {

    private final MongoTemplate mongoTemplate;
    private final KafkaTemplate<String, RecruitDto> kafkaTemplate;

    public List<RecruitDto> findRecruits() {

        return RecruitMapper.instance.convertListRecruitDto(mongoTemplate.findAll(Recruit.class));
    }

    public void insertRecruit(RecruitDto recruitDto) {

        Recruit recruit = new Recruit();
        recruit.set(recruitDto);

        mongoTemplate.save(recruit);

        kafkaTemplate.send("recruit", recruitDto);
    }

    public void deleteRecruit(RecruitDto recruitDto) {

        ObjectId objectId = new ObjectId(recruitDto.getObjectId());

        mongoTemplate.remove(Query.query(Criteria.where("_id").is(objectId)), Recruit.class);

        kafkaTemplate.send("recruit", recruitDto);
    }

    public void updateRecruit(RecruitDto recruitDto) {

        ObjectId objectId = new ObjectId(recruitDto.getObjectId());

        Recruit recruit = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(objectId)), Recruit.class);
        if (recruit != null) {
            recruit.set(recruitDto);

            mongoTemplate.save(recruit);

            kafkaTemplate.send("recruit", recruitDto);
        }
    }
}
