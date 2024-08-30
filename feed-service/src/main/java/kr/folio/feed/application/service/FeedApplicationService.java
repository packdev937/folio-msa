package kr.folio.feed.application.service;

import kr.folio.feed.application.handler.FeedApplicationHandler;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.infrastructure.annotation.ApplicationService;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.DeleteFeedResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedDetailResponse;
import kr.folio.feed.presentation.dto.response.UpdateFeedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class FeedApplicationService implements FeedApplicationUseCase {

    private final FeedApplicationHandler photoApplicationHandler;

    @Override
    public CreateFeedResponse createFeed(CreateFeedRequest createFeedRequest) {
        return photoApplicationHandler.createFeed(createFeedRequest);
    }

    @Override
    public RetrieveFeedDetailResponse retrieveFeedDetail(String requestUserId, Long feedId) {
        return photoApplicationHandler.retrieveFeedDetail(requestUserId, feedId);
    }

    @Override
    public DeleteFeedResponse deleteFeed(Long photoId) {
        return photoApplicationHandler.deleteFeed(photoId);
    }

    @Override
    public FeedsResponse retrieveFeeds(String requestUserId) {
        return photoApplicationHandler.retrieveFeeds(requestUserId);
    }

    @Override
    public FeedsResponse retrieveUserFeeds(
        String requestUserId, String userId) {
        return photoApplicationHandler.retrieveUserFeeds(requestUserId, userId);
    }

    @Override
    public UpdateFeedResponse updateFeedAccessRange(String requestUserId,
        UpdateFeedAccessRangeRequest request) {
        return photoApplicationHandler.updateFeedAccessRange(requestUserId, request);
    }
}