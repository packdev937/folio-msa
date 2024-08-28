package kr.folio.photo.application.handler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import kr.folio.photo.application.mapper.PhotoDataMapper;
import kr.folio.photo.application.ports.output.PhotoRepository;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.core.event.CreatedPhotoEvent;
import kr.folio.photo.domain.core.event.RetrievedPhotoEvent;
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
    private final String UTC = "UTC";
    @Transactional
    public CreatedPhotoEvent createPhoto(CreatePhotoRequest createPhotoRequest) {
        Photo photo = photoDataMapper.toDomain(createPhotoRequest);
        CreatedPhotoEvent photoCreatedEvent = photoDomainUseCase.validatePhoto(photo);
        Photo savedPhoto = photoRepository.createPhoto(photo);

        if (savedPhoto == null) {
            log.error("Could not save photo with id: {}", createPhotoRequest.id());
            throw new IllegalArgumentException();
        }

        log.info("Returning CreatedPhotoEvent for photo id: {}", createPhotoRequest.id());
        return photoCreatedEvent;
    }

    public RetrievedPhotoEvent retrievePhoto(String requestUserId, Long photoId) {
        Photo retrievedPhoto = photoRepository.findPhotoById(photoId);
        if (retrievedPhoto == null) {
            log.error("Could not find photo with id: {}", photoId);
            throw new IllegalArgumentException();
            // todo : PhotoNotFoundException
        }

        log.info("Returning RetrievedPhotoEvent for photo id: {}", photoId);
        return new RetrievedPhotoEvent(retrievedPhoto, requestUserId,
            ZonedDateTime.now(ZoneId.of(UTC));
    }
}
