package kr.folio.photo.application.service;

import java.util.List;
import kr.folio.infrastructure.outbox.OutboxStatus;
import kr.folio.infrastructure.saga.SagaStatus;
import kr.folio.photo.domain.core.event.PhotoCreatedExternalEvent;
import kr.folio.photo.infrastructure.messaging.kafka.publisher.PhotoCreatedEventKafkaPublisher;
import kr.folio.photo.persistence.entity.PhotoOutboxEntity;
import kr.folio.photo.persistence.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class PhotoEventMessenger {

    private final OutboxEventRepository outboxEventRepository;
    private final PhotoCreatedEventKafkaPublisher photoCreatedEventKafkaPublisher;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendEvent(PhotoCreatedExternalEvent event) {
        List<PhotoOutboxEntity> outboxes = outboxEventRepository.findByOutboxStatusAndEventType(
            OutboxStatus.STARTED, event.getEventType()
        );

        outboxes.addAll(outboxEventRepository.findByOutboxStatusAndEventType(
            OutboxStatus.FAILED, event.getEventType()
        ));

        for (PhotoOutboxEntity outbox : outboxes) {
            try {

                photoCreatedEventKafkaPublisher.publish(
                    outbox,
                    this::updateOutboxStatus
                );

                outbox.updateOutboxStatus(OutboxStatus.COMPLETED);
                outbox.updateSagaStatus(SagaStatus.PROCESSING);
                outboxEventRepository.save(outbox);
            } catch (Exception exception) {
                outbox.updateOutboxStatus(OutboxStatus.FAILED);
                outbox.updateSagaStatus(SagaStatus.FAILED);
                outboxEventRepository.save(outbox);
            }
        }
    }

    @Transactional
    public void updateOutboxStatus(PhotoOutboxEntity photoOutboxEntity, OutboxStatus outboxStatus) {
        photoOutboxEntity.updateOutboxStatus(outboxStatus);
        outboxEventRepository.save(photoOutboxEntity);
    }
}
