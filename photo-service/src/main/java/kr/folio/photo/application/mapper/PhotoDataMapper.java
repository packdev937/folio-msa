package kr.folio.photo.application.mapper;

import java.util.List;
import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import kr.folio.photo.presentation.dto.response.DeletePhotoResponse;
import kr.folio.photo.presentation.dto.response.RetrievePhotoResponse;
import kr.folio.photo.presentation.dto.response.UpdatePhotoResponse;
import org.springframework.stereotype.Component;

@Component
public class PhotoDataMapper {

    public Photo toDomain(CreatePhotoRequest createUserRequest, List<String> userIds) {
        return Photo.builder()
            .brandType(createUserRequest.brandType())
            .photoImageUrl(createUserRequest.photoImageUrl())
            .taggedUserIds(userIds)
            .build();
    }

    public CreatePhotoResponse toCreateResponse(Long photoId, String message) {
        return new CreatePhotoResponse(photoId, message);
    }

    public RetrievePhotoResponse toRetrieveResponse(Photo photo) {
        return new RetrievePhotoResponse(
            photo.getPhotoId(),
            photo.getPhotoImageUrl(),
            photo.getBrandType(),
            photo.getTaggedUserIds(),
            photo.getUpdatedAt()
        );
    }

    public UpdatePhotoResponse toUpdateResponse(Photo updatedPhoto, String message) {
        return new UpdatePhotoResponse(updatedPhoto.getPhotoId(), message);
    }

    public DeletePhotoResponse toDeleteResponse(Long photoId, String message) {
        return new DeletePhotoResponse(photoId, message);
    }
}
