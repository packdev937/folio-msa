package kr.folio.feed.application.ports.input;

import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.DeletePhotoResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedResponse;
import kr.folio.feed.presentation.dto.response.UpdateFeedResponse;
import org.springframework.http.ResponseEntity;

public interface FeedApplicationUseCase {

    CreateFeedResponse createFeed(CreateFeedRequest createPhotoRequest);

    RetrieveFeedResponse retrieveFeed(String requestUserId, Long feedId);

    DeletePhotoResponse deleteFeed(Long photoId);

    FeedsResponse retrieveFeeds(String requestUserId);

    FeedsResponse retrieveUserFeeds(
        String requestUserId, String userId);

    UpdateFeedResponse updateFeedAccessRange(String requestUserId, UpdateFeedAccessRangeRequest request);
}
