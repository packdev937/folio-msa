package kr.folio.feed.infrastructure.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.application.ports.input.GenericEventListener;
import kr.folio.feed.domain.core.event.CreatePhotoEvent;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreatePhotoEventKafkaListener implements GenericEventListener<CreatePhotoEvent> {

    private final FeedApplicationUseCase feedApplicationUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "photo_created_topic", containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void listen(String message) {
        try {
            CreatePhotoEvent event = objectMapper.readValue(message, CreatePhotoEvent.class);
            log.info("Received CreatePhotoEvent: {} in CreatePhotoEventKafkaListener", event);

            handleEvent(event);
        } catch (Exception e) {
            log.error("Failed to deserialize JSON message to CreatePhotoEvent", e);
        }
    }

    @Override
    public void handleEvent(CreatePhotoEvent createdPhotoEvent) {
        log.info("Handling event to create feeds by CreatePhotoEvent: {}", createdPhotoEvent);

        if (createdPhotoEvent.createdPhotoEventDTO().taggedUserIds() != null) {
            createdPhotoEvent.createdPhotoEventDTO().taggedUserIds().forEach(
	userId -> {
	    try {
	        List<String> taggedUserIds = createdPhotoEvent.createdPhotoEventDTO().taggedUserIds();
	        taggedUserIds.remove(userId);

	        feedApplicationUseCase.createFeed(
	            new CreateFeedRequest(
		userId,
		createdPhotoEvent.createdPhotoEventDTO().photoId(),
		createdPhotoEvent.createdPhotoEventDTO().photoImageUrl(),
		taggedUserIds
	            )
	        );
	    } catch (Exception e) {
	        log.error("Failed to create feed for user: {}", userId, e);
	    }
	}
            );
        } else {
            log.warn("No tagged user IDs in the event");
        }
    }
}
