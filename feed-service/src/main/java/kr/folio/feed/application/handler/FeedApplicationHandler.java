package kr.folio.feed.application.handler;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kr.folio.feed.application.mapper.FeedDataMapper;
import kr.folio.feed.application.ports.output.FeedRepository;
import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.domain.service.FeedDomainUseCase;
import kr.folio.feed.infrastructure.client.PhotoServiceClient;
import kr.folio.feed.infrastructure.exception.FeedNotFoundException;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
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
    private final PhotoServiceClient photoServiceClient;
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

    // todo : Retrieve 로직 다시 구현
    public RetrieveFeedDetailResponse retrieveFeedDetail(String requestUserId, Long feedId) {
        Optional<Feed> feed = feedRepository.findByFeedIdAndRequestUserId(feedId, requestUserId);

        if (feed.isEmpty()) {
            return retrieveUserFeedDetail(feedId);
        }

        return feedDataMapper.toRetrieveFeedDetailResponse(feed.get());
    }

    public RetrieveFeedDetailResponse retrieveUserFeedDetail(Long feedId) {
        Feed feed = feedRepository.findFeedById(feedId)
            .orElseThrow(FeedNotFoundException::new);

        return feedDataMapper.toRetrieveNonAuthorizedFeedDetailResponse(feed);
    }

    public DeleteFeedResponse deleteFeed(Long feedId) {
        Long photoId = feedRepository.findPhotoIdByFeedId(feedId);
        feedRepository.deleteFeedById(feedId);

        int feedCount = feedRepository.countFeedByPhotoId(photoId);
        if (feedDomainUseCase.isPhotoDeletable(feedCount)) {
            try {
	photoServiceClient.deletePhoto(photoId);
            } catch (FeignException feignException) {
	log.error("Failed to delete photo with id: {}", photoId);
            }
        }

        return new DeleteFeedResponse(feedId, "피드가 성공적으로 제거되었습니다.");
    }

    public FeedsResponse retrieveFeeds(String requestUserId) {
        log.info("Retrieving feeds for user with id: {}", requestUserId);

        return feedDataMapper.toFeedsResponse(
            feedRepository.findFeedsByUserId(requestUserId)
        );
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
        Feed feed = feedRepository.findByFeedIdAndRequestUserId(request.feedId(), requestUserId)
            .orElseThrow(FeedNotFoundException::new);

        feed.updateAccessRange(request.updatedAccessRange());
        feedRepository.save(feed);

        return new UpdateFeedResponse(request.feedId(), "피드가 성공적으로 업데이트되었습니다.");
    }
}
