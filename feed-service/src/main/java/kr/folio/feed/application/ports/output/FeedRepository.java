package kr.folio.feed.application.ports.output;

import java.util.List;
import java.util.Optional;
import kr.folio.feed.domain.core.entity.Feed;

public interface FeedRepository {

    Feed save(Feed feed);

    Optional<Feed> findFeedById(Long aLong);

    void deleteFeedById(Long photoId);

    Long findPhotoIdByFeedId(Long feedId);

    int countFeedByPhotoId(Long photoId);

    List<Feed> findFeedsByUserId(String requestUserId);

}
