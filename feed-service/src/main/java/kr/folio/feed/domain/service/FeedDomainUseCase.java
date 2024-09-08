package kr.folio.feed.domain.service;

import kr.folio.feed.domain.core.entity.Feed;
import kr.folio.feed.domain.core.event.DeleteFeedEvent;

public interface FeedDomainUseCase {

    boolean isPhotoDeletable(int feedCount);

    void updateAccessRange(Feed feed, String updatedAccessRange);

    void updateImageUrl(Feed feed, String updatedImageUrl);

    DeleteFeedEvent createDeleteFeedEvent(Long photoId);
}
