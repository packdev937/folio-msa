package kr.folio.photo.application.handler;

import kr.folio.photo.application.mapper.PhotoDataMapper;
import kr.folio.photo.application.ports.output.PhotoRepository;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.core.event.PhotoCreatedEvent;
import kr.folio.photo.domain.service.PhotoDomainUseCase;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PhotoApplicationHandler {

    private final PhotoDomainUseCase photoDomainUseCase;
    private final PhotoRepository photoRepository;
    private final PhotoDataMapper photoDataMapper;

    @Transactional
    public PhotoCreatedEvent createPhoto(CreatePhotoRequest createPhotoRequest) {
        Photo photo = photoDataMapper.toDomain(createPhotoRequest);
        PhotoCreatedEvent photoCreatedEvent = photoDomainUseCase.validatePhoto(photo);
        Photo savedPhoto = photoRepository.createPhoto(photo);

        if (savedPhoto == null) {
            log.error("Could not save photo with id: {}", createPhotoRequest.id());
            throw new IllegalArgumentException();
        }

        log.info("Returning PhotoCreatedEvent for photo id: {}", createPhotoRequest.id());
        return photoCreatedEvent;
    }
}
