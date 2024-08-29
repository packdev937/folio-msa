package kr.folio.feed.application.handler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import kr.folio.feed.application.mapper.FeedDataMapper;
import kr.folio.feed.application.ports.output.FeedRepository;
import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.domain.core.event.CreatedFeedEvent;
import kr.folio.feed.domain.service.FeedDomainUseCase;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.response.RetrieveFeedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeedApplicationHandler {

    private final FeedDomainUseCase feedDomainUseCase;
    private final FeedRepository feedRepository;
    private final FeedDataMapper feedDataMapper;
    private final String UTC = "UTC";

    public CreatedFeedEvent createFeed(CreateFeedRequest createPhotoRequest) {
        Feed feed = feedDataMapper.toDomain(createPhotoRequest);
        Feed savedFeed = feedRepository.save(feed);

        if (savedFeed == null) {
            log.error("Could not save feed with id: {}", createPhotoRequest.userId());
            throw new IllegalArgumentException();
        }
        return new CreatedFeedEvent(feed, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    public RetrieveFeedResponse retrieveFeed(String requestUserId, Long feedId) {
        Feed feed = feedRepository.findFeedById(feedId)
            .orElseThrow(IllegalArgumentException::new); // todo : FeedNotFoundException

        if (feed == null) {
            log.error("Could not find feed with id: {}", feedId);
            throw new IllegalArgumentException();
        }

        // todo : 작성자인지 아닌지에 따라 다른 비즈니스 로직이 포함될 수 있음
//        if (feed.getUserId() != requestUserId) {
//
//        }

        return feedDataMapper.toRetrieveFeedResponse(feed);
    }
}
