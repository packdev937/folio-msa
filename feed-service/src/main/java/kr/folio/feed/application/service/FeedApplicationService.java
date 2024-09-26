package kr.folio.feed.application.service;

import java.util.List;
import kr.folio.feed.application.handler.FeedApplicationHandler;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.infrastructure.annotation.ApplicationService;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedImageUrlRequest;
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

    private final FeedApplicationHandler feedApplicationHandler;

    @Override
    public CreateFeedResponse createFeed(CreateFeedRequest createFeedRequest) {

        return feedApplicationHandler.createFeed(createFeedRequest);
    }

    @Override
    public RetrieveFeedDetailResponse retrieveFeedDetail(
        String requestUserId,
        Long feedId) {

        return feedApplicationHandler.retrieveFeedDetail(requestUserId, feedId);
    }

    @Override
    public DeleteFeedResponse deleteFeed(Long photoId) {

        return feedApplicationHandler.deleteFeed(photoId);
    }

    @Override
    public DeleteFeedResponse deleteAllFeedByPhotoId(Long photoId) {

        return feedApplicationHandler.deleteAllFeedByPhotoId(photoId);
    }

    @Override
    public List<Long> retrieveFeedIdsByUserId(String userId) {
        return feedApplicationHandler.retrieveFeedIdsByUserId(userId);
    }

    @Override
    public FeedsResponse retrieveFeeds(
        String requestUserId,
        String targetUserId) {

        return feedApplicationHandler.retrieveFeeds(requestUserId, targetUserId);
    }

    @Override
    public UpdateFeedResponse updateFeedAccessRange(
        String requestUserId,
        UpdateFeedAccessRangeRequest updateFeedAccessRangeRequest) {

        return feedApplicationHandler.updateFeedAccessRange(requestUserId, updateFeedAccessRangeRequest);
    }

    @Override
    public UpdateFeedResponse updateFeedImageUrl(
        String requestUserId,
        UpdateFeedImageUrlRequest updateFeedImageUriRequest) {

        return feedApplicationHandler.updateFeedImageUrl(requestUserId, updateFeedImageUriRequest);
    }
}