package kr.folio.photo.application.mapper;

import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import kr.folio.photo.presentation.dto.response.DeletePhotoResponse;
import kr.folio.photo.presentation.dto.response.RetrievePhotoResponse;
import kr.folio.photo.presentation.dto.response.UpdatePhotoResponse;
import org.springframework.stereotype.Component;

@Component
public class PhotoDataMapper {

    public Photo toDomain(CreatePhotoRequest createUserRequest) {
        return Photo.builder()
            .brandType(createUserRequest.brandType())
            .photoImageUrl(createUserRequest.photoImageUrl())
            .userIds(createUserRequest.userIds())
            .build();
    }

    public CreatePhotoResponse toCreateResponse(Photo photo, String message) {
        return new CreatePhotoResponse(photo.getId(), message);
    }

    public RetrievePhotoResponse toRetrieveResponse(Photo photo) {
        return new RetrievePhotoResponse(
            photo.getId(),
            photo.getPhotoImageUrl(),
            photo.getBrandType(),
            photo.getUserIds()
        );
        // todo : taggerUser와 updatedAt은 나중에 추가할 예정
    }

    public UpdatePhotoResponse toUpdateResponse(Photo updatedPhoto, String message) {
        return new UpdatePhotoResponse(updatedPhoto.getId(), message);
    }

    public DeletePhotoResponse toDeleteResponse(Long photoId, String message) {
        return new DeletePhotoResponse(photoId, message);
    }
}
