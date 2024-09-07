package kr.folio.photo.infrastructure.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.folio.photo.application.ports.input.GenericEventListener;
import kr.folio.photo.application.ports.input.PhotoApplicationUseCase;
import kr.folio.photo.domain.core.event.DeleteUserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeleteUserEventKafkaListener implements GenericEventListener<DeleteUserEvent> {

    private final PhotoApplicationUseCase photoApplicationUseCase;
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

        log.info("Handling event to delete photos by DeleteUserEvent: {}", event);

        try {
            photoApplicationUseCase.deleteUserFromTag(event.deleteUserEventDTO().userId());
        } catch (Exception exception) {
            log.error("Failed to delete photos for user: {}", event.deleteUserEventDTO().userId(),
	exception);
        }
    }
}
