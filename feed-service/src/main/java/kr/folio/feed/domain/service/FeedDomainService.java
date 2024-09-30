package kr.folio.feed.domain.service;

import kr.folio.feed.domain.core.entity.Feed;
import org.springframework.stereotype.Component;

@Component
public class FeedDomainService implements FeedDomainUseCase {

    @Override
    public boolean isPhotoDeletable(int feedCount) {
        if (feedCount == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void updateAccessRange(Feed feed, String updatedAccessRange) {
        feed.updateAccessRange(updatedAccessRange);
    }

    @Override
    public void updateImageUrl(Feed feed, String updatedImageUrl) {
        feed.updateImageUrl(updatedImageUrl);
    }
}
