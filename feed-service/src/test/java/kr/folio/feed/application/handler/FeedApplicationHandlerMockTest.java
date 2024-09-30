package kr.folio.feed.application.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.folio.feed.application.mapper.FeedDataMapper;
import kr.folio.feed.application.mapper.FeedEventMapper;
import kr.folio.feed.application.ports.output.FeedRepository;
import kr.folio.feed.application.service.FeedEventService;
import kr.folio.feed.domain.service.FeedDomainUseCase;
import kr.folio.feed.infrastructure.client.FollowServiceClient;
import kr.folio.feed.infrastructure.messaging.kafka.publisher.PhotoDeleteExternalEventKafkaPublisher;
import kr.folio.feed.persistence.entity.FeedOutboxEntity;
import kr.folio.feed.presentation.dto.response.DeleteFeedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FeedApplicationHandlerMockTest {

    @Mock
    private FeedDomainUseCase feedDomainService;

    @Mock
    private FeedRepository feedRepository;

    @Mock
    private FeedDataMapper feedDataMapper;

    @Mock
    private FeedEventService feedEventService;

    @Mock
    private FollowServiceClient followServiceClient;

    @Mock
    private FeedEventMapper feedEventMapper;

    @InjectMocks
    private FeedApplicationHandler feedApplicationHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 피드를_삭제한다() {
        // Given
        Long photoId = 1L;
        Long feedId = 1L;
        int feedCount = 0;

        // When
        when(feedRepository.findPhotoIdByFeedId(feedId)).thenReturn(photoId);
        when(feedRepository.countFeedByPhotoId(photoId)).thenReturn(feedCount);
        when(feedDomainService.isPhotoDeletable(feedCount)).thenReturn(true);
        DeleteFeedResponse response = feedApplicationHandler.deleteFeed(feedId);

        // Then
        assertNotNull(response);
        assertEquals(feedId, response.feedId());
        assertEquals("피드가 성공적으로 제거되었습니다.", response.message());

        verify(feedRepository).findPhotoIdByFeedId(feedId);
        verify(feedRepository).deleteFeedById(feedId);
        verify(feedRepository).countFeedByPhotoId(photoId);
        verify(feedDomainService).isPhotoDeletable(feedCount);
        verify(feedEventService).publishEvent(any());
    }

    @Test
    void 피드를_상세_조회한다() {
        // Given

        // When

        // Then
    }
}
