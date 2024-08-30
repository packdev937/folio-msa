package kr.folio.feed.domain.core.event;

import java.time.ZonedDateTime;
import kr.folio.feed.presentation.dto.event.CreatedPhotoEventDTO;

public record CreatedPhotoEvent(
    CreatedPhotoEventDTO createdPhotoEventDTO,
    ZonedDateTime createdAt
) implements DomainEvent<CreatedPhotoEventDTO> {

}
