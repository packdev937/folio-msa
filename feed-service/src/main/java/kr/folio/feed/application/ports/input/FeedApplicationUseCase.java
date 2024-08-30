package kr.folio.feed.application.ports.input;

import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.DeleteFeedResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedDetailResponse;
import kr.folio.feed.presentation.dto.response.UpdateFeedResponse;

public interface FeedApplicationUseCase {

    CreateFeedResponse createFeed(CreateFeedRequest createFeedRequest);

    RetrieveFeedDetailResponse retrieveFeedDetail(String requestUserId, Long feedId);

    DeleteFeedResponse deleteFeed(Long photoId);

    FeedsResponse retrieveFeeds(String requestUserId);

    FeedsResponse retrieveUserFeeds(
        String requestUserId, String userId);

    UpdateFeedResponse updateFeedAccessRange(String requestUserId,
        UpdateFeedAccessRangeRequest request);
}
