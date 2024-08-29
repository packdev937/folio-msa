package kr.folio.feed.domain.core.event;

import java.time.ZonedDateTime;
import kr.folio.feed.domain.core.entity.Feed;

public record CreatedFeedEvent(
    Feed feed,
    ZonedDateTime createdAt
) implements DomainEvent<Feed> {

}
