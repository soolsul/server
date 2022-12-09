package com.soolsul.soolsulserver.user.mypage.facade;

import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserEditFormResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.ScrapedPostListLookUpResponse;
import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.UserPostListLookUpResponse;
import com.soolsul.soolsulserver.user.mypage.common.dto.response.UserReplyListLookUpResponse;

public interface MyPageQueryFacadeSpec {
    UserLookUpResponse findUserWithDetailInfo(String userId);

    ScrapedPostListLookUpResponse findAllScrapedPost(String userId);

    UserPostListLookUpResponse findAllUserPost(String userId);

    UserReplyListLookUpResponse findAllUserReplies(String userId);

    UserEditFormResponse findUserEditForm(String userId);
}
