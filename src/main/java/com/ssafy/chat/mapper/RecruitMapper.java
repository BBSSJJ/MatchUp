package com.ssafy.chat.mapper;

import com.ssafy.chat.dto.RecruitDto;
import com.ssafy.chat.entity.Recruit;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RecruitMapper {

    RecruitMapper instance = Mappers.getMapper(RecruitMapper.class);

    @Mapping(source = "objectId", target = "objectId", qualifiedByName = "objectIdToString")
    RecruitDto convertRecruitDto(Recruit recruit);

    @Named("objectIdToString")
    default String objectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toString() : null;
    }

    List<RecruitDto> convertListRecruitDto(List<Recruit> recruits);

}
