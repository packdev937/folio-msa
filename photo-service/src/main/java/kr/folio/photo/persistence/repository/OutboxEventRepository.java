package kr.folio.photo.persistence.repository;

import java.util.List;
import kr.folio.infrastructure.outbox.OutboxStatus;
import kr.folio.photo.persistence.entity.PhotoOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<PhotoOutboxEntity, Long> {

    List<PhotoOutboxEntity> findByOutboxStatus(OutboxStatus outboxStatus);

    List<PhotoOutboxEntity> findByOutboxStatusAndEventType(OutboxStatus outboxStatus, String eventType);
}
