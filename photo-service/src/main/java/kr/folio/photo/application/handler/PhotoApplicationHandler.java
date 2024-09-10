package kr.folio.photo.application.handler;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import kr.folio.photo.application.mapper.PhotoDataMapper;
import kr.folio.photo.application.ports.output.PhotoRepository;
import kr.folio.photo.application.service.PhotoEventService;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.domain.core.event.PhotoCreatedExternalEvent;
import kr.folio.photo.domain.service.PhotoDomainUseCase;
import kr.folio.photo.infrastructure.exception.PhotoNotCreatedException;
import kr.folio.photo.infrastructure.exception.PhotoNotFoundException;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.request.UpdatePhotoImageRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import kr.folio.photo.presentation.dto.response.DeletePhotoResponse;
import kr.folio.photo.presentation.dto.response.RetrievePhotoResponse;
import kr.folio.photo.presentation.dto.response.UpdatePhotoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional(readOnly = true)
public class PhotoApplicationHandler {

    private final PhotoDomainUseCase photoDomainUseCase;
    private final PhotoRepository photoRepository;
    private final PhotoDataMapper photoDataMapper;
    private final PhotoEventService photoEventService;

    @Transactional
    public CreatePhotoResponse createPhoto(CreatePhotoRequest createPhotoRequest) {

        List<String> userIds = addUploaderToTaggerUsers(createPhotoRequest);

        Photo photo = photoDataMapper.toDomain(createPhotoRequest, userIds);
        Photo savedPhoto = photoRepository.save(photo);


        if (savedPhoto == null) {
            log.error("Could not create photo. Request User ID : {}", createPhotoRequest.requestUserId());
            throw new PhotoNotCreatedException();
        }

        photoEventService.publishEvent(PhotoCreatedExternalEvent.of(savedPhoto));

        return photoDataMapper.toCreateResponse(
            savedPhoto.getPhotoId(),
            "포토가 정상적으로 생성되었습니다."
        );
    }

    private List<String> addUploaderToTaggerUsers(CreatePhotoRequest createPhotoRequest) {

        List<String> userIds = createPhotoRequest.taggedUserIds();
        userIds.add(createPhotoRequest.requestUserId());

        return userIds;
    }

    public RetrievePhotoResponse retrievePhoto(Long photoId) {

        Photo retrievedPhoto = photoRepository.findPhotoById(photoId).orElse(null);
        if (retrievedPhoto == null) {
            log.error("Could not find photo with id: {}", photoId);
            throw new PhotoNotFoundException();
        }

        log.info("Returning RetrievedPhotoEvent for photo id: {}", photoId);
        return photoDataMapper.toRetrieveResponse(retrievedPhoto);
    }

    @Transactional
    public UpdatePhotoResponse updatePhotoImage(
        String requestUserId,
        UpdatePhotoImageRequest updatePhotoImageRequest) {

        Photo photo = findPhotoById(updatePhotoImageRequest.photoId());

        if (!photo.isTaggedBy(requestUserId)) {
            log.error("User with id: {} is not tagged in photo with id: {}", requestUserId,
	updatePhotoImageRequest.photoId());

            throw new IllegalStateException("사진에 태그된 사용자만 수정할 수 있습니다.");
        }

        return updatePhotoField(
            photo,
            updatedPhoto -> photoDomainUseCase.updatePhotoImageUrl(
	updatedPhoto,
	updatePhotoImageRequest.updatedPhotoUrl()
            )
        );
    }

    private UpdatePhotoResponse updatePhotoField(
        Photo photo,
        Consumer<Photo> updateFunction) {

        updateFunction.accept(photo);
        Photo updatedPhoto = photoRepository.save(photo);

        return photoDataMapper.toUpdateResponse(
            updatedPhoto.getPhotoId(),
            updatedPhoto.getPhotoImageUrl(),
            "포토가 정상적으로 수정되었습니다"
        );
    }

    @Transactional
    public DeletePhotoResponse deletePhoto(Long photoId) {

        photoRepository.deletePhotoById(photoId);

        return photoDataMapper.toDeleteResponse(photoId, "포토가 정상적으로 삭제되었습니다");
    }

    @Transactional
    public void deleteUserFromTag(String userId) {

        List<Photo> photos = photoRepository.findPhotosByTaggedUserId(userId);

        photoRepository.saveAll(photos.stream()
            .map(photo -> photoDomainUseCase.deleteUserFromTag(photo, userId))
            .collect(Collectors.toList()));
    }

    private Photo findPhotoById(Long photoId) {

        return photoRepository.findPhotoById(photoId)
            .orElseThrow(PhotoNotFoundException::new);
    }
}
