package kr.folio.feed.presentation.dto.response;

import java.util.List;

public record FeedsResponse(
    List<FeedResponse> feeds
) {

}
