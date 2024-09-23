package kr.folio.user.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "user_outbox")
public class UserOutboxEntity extends BaseEntity {

    @Id
    private UUID id;
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
        if (!(o instanceof UserOutboxEntity e)) {
            return false;
        }
        return Objects.equals(id, e.id);
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

