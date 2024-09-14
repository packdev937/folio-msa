package kr.folio.feed.infrastructure.messaging.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.application.ports.input.PhotoCreatedMessageListener;
import kr.folio.feed.domain.core.event.PhotoCreatedExternalEvent;
import kr.folio.feed.presentation.dto.request.CreateFeedRequest;
import kr.folio.infrastructure.kafka.consumer.KafkaConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PhotoCreatedExternalEventKafkaListener implements KafkaConsumer<PhotoCreatedExternalEvent> {

    private final PhotoCreatedMessageListener photoCreatedMessageListener;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "photo_created_topic", containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void receive(String message) {
        try {
            PhotoCreatedExternalEvent event = objectMapper.readValue(message, PhotoCreatedExternalEvent.class);
            log.info("Received PhotoCreatedExternalEvent: {} at PhotoCreatedExternalEventKafkaListener", event);

            photoCreatedMessageListener
            handleEvent(event);
        } catch (Exception exception) {
            log.error("Failed to deserialize JSON message to PhotoCreatedExternalEvent", exception);
        }
    }

    public void handleEvent(PhotoCreatedExternalEvent event) {
        log.info("Handling event to create feeds by CreatePhotoEvent: {}", event);

        if (event.getUserIds() != null) {
            event.getUserIds().forEach(
	userId -> {
	    try {
	        List<String> taggedUserIds = event.getUserIds();
	        taggedUserIds.remove(userId);

	        feedApplicationUseCase.createFeed(
	            new CreateFeedRequest(
		userId,
		event.getAggregateId(),
		event.getPhotoImageUrl(),
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
