package kr.folio.feed.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import kr.folio.common.domain.core.event.ExternalEvent;
import kr.folio.common.domain.core.event.photo.PhotoDeleteExternalEvent;
import kr.folio.feed.persistence.entity.FeedOutboxEntity;
import kr.folio.feed.persistence.repository.FeedOutboxJpaRepository;
import kr.folio.infrastructure.outbox.OutboxStatus;
import kr.folio.infrastructure.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeedEventRecorder {

    private final FeedOutboxJpaRepository feedOutboxRepository;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void recordEvent(ExternalEvent externalEvent) {
        if (externalEvent instanceof PhotoDeleteExternalEvent) {
            PhotoDeleteExternalEvent event = (PhotoDeleteExternalEvent) externalEvent;
            String payload = createPayload(event);

            FeedOutboxEntity outboxEvent = FeedOutboxEntity.builder()
	.outboxId(UUID.randomUUID())
	.sagaId(event.getSagaId())
	.eventType(event.getEventType())
	.payload(payload)
	.outboxStatus(OutboxStatus.STARTED)
	.sagaStatus(SagaStatus.STARTED)
	.build();

            log.info("Recording event type {} at {}", event.getEventType(), FeedEventRecorder.class.getSimpleName());
            saveOutboxEvent(outboxEvent);
        }
    }

    private void saveOutboxEvent(FeedOutboxEntity outboxEvent) {
        try {
            feedOutboxRepository.save(outboxEvent);
        } catch (Exception exception) {
            throw new RuntimeException("Failed to save Outbox Event", exception);
        }
    }

    private String createPayload(ExternalEvent externalEvent) {
        try {
            return objectMapper.writeValueAsString(externalEvent);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException("Failed to serialize " + externalEvent.getClass().getSimpleName(), jsonProcessingException);
        }
    }
}
