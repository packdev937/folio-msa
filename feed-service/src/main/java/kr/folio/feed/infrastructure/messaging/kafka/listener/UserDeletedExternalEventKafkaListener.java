package kr.folio.feed.infrastructure.messaging.kafka.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import kr.folio.common.domain.core.event.user.UserDeletedExternalEvent;
import kr.folio.feed.application.ports.input.FeedApplicationUseCase;
import kr.folio.feed.application.ports.input.UserDeletedMessageListener;
import kr.folio.feed.domain.core.event.DeleteUserEvent;
import kr.folio.infrastructure.kafka.consumer.KafkaConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDeletedExternalEventKafkaListener implements KafkaConsumer<UserDeletedExternalEvent> {

    private final UserDeletedMessageListener userDeletedMessageListener;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "user_deleted_topic", containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void receive(String message) {

        try {
            UserDeletedExternalEvent event = objectMapper.readValue(message, UserDeletedExternalEvent.class);
            log.info("Received UserDeletedExternalEvent: {} at {}", event, this.getClass().getSimpleName());

            userDeletedMessageListener.handleEvent(event);
        } catch (Exception e) {
            log.error("Failed to deserialize JSON message to DeleteUserEvent", e);
        }
    }
}
