package kr.folio.feed.persistence.repository;

import java.util.List;
import java.util.Optional;
import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.persistence.entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface FeedJpaRepository extends JpaRepository<FeedEntity, Long> {

    Long findPhotoIdByFeedId(Long feedId);

    int countFeedByPhotoId(Long photoId);

    List<FeedEntity> findFeedsByUserId(String requestUserId);

    Optional<FeedEntity> findByFeedIdAndRequestUserId(Long feedId, String requestUserId);

    String findUserIdByFeedId(Long feedId);

    List<Long> findFeedIdsByUserId(String userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM FeedEntity f WHERE f.photoId = :photoId")
    void deleteAllFeedByPhotoId(Long photoId);
}
