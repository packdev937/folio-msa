package kr.folio.feed.persistence.repository;

import kr.folio.feed.persistence.entity.FeedOutboxEntity;
import kr.folio.infrastructure.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedOutboxJpaRepository extends JpaRepository<FeedOutboxEntity, Long> {

    Iterable<FeedOutboxEntity> findByOutboxStatusAndEventType(String eventType, OutboxStatus outboxStatus);
}
