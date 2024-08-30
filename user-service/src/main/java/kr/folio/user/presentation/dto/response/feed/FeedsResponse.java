package kr.folio.user.presentation.dto.response.feed;

import java.util.List;
import kr.folio.user.presentation.dto.response.feed.RetrieveFeedResponse;

public record FeedsResponse(
    List<RetrieveFeedResponse> feeds
) {

}
