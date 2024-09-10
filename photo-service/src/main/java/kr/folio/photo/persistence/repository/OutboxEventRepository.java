package kr.folio.photo.persistence.repository;

import java.util.List;
import kr.folio.photo.persistence.event.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, Long> {

    List<OutboxEvent> findByStatus(String status);
}
