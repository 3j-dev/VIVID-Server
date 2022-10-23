package com.chicplay.mediaserver.domain.video_space.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVideoSpaceParticipant is a Querydsl query type for VideoSpaceParticipant
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVideoSpaceParticipant extends EntityPathBase<VideoSpaceParticipant> {

    private static final long serialVersionUID = -681639873L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVideoSpaceParticipant videoSpaceParticipant = new QVideoSpaceParticipant("videoSpaceParticipant");

    public final com.chicplay.mediaserver.global.common.QBaseTime _super = new com.chicplay.mediaserver.global.common.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo, com.chicplay.mediaserver.domain.individual_video.domain.QIndividualVideo> individualVideos = this.<com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo, com.chicplay.mediaserver.domain.individual_video.domain.QIndividualVideo>createList("individualVideos", com.chicplay.mediaserver.domain.individual_video.domain.IndividualVideo.class, com.chicplay.mediaserver.domain.individual_video.domain.QIndividualVideo.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final com.chicplay.mediaserver.domain.user.domain.QUser user;

    public final QVideoSpace videoSpace;

    public QVideoSpaceParticipant(String variable) {
        this(VideoSpaceParticipant.class, forVariable(variable), INITS);
    }

    public QVideoSpaceParticipant(Path<? extends VideoSpaceParticipant> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVideoSpaceParticipant(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVideoSpaceParticipant(PathMetadata metadata, PathInits inits) {
        this(VideoSpaceParticipant.class, metadata, inits);
    }

    public QVideoSpaceParticipant(Class<? extends VideoSpaceParticipant> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.chicplay.mediaserver.domain.user.domain.QUser(forProperty("user"), inits.get("user")) : null;
        this.videoSpace = inits.isInitialized("videoSpace") ? new QVideoSpace(forProperty("videoSpace")) : null;
    }

}

