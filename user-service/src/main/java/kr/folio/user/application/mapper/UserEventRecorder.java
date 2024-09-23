package kr.folio.user.application.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import kr.folio.common.domain.core.event.ExternalEvent;
import kr.folio.common.domain.core.event.user.UserDeletedExternalEvent;
import kr.folio.infrastructure.outbox.OutboxStatus;
import kr.folio.infrastructure.saga.SagaStatus;
import kr.folio.user.persistence.entity.UserOutboxEntity;
import kr.folio.user.persistence.repository.UserOutboxJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class UserEventRecorder {

    private final UserOutboxJpaRepository userOutboxRepository;
    private final ObjectMapper objectMapper;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void recordEvent(ExternalEvent externalEvent) {
        if (externalEvent instanceof UserDeletedExternalEvent) {
            UserDeletedExternalEvent event = (UserDeletedExternalEvent) externalEvent;
            String payload = createPayload(event);

            UserOutboxEntity outboxEvent = UserOutboxEntity.builder()
	.id(UUID.randomUUID())
	.sagaId(event.getSagaId())
	.eventType(event.getEventType())
	.payload(payload)
	.outboxStatus(OutboxStatus.STARTED)
	.sagaStatus(SagaStatus.STARTED)
	.build();

            saveOutboxEvent(outboxEvent);
        }
    }

    private void saveOutboxEvent(UserOutboxEntity outboxEvent) {
        try {
            userOutboxRepository.save(outboxEvent);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save Outbox Event", e);
        }
    }

    private String createPayload(ExternalEvent externalEvent) {
        try {
            return objectMapper.writeValueAsString(externalEvent);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new RuntimeException(
	"Failed to serialize " + externalEvent.getClass().getSimpleName(), jsonProcessingException);
        }
    }
}
