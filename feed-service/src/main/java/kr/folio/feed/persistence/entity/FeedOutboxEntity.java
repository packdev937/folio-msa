package kr.folio.feed.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import kr.folio.infrastructure.outbox.OutboxStatus;
import kr.folio.infrastructure.saga.SagaStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "feed_outbox")
public class FeedOutboxEntity extends BaseEntity {

    @Id
    @Column(name = "feed_outbox_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "outbox_id")
    private UUID outboxId;

    @Column(name = "saga_id")
    private UUID sagaId;

    private LocalDateTime processedAt;

    private String eventType;

    @Lob
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;

    @Enumerated(EnumType.STRING)
    private SagaStatus sagaStatus;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedOutboxEntity that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void updateOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }

    public void updateSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
    }
}
