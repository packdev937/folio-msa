package kr.folio.photo.infrastructure.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.folio.photo.domain.core.event.PhotoCreatedExternalEvent;
import kr.folio.photo.persistence.event.OutboxEvent;
import kr.folio.photo.persistence.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class PhotoEventRecordListener {

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    private final String PENDING = "PENDING";

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void recordEvent(PhotoCreatedExternalEvent event) {
        try {
            // 트랜잭션이 커밋되기 전에 Outbox 테이블에 이벤트 저장
            String payload = objectMapper.writeValueAsString(event);

            String PENDING = "PENDING";
            OutboxEvent outboxEvent = OutboxEvent.of(
	event.getEventType(),
	event.getAggregateId(),
	payload,
	PENDING
            );

            outboxEventRepository.save(outboxEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize event", e);
        }
    }
}
