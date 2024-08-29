package kr.folio.feed.application.ports.input;

import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedResponse;

public interface FeedApplicationUseCase {

    CreateFeedResponse createFeed(CreateFeedRequest createPhotoRequest);

    RetrieveFeedResponse retrieveFeed(String requestUserId, Long feedId);
}
