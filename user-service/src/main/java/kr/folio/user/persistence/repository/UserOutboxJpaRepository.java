package kr.folio.user.persistence.repository;

import java.util.List;
import kr.folio.infrastructure.outbox.OutboxStatus;
import kr.folio.user.persistence.entity.UserOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOutboxJpaRepository extends
    JpaRepository<UserOutboxEntity, Long> {

    List<UserOutboxEntity> findByOutboxStatusAndEventType(OutboxStatus outboxStatus, String eventType);
}
