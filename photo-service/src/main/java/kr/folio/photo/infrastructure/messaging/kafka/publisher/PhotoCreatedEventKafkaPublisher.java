package kr.folio.photo.infrastructure.messaging.kafka.publisher;

import java.util.function.BiConsumer;
import kr.folio.infrastructure.kafka.producer.GenericEventPublisher;
import kr.folio.infrastructure.outbox.OutboxStatus;
import kr.folio.photo.persistence.entity.PhotoOutboxEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component("CreatedPhotoEventKafkaPublisher")
public class PhotoCreatedEventKafkaPublisher implements GenericEventPublisher<PhotoOutboxEntity> {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(
        PhotoOutboxEntity photoOutboxEntity,
        BiConsumer<PhotoOutboxEntity, OutboxStatus> outboxCallback) {

        String payload = photoOutboxEntity.getPayload();

        try {
            kafkaTemplate.send("created_photo_topic", payload);

            log.info("Published CreatedPhotoEvent to Kafka");
            outboxCallback.accept(photoOutboxEntity, OutboxStatus.COMPLETED);
        } catch (Exception exception) {
            log.error("Failed to send CreatedPhotoEvent to Kafka", exception);
            outboxCallback.accept(photoOutboxEntity, OutboxStatus.FAILED);
        }
    }
}
