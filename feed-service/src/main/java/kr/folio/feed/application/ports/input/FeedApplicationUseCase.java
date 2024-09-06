package kr.folio.feed.application.ports.input;

import java.util.List;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedImageUrlRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.DeleteFeedResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedDetailResponse;
import kr.folio.feed.presentation.dto.response.UpdateFeedResponse;

public interface FeedApplicationUseCase {

    CreateFeedResponse createFeed(CreateFeedRequest createFeedRequest);

    RetrieveFeedDetailResponse retrieveFeedDetail(
        String requestUserId,
        Long feedId
    );

    DeleteFeedResponse deleteFeed(Long feedId);

    List<Long> retrieveFeedIdsByUserId(String userId);

    FeedsResponse retrieveFeeds(
        String requestUserId,
        String targetUserId
    );

    UpdateFeedResponse updateFeedAccessRange(
        String requestUserId,
        UpdateFeedAccessRangeRequest request
    );

    UpdateFeedResponse updateFeedImageUrl(
        String requestUserId,
        UpdateFeedImageUrlRequest updateFeedImageUriRequest
    );
}
