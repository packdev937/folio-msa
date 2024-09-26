package kr.folio.feed.infrastructure.messaging.kafka.publisher;

import java.util.function.BiConsumer;
import kr.folio.feed.persistence.entity.FeedOutboxEntity;
import kr.folio.infrastructure.kafka.producer.GenericEventPublisher;
import kr.folio.infrastructure.outbox.OutboxStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PhotoDeleteExternalEventKafkaPublisher implements GenericEventPublisher<FeedOutboxEntity> {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(FeedOutboxEntity feedOutboxEntity,
        BiConsumer<FeedOutboxEntity, OutboxStatus> outboxCallback) {
        String payload = feedOutboxEntity.getPayload();
        try {
            log.info("Published PhotoDeleteExternalEvent to Kafka");

            kafkaTemplate.send("photo_delete_topic", payload);

            outboxCallback.accept(feedOutboxEntity, OutboxStatus.COMPLETED);
        } catch (Exception exception) {
            log.error("Failed to send CreatedPhotoEvent to Kafka", exception);
            outboxCallback.accept(feedOutboxEntity, OutboxStatus.FAILED);
        }
    }
}
