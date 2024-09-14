package kr.folio.photo.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import kr.folio.infrastructure.outbox.OutboxStatus;
import kr.folio.infrastructure.saga.SagaStatus;
import kr.folio.photo.domain.core.event.PhotoCreatedExternalEvent;
import kr.folio.photo.persistence.entity.PhotoOutboxEntity;
import kr.folio.photo.persistence.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class PhotoEventRecorder {

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void recordEvent(PhotoCreatedExternalEvent photoCreatedExternalEvent) {
        String payload = createPayload(photoCreatedExternalEvent);

        PhotoOutboxEntity outboxEvent = PhotoOutboxEntity.builder()
            .id(UUID.randomUUID())
            .sagaId(photoCreatedExternalEvent.getSagaId())
            .eventType(photoCreatedExternalEvent.getEventType())
            .payload(payload)
            .outboxStatus(OutboxStatus.STARTED)
            .sagaStatus(SagaStatus.STARTED)
            .createdAt(photoCreatedExternalEvent.getCreatedAt())
            .build();

        outboxEventRepository.save(outboxEvent);
    }

    private String createPayload(PhotoCreatedExternalEvent photoCreatedExternalEvent) {
        try {
            return objectMapper.writeValueAsString(photoCreatedExternalEvent);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException(
	"Failed to serialize photoCreatedExternalEvent at PhotoEventRecorder",
	jsonProcessingException);
        }
    }
}
