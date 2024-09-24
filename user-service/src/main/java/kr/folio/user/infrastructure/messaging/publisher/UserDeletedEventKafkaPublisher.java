package kr.folio.user.infrastructure.messaging.publisher;

import java.util.function.BiConsumer;
import kr.folio.infrastructure.kafka.producer.GenericEventPublisher;
import kr.folio.infrastructure.outbox.OutboxStatus;
import kr.folio.user.persistence.entity.UserOutboxEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDeletedEventKafkaPublisher implements GenericEventPublisher<UserOutboxEntity> {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(UserOutboxEntity userOutboxEntity,
        BiConsumer<UserOutboxEntity, OutboxStatus> outboxCallback) {

        String payload = userOutboxEntity.getPayload();

        try {
            kafkaTemplate.send("deleted_user_topic", payload);

            log.info("Published DeletedUserEvent to Kafka");
            outboxCallback.accept(userOutboxEntity, OutboxStatus.COMPLETED);
        } catch (Exception exception) {
            log.error("Failed to send DeletedUserEvent to Kafka", exception);
            outboxCallback.accept(userOutboxEntity, OutboxStatus.FAILED);
        }
    }
}
