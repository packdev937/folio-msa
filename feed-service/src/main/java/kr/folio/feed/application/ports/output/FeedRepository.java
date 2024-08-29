package kr.folio.feed.application.ports.output;

import java.util.Optional;
import kr.folio.feed.domain.core.entity.Feed;

public interface FeedRepository {

    Feed save(Feed feed);

    Optional<Feed> findFeedById(Long feedId);
}
