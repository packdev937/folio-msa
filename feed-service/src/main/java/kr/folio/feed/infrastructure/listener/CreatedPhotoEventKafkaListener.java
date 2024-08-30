package kr.folio.feed.infrastructure.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.application.ports.input.GenericEventListener;
import kr.folio.feed.domain.core.event.CreatedPhotoEvent;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@RequiredArgsConstructor
public class CreatedPhotoEventKafkaListener implements GenericEventListener<CreatedPhotoEvent> {

    private final FeedApplicationUseCase feedApplicationUseCase;
    private final ObjectMapper objectMapper;

    // todo : group 명은 후에 수정할 필요가 있음
    @KafkaListener(topics = "created_photo_topic", groupId = "feed-service")
    @Override
    public void listen(String message) {
        try {
            CreatedPhotoEvent event = objectMapper.readValue(message, CreatedPhotoEvent.class);
            log.info("Received CreatedPhotoEvent: {}  in CreatedPhotoEventKafkaListener", event);

            handleEvent(event);
        } catch (Exception e) {
            log.error("Failed to deserialize JSON message to CreatedPhotoEvent", e);
        }
    }

    @Override
    public void handleEvent(CreatedPhotoEvent createdPhotoEvent) {
        log.info("Handling event to create feeds by CreatedPhotoEvent: {}", createdPhotoEvent);

        createdPhotoEvent.createdPhotoEventDTO().taggedUserIds()
            .forEach(
	userId -> feedApplicationUseCase.createFeed(
	    new CreateFeedRequest(
	        userId,
	        createdPhotoEvent.createdPhotoEventDTO().photoId(),
	        createdPhotoEvent.createdPhotoEventDTO().photoImageUrl()
	    )
	)
            );
    }
}
