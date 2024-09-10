package kr.folio.photo.infrastructure.listener;

import java.util.List;
import kr.folio.photo.domain.core.event.PhotoCreatedExternalEvent;
import kr.folio.photo.persistence.event.OutboxEvent;
import kr.folio.photo.persistence.repository.OutboxEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class PhotoEventMessageListener {

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendEvent(PhotoCreatedExternalEvent event) {
        // 트랜잭션이 커밋된 후 Outbox 테이블에서 이벤트를 읽어 Kafka로 전송
        List<OutboxEvent> pendingEvents = outboxEventRepository.findByStatus("PENDING");

        for (OutboxEvent outboxEvent : pendingEvents) {
            try {
	kafkaTemplate.send(outboxEvent.getEventType(), outboxEvent.getPayload());
	outboxEvent.setStatus("SENT");
	outboxEventRepository.save(outboxEvent);  // 상태 업데이트
            } catch (Exception e) {
	e.printStackTrace();
            }
        }
    }
}
