package kr.folio.photo.persistence.event;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import kr.folio.photo.persistence.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "outbox_event")
public class OutboxEvent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;
    private Long aggregateId;

    @Lob
    private String payload;
    private String status;

    public static OutboxEvent of(String eventType, Long aggregateId, String payload,
        String status) {
        return new OutboxEvent(eventType, aggregateId, payload, status);
    }

    public OutboxEvent(String eventType, Long aggregateId, String payload, String status) {
        this.eventType = eventType;
        this.aggregateId = aggregateId;
        this.payload = payload;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
