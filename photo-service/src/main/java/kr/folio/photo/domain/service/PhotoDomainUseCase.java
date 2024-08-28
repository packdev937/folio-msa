package kr.folio.photo.domain.service;

import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.core.event.CreatedPhotoEvent;

public interface PhotoDomainUseCase {

    CreatedPhotoEvent validatePhoto(Photo photo);
}
