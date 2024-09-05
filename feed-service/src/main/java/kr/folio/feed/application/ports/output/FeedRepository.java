package kr.folio.feed.application.ports.output;

import java.util.List;
import java.util.Optional;
import kr.folio.feed.domain.core.entity.Feed;

public interface FeedRepository {

    Feed save(Feed feed);

    Optional<Feed> findByFeedIdAndRequestUserId(Long feedId, String requestUserId);

    void deleteFeedById(Long photoId);

    Long findPhotoIdByFeedId(Long feedId);

    int countFeedByPhotoId(Long photoId);

    List<Feed> findFeedsByUserId(String requestUserId);

    Optional<Feed> findFeedById(Long feedId);

    Optional<String> findUserIdByFeedId(Long feedId);
}
