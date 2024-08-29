package kr.folio.feed.persistence.repository;

import kr.folio.feed.persistence.entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedJpaRepository extends JpaRepository<FeedEntity, Long> {

}
