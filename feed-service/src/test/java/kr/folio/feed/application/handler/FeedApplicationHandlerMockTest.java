package kr.folio.feed.application.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.folio.feed.application.mapper.FeedDataMapper;
import kr.folio.feed.application.ports.output.FeedRepository;
import kr.folio.feed.domain.core.event.DeleteFeedEvent;
import kr.folio.feed.domain.service.FeedDomainUseCase;
import kr.folio.feed.infrastructure.client.FollowServiceClient;
import kr.folio.feed.infrastructure.messaging.kafka.publisher.PhotoDeleteEventKafkaPublisher;
import kr.folio.feed.presentation.dto.event.DeleteFeedEventDTO;
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
    private FollowServiceClient followServiceClient;

    @Mock
    private PhotoDeleteEventKafkaPublisher deleteFeedEventKafkaPublisher;

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
        when(feedDomainService.createDeleteFeedEvent(photoId)).thenReturn(new DeleteFeedEvent(
            new DeleteFeedEventDTO(photoId), null));
        DeleteFeedResponse response = feedApplicationHandler.deleteFeed(feedId);

        // Then
        assertNotNull(response);
        assertEquals(feedId, response.feedId());
        assertEquals("피드가 성공적으로 제거되었습니다.", response.message());

        verify(feedRepository).findPhotoIdByFeedId(feedId);
        verify(feedRepository).deleteFeedById(feedId);
        verify(feedRepository).countFeedByPhotoId(photoId);
        verify(feedDomainService).isPhotoDeletable(feedCount);
        verify(feedDomainService).createDeleteFeedEvent(photoId);
        verify(deleteFeedEventKafkaPublisher).publish(any(DeleteFeedEvent.class), any());
    }

    @Test
    void 피드를_상세_조회한다() {
        // Given

        // When

        // Then
    }
}
