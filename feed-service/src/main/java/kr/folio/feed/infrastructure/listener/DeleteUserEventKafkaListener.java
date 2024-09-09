package kr.folio.feed.infrastructure.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.domain.core.event.DeleteUserEvent;
import kr.folio.infrastructure.kafka.consumer.GenericEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeleteUserEventKafkaListener implements GenericEventListener<DeleteUserEvent> {

    private final FeedApplicationUseCase feedApplicationUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user_deleted_topic", containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void listen(String message) {

        try {
            DeleteUserEvent event = objectMapper.readValue(message, DeleteUserEvent.class);
            log.info("Received DeleteUserEvent: {} in DeleteUserEventKafkaListener", event);

            handleEvent(event);
        } catch (Exception e) {
            log.error("Failed to deserialize JSON message to DeleteUserEvent", e);
        }
    }

    @Override
    public void handleEvent(DeleteUserEvent event) {

        log.info("Handling event to delete feeds by DeleteUserEvent: {}", event);
        List<Long> feedIds = feedApplicationUseCase.retrieveFeedIdsByUserId(
            event.deleteUserEventDTO().userId());
        try {
            feedIds.forEach(feedId -> {
	try {
	    feedApplicationUseCase.deleteFeed(feedId);
	} catch (Exception exception) {
	    log.error("Failed to delete feed for feedId: {}", feedId, exception);
	}
            });
        } catch (Exception exception) {
            log.error("Failed to delete feeds for user: {}", event.deleteUserEventDTO().userId(),
	exception);
        }
    }
}
