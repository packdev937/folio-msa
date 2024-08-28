package kr.folio.photo.application.mapper;

import kr.folio.photo.domain.core.entity.Photo;
import kr.folio.photo.presentation.dto.request.CreatePhotoRequest;
import kr.folio.photo.presentation.dto.response.CreatePhotoResponse;
import org.springframework.stereotype.Component;

@Component
public class PhotoDataMapper {

    public Photo toDomain(CreatePhotoRequest createUserRequest) {
        return Photo.builder()
            .brandType(createUserRequest.brandType())
            .photoUrl(createUserRequest.photoUrl())
            .userIds(createUserRequest.userIds())
            .build();
    }

    public CreatePhotoResponse toCreateResponse(Photo photo, String message) {
        return new CreatePhotoResponse(photo.getId(), message);
    }
}
