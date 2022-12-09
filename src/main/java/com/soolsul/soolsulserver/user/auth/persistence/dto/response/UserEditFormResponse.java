package com.soolsul.soolsulserver.user.auth.persistence.dto.response;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UserEditFormResponse(
        @NotBlank String imageUrl,
        @NotBlank String nickName,
        @Email @NotBlank String email
) {
}
