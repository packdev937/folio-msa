package kr.folio.feed.application.service;

import kr.folio.common.domain.core.event.photo.PhotoDeleteExternalEvent;
import kr.folio.feed.infrastructure.messaging.kafka.publisher.PhotoDeleteExternalEventKafkaPublisher;
import kr.folio.feed.persistence.entity.FeedOutboxEntity;
import kr.folio.feed.persistence.repository.FeedOutboxJpaRepository;
import kr.folio.infrastructure.outbox.OutboxStatus;
import kr.folio.infrastructure.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeedEventMessenger {

    private final FeedOutboxJpaRepository feedOutboxRepository;
    private final PhotoDeleteExternalEventKafkaPublisher photoDeleteExternalEventKafkaPublisher;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendEvent(PhotoDeleteExternalEvent photoDeleteExternalEvent) {
        log.info("Sending Outbox Event : {} at {} to photoDeleteExternalEventPublisher", photoDeleteExternalEvent.getEventType(), this.getClass().getSimpleName());
        feedOutboxRepository.findByOutboxStatusAndEventType(
            photoDeleteExternalEvent.getEventType(),
            OutboxStatus.STARTED
        ).forEach(feedOutboxEntity -> {
            try {
	photoDeleteExternalEventKafkaPublisher.publish(
	    feedOutboxEntity,
	    this::updateOutboxStatus
	);
	feedOutboxEntity.updateSagaStatus(SagaStatus.PROCESSING);
	feedOutboxRepository.save(feedOutboxEntity);
            } catch (Exception exception) {
	feedOutboxEntity.updateOutboxStatus(OutboxStatus.FAILED);
	feedOutboxEntity.updateSagaStatus(SagaStatus.FAILED);
	feedOutboxRepository.save(feedOutboxEntity);
            }
        });
    }

    @Transactional
    public void updateOutboxStatus(FeedOutboxEntity feedOutboxEntity, OutboxStatus outboxStatus) {
        feedOutboxEntity.updateOutboxStatus(outboxStatus);
        feedOutboxRepository.save(feedOutboxEntity);
    }
}
