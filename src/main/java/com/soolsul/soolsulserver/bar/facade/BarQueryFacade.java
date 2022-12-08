package com.soolsul.soolsulserver.bar.facade;

import com.soolsul.soolsulserver.bar.businees.BarAlcoholTagService;
import com.soolsul.soolsulserver.bar.businees.BarMoodTagService;
import com.soolsul.soolsulserver.bar.businees.BarQueryService;
import com.soolsul.soolsulserver.bar.common.dto.request.BarLookupServiceConditionRequest;
import com.soolsul.soolsulserver.bar.common.dto.request.BarLookupConditionRequest;
import com.soolsul.soolsulserver.bar.common.dto.response.FilteredBarsLookupResponse;
import com.soolsul.soolsulserver.location.common.dto.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.location.common.dto.response.LocationSquareRangeCondition;
import com.soolsul.soolsulserver.location.business.LocationRangeService;
import com.soolsul.soolsulserver.menu.alcohol.service.AlcoholCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
public class BarQueryFacade {

    private static final String TAG_NAME_DELIMITER = ",";

    private final BarQueryService barQueryService;
    private final BarAlcoholTagService barAlcoholTagService;
    private final BarMoodTagService barMoodTarService;
    private final AlcoholCategoryService alcoholCategoryService;
    private final LocationRangeService locationRangeService;

    public FilteredBarsLookupResponse findBarFilteredByConditions(BarLookupConditionRequest barLookupConditionRequest) {
        LocationSquareRangeRequest locationSquareRangeRequest = new LocationSquareRangeRequest(
                barLookupConditionRequest.latitude(),
                barLookupConditionRequest.longitude(),
                barLookupConditionRequest.level()
        );

        LocationSquareRangeCondition locationSquareRangeCondition = locationRangeService.calculateLocationSquareRange(
                locationSquareRangeRequest
        );

        BarLookupServiceConditionRequest barLookupServiceConditionRequest = new BarLookupServiceConditionRequest(
                locationSquareRangeCondition.southWestLatitude(),
                locationSquareRangeCondition.southWestLongitude(),
                locationSquareRangeCondition.northEastLatitude(),
                locationSquareRangeCondition.northEastLongitude(),
                getBarMoodTagIds(barLookupConditionRequest.barMoodTagNames()),
                getBarAlcoholTagIds(barLookupConditionRequest.barAlcoholTagNames())
        );

        return barQueryService.findBarFilteredByConditions(barLookupServiceConditionRequest);
    }

    private List<String> getBarMoodTagIds(String barMoodTagNames) {
        List<String> parsedMoodTagNames = parseTagNames(barMoodTagNames);
        return barMoodTarService.findBarAlcoholTagIdsByAlcoholNames(parsedMoodTagNames);
    }

    private List<String> getBarAlcoholTagIds(@NotEmpty String alcoholTagNames) {
        List<String> alcoholCategoryNames = parseTagNames(alcoholTagNames);
        List<String> alcoholCategoryIds = alcoholCategoryService.findAlcoholCategoryIdsByAlcoholCategoryNames(
                alcoholCategoryNames
        );
        return barAlcoholTagService.findBarAlcoholTagIdsByAlcoholCategoryIds(alcoholCategoryIds);
    }

    private List<String> parseTagNames(String tagNames) {
        if(tagNames == null || tagNames.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(tagNames.split(TAG_NAME_DELIMITER)).toList();
    }

}
