package kr.folio.feed.domain.core.event;

import java.time.ZonedDateTime;
import kr.folio.feed.presentation.dto.event.DeleteUserEventDTO;

public record DeleteUserEvent(
    DeleteUserEventDTO deleteUserEventDTO,
    ZonedDateTime createdAt
) implements DomainEvent<DeleteUserEventDTO> {

}
