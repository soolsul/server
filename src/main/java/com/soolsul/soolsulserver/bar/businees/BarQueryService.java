package com.soolsul.soolsulserver.bar.businees;

import com.soolsul.soolsulserver.bar.businees.dto.FilteredBarLookupResponse;
import com.soolsul.soolsulserver.bar.businees.dto.BarLookupServiceConditionRequest;
import com.soolsul.soolsulserver.bar.exception.BarNotFoundException;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupResponse;
import com.soolsul.soolsulserver.bar.presentation.dto.FilteredBarsLookupResponse;
import com.soolsul.soolsulserver.bar.persistence.BarQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BarQueryService {

    private final BarQueryRepository barQueryRepository;

    public BarLookupResponse findById(String barId) {
        return barQueryRepository.findById(barId)
                .orElseThrow(BarNotFoundException::new);
    }

    public FilteredBarsLookupResponse findBarFilteredByConditions(
            @Valid BarLookupServiceConditionRequest barLookupServiceConditionRequest
    ) {
        List<FilteredBarLookupResponse> filteredBarLookupResponses
                = barQueryRepository.findBarFilteredByConditions(barLookupServiceConditionRequest);
        return new FilteredBarsLookupResponse(filteredBarLookupResponses);
    }

}