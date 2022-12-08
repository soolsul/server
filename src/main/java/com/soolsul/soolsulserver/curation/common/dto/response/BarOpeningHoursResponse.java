package com.soolsul.soolsulserver.curation.common.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;

public record BarOpeningHoursResponse(
        @JsonFormat(pattern = "a hh:mm") LocalTime openTime,
        @JsonFormat(pattern = "a hh:mm") LocalTime closeTime
) {
}
