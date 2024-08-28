package kr.folio.photo.domain.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.core.event.CreatedPhotoEvent;
import kr.folio.photo.infrastructure.annotation.DomainService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class PhotoDomainService implements PhotoDomainUseCase {
    private final String UTC = "UTC";

    @Override
    public CreatedPhotoEvent validatePhoto(Photo photo) {
        // 순수 도메인 로직을 검증하는 공간
        return new CreatedPhotoEvent(photo, ZonedDateTime.now(ZoneId.of(UTC)));
    }
}
