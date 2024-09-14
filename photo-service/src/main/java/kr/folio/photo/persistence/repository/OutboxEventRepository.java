package kr.folio.photo.persistence.repository;

import java.util.List;
import kr.folio.photo.persistence.event.PhotoOutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<PhotoOutboxEvent, Long> {

    List<PhotoOutboxEvent> findByStatus(String status);

    List<PhotoOutboxEvent> findByStatusAndEventType(String pending, String photoCreatedEvent);
}
