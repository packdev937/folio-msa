package kr.folio.feed.domain.core.event;

import java.time.ZonedDateTime;
import kr.folio.feed.presentation.dto.event.DeleteFeedEventDTO;

public record DeleteFeedEvent(
    DeleteFeedEventDTO deleteFeedEventDTO,
    ZonedDateTime createdAt
) implements DomainEvent<DeleteFeedEventDTO> {

}
