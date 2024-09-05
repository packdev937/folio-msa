package kr.folio.feed.persistence.repository;

import java.util.List;
import java.util.Optional;
import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.persistence.entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedJpaRepository extends JpaRepository<FeedEntity, Long> {

    Long findPhotoIdByFeedId(Long feedId);

    int countFeedByPhotoId(Long photoId);

    List<FeedEntity> findFeedsByUserId(String requestUserId);

    Optional<FeedEntity> findByFeedIdAndRequestUserId(Long feedId, String requestUserId);

    String findUserIdByFeedId(Long feedId);
}
