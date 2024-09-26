package kr.folio.feed.persistence.repository;

import kr.folio.feed.persistence.entity.FeedOutboxEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedOutboxJpaRepository extends JpaRepository<FeedOutboxEntity, Long> {

}
