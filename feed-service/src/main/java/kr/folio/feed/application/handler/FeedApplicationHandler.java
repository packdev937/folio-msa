package kr.folio.feed.application.handler;

import feign.FeignException;
import java.util.List;
import java.util.stream.Collectors;
import kr.folio.feed.application.mapper.FeedDataMapper;
import kr.folio.feed.application.ports.output.FeedRepository;
import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.domain.core.vo.AccessRange;
import kr.folio.feed.domain.core.vo.FollowStatus;
import kr.folio.feed.domain.service.FeedDomainUseCase;
import kr.folio.feed.infrastructure.client.FollowServiceClient;
import kr.folio.feed.infrastructure.exception.FeedNotFoundException;
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
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeedApplicationHandler {

    private final FeedDomainUseCase feedDomainUseCase;
    private final FeedRepository feedRepository;
    private final FeedDataMapper feedDataMapper;
    private final FollowServiceClient followServiceClient;
    private final String UTC = "UTC";

    public CreateFeedResponse createFeed(CreateFeedRequest createPhotoRequest) {

        Feed feed = feedDataMapper.toDomain(createPhotoRequest);
        Feed savedFeed = feedRepository.save(feed);

        if (savedFeed == null) {
            log.error("Could not save feed with id: {}", createPhotoRequest.userId());
            throw new IllegalArgumentException();
        }

        return feedDataMapper.toCreateResponse(savedFeed);
    }

    public RetrieveFeedDetailResponse retrieveFeedDetail(
        String requestUserId,
        Long feedId) {

        String targetUserId = feedRepository.findUserIdByFeedId(feedId)
            .orElseThrow(() -> new IllegalStateException("FeedId에 해당하는 userId가 없습니다."));

        FollowStatus followStatus = followServiceClient.retrieveFollowStatus(
            requestUserId,
            targetUserId);

        Feed feed = feedRepository.findFeedById(feedId)
            .orElseThrow(FeedNotFoundException::new);

        if (followStatus.isAuthorized()) {
            return feedDataMapper.toRetrieveNonAuthorizedFeedDetailResponse(feed);
        }

        return feedDataMapper.toRetrieveFeedDetailResponse(feed);
    }

    public FeedsResponse retrieveFeeds(
        String requestUserId,
        String targetUserId) {

        FollowStatus followStatus = followServiceClient.retrieveFollowStatus(
            requestUserId,
            targetUserId);

        List<Feed> feeds = feedRepository.findFeedsByUserId(targetUserId);

        switch (followStatus) {
            case FOLLOW:
	feeds = filterFeedsByAccessRange(feeds, AccessRange.FRIEND);
	break;
            case UNFOLLOW:
	feeds = filterFeedsByAccessRange(feeds, AccessRange.PUBLIC);
	break;
            default:
	feeds = filterFeedsByAccessRange(feeds, AccessRange.PRIVATE);
        }

        return feedDataMapper.toFeedsResponse(feeds);
    }

    private List<Feed> filterFeedsByAccessRange(
        List<Feed> feeds,
        AccessRange accessRange) {

        return feeds.stream()
            .filter(feed -> feed.getAccessRange().isAccessible(accessRange))
            .collect(Collectors.toList());
    }

    public UpdateFeedResponse updateFeedAccessRange(
        String requestUserId,
        UpdateFeedAccessRangeRequest updateFeedAccessRangeRequest) {

        Feed feed = feedRepository.findByFeedIdAndRequestUserId(
            updateFeedAccessRangeRequest.feedId(),
            requestUserId
        ).orElseThrow(FeedNotFoundException::new);

        feedDomainUseCase.updateAccessRange(feed,
            updateFeedAccessRangeRequest.updatedAccessRange());
        feedRepository.save(feed);

        return new UpdateFeedResponse(
            updateFeedAccessRangeRequest.feedId(),
            "피드가 범위가 성공적으로 업데이트되었습니다."
        );
    }

    public UpdateFeedResponse updateFeedImageUrl(
        String requestUserId,
        UpdateFeedImageUrlRequest updateFeedImageUrlRequest) {

        Feed feed = feedRepository.findByFeedIdAndRequestUserId(
            updateFeedImageUrlRequest.feedId(),
            requestUserId
        ).orElseThrow(FeedNotFoundException::new);

        feedDomainUseCase.updateImageUrl(feed, updateFeedImageUrlRequest.updatedImageUri());
        feedRepository.save(feed);

        return new UpdateFeedResponse(
            updateFeedImageUrlRequest.feedId(),
            "피드 이미지가 성공적으로 업데이트되었습니다."
        );
    }

    public DeleteFeedResponse deleteFeed(Long feedId) {

        Long photoId = feedRepository.findPhotoIdByFeedId(feedId);
        feedRepository.deleteFeedById(feedId);

        int feedCount = feedRepository.countFeedByPhotoId(photoId);
        if (feedDomainUseCase.isPhotoDeletable(feedCount)) {
            try {
	// Delete는 카프카 이벤트를 사용하자
            } catch (FeignException feignException) {
	log.error("Failed to delete photo with id: {}", photoId);
            }
        }

        return new DeleteFeedResponse(feedId, "피드가 성공적으로 제거되었습니다.");
    }
}
