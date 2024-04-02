package com.ssafy.matchup_statistics.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSummonerProfile is a Querydsl query type for SummonerProfile
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QSummonerProfile extends BeanPath<SummonerProfile> {

    private static final long serialVersionUID = -569329450L;

    public static final QSummonerProfile summonerProfile = new QSummonerProfile("summonerProfile");

    public final StringPath iconUrl = createString("iconUrl");

    public final NumberPath<Long> level = createNumber("level", Long.class);

    public final StringPath name = createString("name");

    public final StringPath tag = createString("tag");

    public QSummonerProfile(String variable) {
        super(SummonerProfile.class, forVariable(variable));
    }

    public QSummonerProfile(Path<? extends SummonerProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSummonerProfile(PathMetadata metadata) {
        super(SummonerProfile.class, metadata);
    }

}

