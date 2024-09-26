package kr.folio.user.application.service;

import java.util.List;
import kr.folio.common.domain.core.event.user.UserDeletedExternalEvent;
import kr.folio.infrastructure.outbox.OutboxStatus;
import kr.folio.infrastructure.saga.SagaStatus;
import kr.folio.user.infrastructure.messaging.publisher.UserDeletedEventKafkaPublisher;
import kr.folio.user.persistence.entity.UserOutboxEntity;
import kr.folio.user.persistence.repository.UserOutboxJpaRepository;
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
public class UserEventMessenger {

    private final UserOutboxJpaRepository userOutboxRepository;
    private final UserDeletedEventKafkaPublisher userDeletedEventKafkaPublisher;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendEvent(UserDeletedExternalEvent event) {
        log.info("Sending Outbox Event: {} at {}", event.getEventType(), this.getClass().getSimpleName());
        List<UserOutboxEntity> outboxes = userOutboxRepository.findByOutboxStatusAndEventType(
            OutboxStatus.STARTED, event.getEventType()
        );

        outboxes.addAll(userOutboxRepository.findByOutboxStatusAndEventType(
            OutboxStatus.FAILED, event.getEventType()
        ));

        log.info("Outboxes: {}", outboxes.size());
        for (UserOutboxEntity outbox : outboxes) {
            try {
	userDeletedEventKafkaPublisher.publish(
	    outbox,
	    this::updateOutboxStatus
	);
	outbox.updateSagaStatus(SagaStatus.PROCESSING);
	userOutboxRepository.save(outbox);
            } catch (Exception exception) {
	outbox.updateOutboxStatus(OutboxStatus.FAILED);
	outbox.updateSagaStatus(SagaStatus.FAILED);
	userOutboxRepository.save(outbox);
            }
        }
    }

    @Transactional
    public void updateOutboxStatus(UserOutboxEntity userOutboxEntity, OutboxStatus outboxStatus) {
        userOutboxEntity.updateOutboxStatus(outboxStatus);
        userOutboxRepository.save(userOutboxEntity);
    }

}
