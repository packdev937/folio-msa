package kr.folio.feed.application.service;

import kr.folio.feed.application.handler.FeedApplicationHandler;
import kr.folio.feed.application.mapper.FeedDataMapper;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.application.ports.output.FeedMessagePublisher;
import kr.folio.feed.domain.core.event.CreatedFeedEvent;
import kr.folio.feed.infrastructure.annotation.ApplicationService;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedResponse;
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
}