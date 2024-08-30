package kr.folio.user.presentation.dto.response.user;

import kr.folio.user.presentation.dto.response.feed.FeedsResponse;
import lombok.Builder;

@Builder
public record UserHomeResponse(
    UserProfileResponse userProfile,
    FeedsResponse feeds
) {

}
