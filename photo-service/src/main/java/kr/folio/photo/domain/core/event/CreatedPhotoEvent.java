package kr.folio.photo.domain.core.event;

import java.time.ZonedDateTime;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.presentation.dto.event.CreatedPhotoEventDTO;

public record CreatedPhotoEvent(
    CreatedPhotoEventDTO createdPhotoEventDTO,
    ZonedDateTime createdAt
) implements DomainEvent<Photo> {

}
