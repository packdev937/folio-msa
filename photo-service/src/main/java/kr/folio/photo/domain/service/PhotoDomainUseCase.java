package kr.folio.photo.domain.service;

import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.core.event.PhotoCreatedEvent;

public interface PhotoDomainUseCase {

    PhotoCreatedEvent validatePhoto(Photo photo);
}
