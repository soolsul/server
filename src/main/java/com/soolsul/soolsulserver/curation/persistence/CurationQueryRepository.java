package com.soolsul.soolsulserver.curation.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationListLookupResponse;
import com.soolsul.soolsulserver.curation.common.dto.response.CurationLookupResponse;
import com.soolsul.soolsulserver.location.common.dto.response.LocationSquareRangeCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.soolsul.soolsulserver.bar.domain.QBar.bar;
import static com.soolsul.soolsulserver.bar.domain.QBarAlcoholTag.barAlcoholTag;
import static com.soolsul.soolsulserver.bar.domain.QBarMoodTag.barMoodTag;
import static com.soolsul.soolsulserver.curation.domain.QCuration.curation;

@Repository
@RequiredArgsConstructor
public class CurationQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    // 무드, 술집 태그 중복한 큐레이션 결과 반환
    public List<CurationListLookupResponse> findAllCurationsInLocationRange(
            LocationSquareRangeCondition locationSquareRangeCondition
    ) {
        double southWestLatitude = locationSquareRangeCondition.southWestLatitude();
        double southWestLongitude = locationSquareRangeCondition.southWestLongitude();
        double northEastLatitude = locationSquareRangeCondition.northEastLatitude();
        double northEastLongitude = locationSquareRangeCondition.northEastLongitude();

        return jpaQueryFactory.select(Projections.constructor(
                        CurationListLookupResponse.class,
                        curation.id,
                        curation.mainPictureUrl,
                        curation.title,
                        curation.content,
                        barMoodTag.name,
                        barAlcoholTag.alcoholCategoryName))
                .from(bar)
                .innerJoin(curation).on(bar.id.eq(curation.barId))
                .innerJoin(barMoodTag).on(bar.id.eq(barMoodTag.barId))
                .innerJoin(barAlcoholTag).on(bar.id.eq(barAlcoholTag.barId))
                .where(
                        bar.location.latitude.between(southWestLatitude, northEastLatitude),
                        bar.location.longitude.between(southWestLongitude, northEastLongitude)
                )
                .fetch();
    }

    public CurationLookupResponse findById(String curationId) {
        return jpaQueryFactory.select(Projections.constructor(CurationLookupResponse.class,
                        curation.id,
                        curation.mainPictureUrl,
                        curation.title,
                        curation.content,
                        curation.barId))
                .from(curation)
                .where(curation.id.eq(curationId))
                .fetchOne();
    }
}
