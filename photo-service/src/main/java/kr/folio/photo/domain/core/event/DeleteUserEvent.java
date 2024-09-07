package kr.folio.photo.domain.core.event;

import java.time.ZonedDateTime;
import kr.folio.photo.presentation.dto.event.DeleteUserEventDTO;

public record DeleteUserEvent(
    DeleteUserEventDTO deleteUserEventDTO,
    ZonedDateTime createdAt
) implements DomainEvent<DeleteUserEventDTO> {

}
