package kr.folio.photo.infrastructure.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.folio.common.domain.core.event.UserDeletedExternalEvent;
import kr.folio.infrastructure.kafka.consumer.GenericEventListener;
import kr.folio.photo.application.ports.input.PhotoApplicationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDeletedEventKafkaListener implements
    GenericEventListener<UserDeletedExternalEvent> {

    private final PhotoApplicationUseCase photoApplicationUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user_deleted_topic", containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void listen(String message) {

        try {
            // todo : Avro로 변경
            UserDeletedExternalEvent event = objectMapper.readValue(message,
	UserDeletedExternalEvent.class);
            log.info("Received DeleteUserEvent: {} in UserDeletedEventKafkaListener", event);

            handleEvent("userId");
        } catch (Exception e) {
            log.error("Failed to deserialize JSON message to DeleteUserEvent", e);
        }

    }

    public void handleEvent(String userId) {
        try {
            photoApplicationUseCase.deleteUserFromTag(userId);
        } catch (Exception exception) {
            log.error("Failed to delete photos for user: {}", userId);
        }
    }
}
