package kr.folio.feed.domain.core.event;

import java.time.ZonedDateTime;
import kr.folio.feed.presentation.dto.event.CreatePhotoEventDTO;

public record CreatePhotoEvent(
    CreatePhotoEventDTO createdPhotoEventDTO,
    ZonedDateTime createdAt
) implements DomainEvent<CreatePhotoEventDTO> {

}
