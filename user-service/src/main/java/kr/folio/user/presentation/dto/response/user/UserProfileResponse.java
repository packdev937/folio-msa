package kr.folio.user.presentation.dto.response.user;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.folio.user.domain.core.entity.User;

@Schema(description = "유저 프로필 정보")
public record UserProfileResponse(
    String id,
    String nickname,
    String profileImageUrl
) {

    public static UserProfileResponse of(User user) {
        return new UserProfileResponse(user.getFolioId(), user.getNickname(), user.getProfileImageUrl()
        );
    }
}