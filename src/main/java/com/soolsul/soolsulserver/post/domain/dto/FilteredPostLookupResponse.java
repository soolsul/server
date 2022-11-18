package com.soolsul.soolsulserver.post.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.soolsul.soolsulserver.auth.UserInfo;
import com.soolsul.soolsulserver.post.domain.Post;

import javax.validation.constraints.NotNull;

public record FilteredPostLookupResponse(

        @NotNull
        Post post,

        @NotNull
        UserInfo userInfo
) {
    @QueryProjection
    public FilteredPostLookupResponse(@NotNull Post post,
                                      @NotNull UserInfo userInfo) {
        this.post = post;
        this.userInfo = userInfo;
    }
}