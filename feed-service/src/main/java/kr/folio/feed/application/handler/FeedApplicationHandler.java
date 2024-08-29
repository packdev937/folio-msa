package kr.folio.feed.application.handler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import kr.folio.feed.application.mapper.FeedDataMapper;
import kr.folio.feed.application.ports.output.FeedRepository;
import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.domain.core.event.CreatedFeedEvent;
import kr.folio.feed.domain.service.FeedDomainUseCase;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.response.DeletePhotoResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedResponse;
import kr.folio.feed.presentation.dto.response.UpdateFeedResponse;
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

    public DeletePhotoResponse deleteFeed(Long feedId) {
        Long photoId = feedRepository.findPhotoIdByFeedId(feedId);
        feedRepository.deleteFeedById(feedId);

        int feedCount = feedRepository.countFeedByPhotoId(photoId);
        if (feedCount == 0) {
            // PhotoService 에게 Photo 삭제 요청
        }

        return new DeletePhotoResponse(feedId, "피드가 성공적으로 제거되었습니다.");
    }

    public FeedsResponse retrieveFeeds(String requestUserId) {
        return feedDataMapper.toFeedsResponse(
            feedRepository.findFeedsByUserId(requestUserId));
    }

    public FeedsResponse retrieveUserFeeds(String requestUserId, String userId) {
        // 전체 공개인 것만 반환
        // Follow-Service 에게 요청해서 팔로우 여부 확인
        // todo : requestUserId도 받아서 follow 여부에 따라 isFriend 까지 허용
        List<Feed> feeds = feedRepository.findFeedsByUserId(userId)
            .stream().filter(feed -> feed.getAccessRange().isPublic())
            .collect(Collectors.toList());

        return feedDataMapper.toFeedsResponse(feeds);
    }

    public UpdateFeedResponse updateFeedAccessRange(String requestUserId,
        UpdateFeedAccessRangeRequest request) {
        Feed feed = feedRepository.findFeedById(request.feedId())
            .orElseThrow(IllegalArgumentException::new);
        // todo : FeedNotFoundException

        if (feed.getUserId().equals(requestUserId)) {
            feed.updateAccessRange(request.updatedAccessRange());
            feedRepository.save(feed);
        } else {
            log.error("User {} is not authorized to update feed with id: {}", requestUserId,
	request.feedId());
            throw new IllegalArgumentException();
        }
        return new UpdateFeedResponse(request.feedId(), "피드가 성공적으로 업데이트되었습니다.");
    }
}
