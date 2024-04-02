package com.ssafy.matchup_statistics.global.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRiotAccount is a Querydsl query type for RiotAccount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRiotAccount extends EntityPathBase<RiotAccount> {

    private static final long serialVersionUID = -1505320436L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRiotAccount riotAccount = new QRiotAccount("riotAccount");

    public final StringPath id = createString("id");

    public final NumberPath<Integer> leaguePoint = createNumber("leaguePoint", Integer.class);

    public final StringPath leagueRank = createString("leagueRank");

    public final NumberPath<Long> revisionDate = createNumber("revisionDate", Long.class);

    public final QSummonerProfile summonerProfile;

    public final StringPath tier = createString("tier");

    public final QUser user;

    public QRiotAccount(String variable) {
        this(RiotAccount.class, forVariable(variable), INITS);
    }

    public QRiotAccount(Path<? extends RiotAccount> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRiotAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRiotAccount(PathMetadata metadata, PathInits inits) {
        this(RiotAccount.class, metadata, inits);
    }

    public QRiotAccount(Class<? extends RiotAccount> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.summonerProfile = inits.isInitialized("summonerProfile") ? new QSummonerProfile(forProperty("summonerProfile")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

