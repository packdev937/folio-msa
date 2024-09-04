package kr.folio.user.presentation.dto.response.user;

import kr.folio.user.presentation.dto.response.feed.FeedsResponse;
import lombok.Builder;

@Builder
public record UserHomeResponse(
    UserProfileResponse userProfile,
    // todo : 추후 변경 가능
    FeedsResponse feeds
) {

}
