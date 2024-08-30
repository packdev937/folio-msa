package kr.folio.photo.application.handler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;
import kr.folio.photo.application.mapper.PhotoDataMapper;
import kr.folio.photo.application.ports.output.PhotoRepository;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.core.event.CreatedPhotoEvent;
import kr.folio.photo.domain.core.event.RetrievedPhotoEvent;
import kr.folio.photo.domain.service.PhotoDomainUseCase;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.request.UpdatePhotoImageRequest;
import kr.folio.photo.presentation.dto.response.DeletePhotoResponse;
import kr.folio.photo.presentation.dto.response.UpdatePhotoResponse;
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
        List<String> userIds = includeUploaderToList(createPhotoRequest);

        Photo photo = photoDataMapper.toDomain(createPhotoRequest, userIds);
        Photo savedPhoto = photoRepository.createPhoto(photo);

        CreatedPhotoEvent createdPhotoEvent = photoDomainUseCase.createPhotoEvent(photo);

        if (savedPhoto == null) {
            log.error("Could not create photo. Request User ID : {}",
	createPhotoRequest.requestUserId());
            throw new IllegalArgumentException();
            // todo : Exception 변경
        }

        log.info("Returning CreatedPhotoEvent for photo. Photo ID : {}",
            createPhotoRequest.requestUserId());
        return createdPhotoEvent;
    }

    private List<String> includeUploaderToList(CreatePhotoRequest createPhotoRequest) {
        List<String> userIds = createPhotoRequest.taggedUserIds();
        userIds.add(createPhotoRequest.requestUserId());

        return userIds;
    }

    // todo : 피드에서 요청 되는 부분
    public RetrievedPhotoEvent retrievePhoto(String requestUserId, Long photoId) {
        // todo : 요청하는 사람에 따라 구분되는 로직
        Photo retrievedPhoto = photoRepository.findPhotoById(photoId).orElse(null);
        if (retrievedPhoto == null) {
            log.error("Could not find photo with id: {}", photoId);
            throw new IllegalArgumentException();
            // todo : PhotoNotFoundException
        }

        log.info("Returning RetrievedPhotoEvent for photo id: {}", photoId);
        return new RetrievedPhotoEvent(retrievedPhoto, requestUserId,
            ZonedDateTime.now(ZoneId.of(UTC)));
    }

    public UpdatePhotoResponse updatePhotoImage(String requestUserId,
        UpdatePhotoImageRequest updatePhotoRequest) {
        return updatePhotoField(updatePhotoRequest.photoId(), photo -> {
            photo.photoImageUrl(updatePhotoRequest.updatedPhotoUrl());
        });
    }

    // todo : 수정 권한은 Feed-Service 에서 수행
    private Photo findPhotoById(Long photoId) {
        return photoRepository.findPhotoById(photoId).orElseThrow(
            IllegalArgumentException::new
            // todo : PhotoNotFoundException
        );
    }

    private UpdatePhotoResponse updatePhotoField(Long photoId, Consumer<Photo> updateFunction) {
        Photo photo = findPhotoById(photoId);
        updateFunction.accept(photo);
        Photo updatedPhoto = photoRepository.updatePhoto(photo);

        if (updatedPhoto == null) {
            log.error("Could not update user with id: {}", photoId);
            throw new IllegalArgumentException();
            // todo : PhotoNotFoundException
        }

        return photoDataMapper.toUpdateResponse(
            updatedPhoto, "피드가 정상적으로 수정되었습니다"
        );
    }

    public DeletePhotoResponse deletePhoto(Long photoId) {
        photoRepository.deletePhotoById(photoId);
        return photoDataMapper.toDeleteResponse(photoId, "피드가 정상적으로 삭제되었습니다");
    }
}
