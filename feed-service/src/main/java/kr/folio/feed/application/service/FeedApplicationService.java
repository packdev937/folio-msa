package kr.folio.feed.application.service;

import kr.folio.feed.application.handler.FeedApplicationHandler;
import kr.folio.feed.application.mapper.FeedDataMapper;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.application.ports.output.FeedMessagePublisher;
import kr.folio.feed.domain.core.event.CreatedFeedEvent;
import kr.folio.feed.infrastructure.annotation.ApplicationService;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.DeletePhotoResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedResponse;
import kr.folio.feed.presentation.dto.response.UpdateFeedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
public class FeedApplicationService implements FeedApplicationUseCase {

    private final FeedApplicationHandler photoApplicationHandler;
    private final FeedDataMapper photoDataMapper;

    @Qualifier("createdFeedMessagePublisher")
    private final FeedMessagePublisher createdFeedMessagePublisher;

    @Override
    public CreateFeedResponse createFeed(CreateFeedRequest createPhotoRequest) {
        CreatedFeedEvent createdFeedEvent = photoApplicationHandler.createFeed(createPhotoRequest);
        createdFeedMessagePublisher.publish(createdFeedEvent);
        return new CreateFeedResponse(createdFeedEvent.feed().getId(), "Feed saved successfully!");
    }

    @Override
    public RetrieveFeedResponse retrieveFeed(String requestUserId, Long feedId) {
        // RetrievedFeedEvent (조회수 증가 등)
        return photoApplicationHandler.retrieveFeed(requestUserId, feedId);
    }

    @Override
    public DeletePhotoResponse deleteFeed(Long photoId) {
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