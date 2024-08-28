package kr.folio.photo.domain.core.event;

import java.time.ZonedDateTime;
import kr.folio.photo.domain.core.entity.Photo;

public record PhotoCreatedEvent(Photo photo, ZonedDateTime createdAt) implements DomainEvent<Photo> {

}
