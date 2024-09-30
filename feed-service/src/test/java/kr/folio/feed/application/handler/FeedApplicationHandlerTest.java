package kr.folio.feed.application.handler;

import static kr.folio.feed.common.FeedSteps.피드_생성;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.folio.feed.application.mapper.FeedDataMapper;
import kr.folio.feed.application.ports.output.FeedRepository;
import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.domain.core.vo.AccessRange;
import kr.folio.feed.domain.core.vo.FollowStatus;
import kr.folio.feed.domain.service.FeedDomainService;
import kr.folio.feed.domain.service.FeedDomainUseCase;
import kr.folio.feed.infrastructure.client.FollowServiceClient;
import kr.folio.feed.infrastructure.exception.FeedNotFoundException;
import kr.folio.feed.persistence.repository.FakeFeedRepository;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedAccessRangeRequest;
import kr.folio.feed.presentation.dto.request.UpdateFeedImageUrlRequest;
import kr.folio.feed.presentation.dto.response.CreateFeedResponse;
import kr.folio.feed.presentation.dto.response.DeleteFeedResponse;
import kr.folio.feed.presentation.dto.response.FeedsResponse;
import kr.folio.feed.presentation.dto.response.RetrieveFeedDetailResponse;
import kr.folio.feed.presentation.dto.response.UpdateFeedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FeedApplicationHandler 테스트")
class FeedApplicationHandlerTest {

    private FeedDomainUseCase feedDomainService;
    private FeedRepository feedRepository;
    private FeedDataMapper feedDataMapper;
    private FeedApplicationHandler feedApplicationHandler;

    @BeforeEach
    void setUp() {
        feedDomainService = new FeedDomainService();
        feedRepository = new FakeFeedRepository();
        feedDataMapper = new FeedDataMapper();
        feedApplicationHandler = new FeedApplicationHandler(feedDomainService, feedRepository,
            feedDataMapper, null, null, null);
    }

    @Test
    void 피드를_생성한다() {
        // Given
        CreateFeedRequest createFeedRequest = new CreateFeedRequest(
            "packdev937",
            1L,
            "photoImageUrl",
            List.of("tag1", "tag2")
        );

        // When
        CreateFeedResponse response = feedApplicationHandler.createFeed(createFeedRequest);

        // Then
        assertNotNull(response);

        Feed savedFeed = feedRepository.findFeedById(response.feedId()).orElse(null);
        assertNotNull(savedFeed);
        assertEquals(createFeedRequest.userId(), savedFeed.getUserId());
        assertEquals(createFeedRequest.photoId(), savedFeed.getPhotoId());
    }


    @Test
    void 피드_범위를_변경한다() {
        // Given
        Feed feed = feedRepository.save(피드_생성());

        UpdateFeedAccessRangeRequest request = new UpdateFeedAccessRangeRequest(
            feed.getId(),
            "PRIVATE"
        );

        // When
        UpdateFeedResponse response = feedApplicationHandler.updateFeedAccessRange(
            feed.getUserId(),
            request
        );

        // Then
        assertNotNull(response);
        assertEquals("피드가 범위가 성공적으로 업데이트되었습니다.", response.message());

        Feed updatedFeed = feedRepository.findFeedById(feed.getId())
            .orElseThrow(FeedNotFoundException::new);
        assertEquals(AccessRange.PRIVATE, updatedFeed.getAccessRange());
    }

    @Test
    void 피드_이미지를_변경한다() {
        // Given
        Feed feed = feedRepository.save(피드_생성());

        UpdateFeedImageUrlRequest request = new UpdateFeedImageUrlRequest(
            feed.getId(),
            "https://www.google.com"
        );

        // When
        UpdateFeedResponse response = feedApplicationHandler.updateFeedImageUrl(
            feed.getUserId(),
            request
        );

        // Then
        assertNotNull(response);
        assertEquals("피드 이미지가 성공적으로 업데이트되었습니다.", response.message());

        Feed updatedFeed = feedRepository.findFeedById(feed.getId())
            .orElseThrow(FeedNotFoundException::new);
        assertEquals("https://www.google.com", updatedFeed.getImageUrl());
    }
}
